package com.ikikyou.practice.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ikikyou.practice.common.BusinessException;
import com.ikikyou.practice.dto.UserDTO;
import com.ikikyou.practice.dto.query.UserQueryDTO;
import com.ikikyou.practice.emun.UserStatusEmun;
import com.ikikyou.practice.entity.system.SysUser;
import com.ikikyou.practice.entity.system.UserRole;
import com.ikikyou.practice.mapper.UserRoleMapper;
import com.ikikyou.practice.service.SysUserService;
import com.ikikyou.practice.mapper.SysUserMapper;
import com.ikikyou.practice.utils.PageResult;
import com.ikikyou.practice.utils.Result;
import com.ikikyou.practice.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.ikikyou.practice.constant.CommonConstant.BCRYPT_SALT;

/**
* @author 25726
* @description 针对表【sys_user】的数据库操作Service实现
* @createDate 2023-03-21 10:02:03
*/
@Service
@Slf4j
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService{

    final SysUserMapper userMapper;
    final UserRoleMapper userRoleMapper;
    @Value("${practice.user.isLogicalDelete}")
    private String isLogicalDelete;

    @Override
    public SysUser findByName(String username) {
        Objects.requireNonNull(username);
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", username);
        return userMapper.selectOne(queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result<Void> insert(UserDTO user) {
        if (ObjectUtil.isEmpty(user)) {
            return Result.fail("参数为空");
        }
        // 校验账号是否合法
        if (checkUser(user)) {
            return Result.fail("用户名已被注册！");
        }
        // 新增用户信息
        SysUser userInfo = new SysUser();
        BeanUtils.copyProperties(user, userInfo);
        userInfo.setId(System.currentTimeMillis());
        userInfo.setStatus(1);
        userInfo.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        return this.save(userInfo) ? Result.ok("新增成功") : Result.fail("新增失败");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Void> update(UserDTO userDTO) {
        if (ObjectUtil.isEmpty(userDTO) || userDTO.getId() == null){
            return Result.fail("修改失败");
        }
        SysUser user = userMapper.selectById(userDTO.getId());
        if (null == user) {
            return Result.fail("修改失败");
        }
        user.setPassword(BCrypt.hashpw(userDTO.getPassword(), BCRYPT_SALT));
        return this.saveOrUpdate(user) ? Result.ok() : Result.fail();
    }

    @Override
    public PageResult<SysUser> getUsers(UserQueryDTO userQueryDTO) {
        Page<SysUser> page = new Page<>(userQueryDTO.getPageNum(), userQueryDTO.getPageSize());
        List<Long> userIds = new ArrayList<>();
        if (userQueryDTO.getRoleId() != 0) {
            userIds = userRoleMapper.selectList(new LambdaQueryWrapper<UserRole>()
                            .select(UserRole::getUserId).eq(UserRole::getRoleId, userQueryDTO.getRoleId()))
                    .stream().map(UserRole::getUserId).collect(Collectors.toList());
        }
        Page<SysUser> sysUserPage = userMapper.selectPage(page, new LambdaQueryWrapper<SysUser>()
                .in(!userIds.isEmpty(), SysUser::getId, userIds)
                .like(StrUtil.isNotBlank(userQueryDTO.getNickName()), SysUser::getNickName, userQueryDTO.getNickName())
                .like(StrUtil.isNotBlank(userQueryDTO.getTel()), SysUser::getNickName, userQueryDTO.getTel())
                .like(StrUtil.isNotBlank(userQueryDTO.getEmail()), SysUser::getEmail, userQueryDTO.getEmail())
                .like(StrUtil.isNotBlank(userQueryDTO.getUsername()), SysUser::getName, userQueryDTO.getUsername()));
        PageResult<SysUser> pageResult = new PageResult<>();
        BeanUtils.copyProperties(sysUserPage, pageResult);
        return pageResult;
    }

    @Override
    public Result<UserDTO> getUserById(Long uid) {
        if (uid == null) {
            return Result.fail();
        }
        SysUser user = getById(uid);
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return Result.ok(userDTO);
    }

    @Override
    public Result<UserDTO> getUserByName(String username) {
        Objects.requireNonNull(username, "null");
        SysUser user = this.findByName(username);
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return Result.ok(userDTO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Void> deleteById(Long id) {
        if (null == id) {
            return Result.fail("null");
        }
        SysUser user = userMapper.selectById(id);
        if (StrUtil.isEmpty(isLogicalDelete) || Boolean.parseBoolean(isLogicalDelete)) {
            if (null != user) {
                user.setStatus(UserStatusEmun.CANCELLATION.getCode());
                user.setUpdateTime(new Date());
                saveOrUpdate(user);
                log.warn("--用户{}注销账户{}----", SecurityUtil.getUserName(), user.getName());
            }
        } else {
            if (userMapper.deleteById(id) != 0) {
                log.warn("--用户{}删除账户{}----", SecurityUtil.getUserName(), user.getName());
            }
        }
        return Result.ok();
    }

    /**
     * 校验用户数据是否合法
     *
     * @return 结果
     */
    private Boolean checkUser(UserDTO userDTO) {
        //查询用户名是否存在
        SysUser user = userMapper.selectOne(new LambdaQueryWrapper<SysUser>()
                .select(SysUser::getName)
                .eq(SysUser::getName, userDTO.getName()));
        return Objects.nonNull(user);
    }
}




