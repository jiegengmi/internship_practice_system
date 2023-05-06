package com.ikikyou.practice.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ikikyou.practice.common.BusinessException;
import com.ikikyou.practice.dto.UserDTO;
import com.ikikyou.practice.dto.UserUpdateDTO;
import com.ikikyou.practice.dto.query.UserQueryDTO;
import com.ikikyou.practice.emun.UserStatusEmun;
import com.ikikyou.practice.entity.system.SysUser;
import com.ikikyou.practice.mapper.SysPostMapper;
import com.ikikyou.practice.mapper.SysRoleMapper;
import com.ikikyou.practice.service.SysUserPostService;
import com.ikikyou.practice.service.SysUserRoleService;
import com.ikikyou.practice.service.SysUserService;
import com.ikikyou.practice.mapper.SysUserMapper;
import com.ikikyou.practice.utils.Result;
import com.ikikyou.practice.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
* @author 25726
* @description 针对表【sys_user(用户信息表)】的数据库操作Service实现
* @createDate 2023-04-21 11:07:14
*/
@Service
@Slf4j
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService{

    final SysUserMapper userMapper;
    final SysPostMapper postMapper;
    final SysRoleMapper roleMapper;
    final SysUserRoleService userRoleService;
    final SysUserPostService userPostService;

    @Value("${practice.user.isLogicalDelete}")
    private String isLogicalDelete;

    @Override
    public SysUser findByName(String username) {
        Objects.requireNonNull(username);
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", username);
        return userMapper.selectOne(queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result<Void> insert(UserUpdateDTO userDTO) {
        if (ObjectUtil.hasEmpty(userDTO)) {
            return Result.fail("参数为空");
        }
        // 校验账号是否合法
        Result<Void> checkedUser = checkUser(userDTO);
        if (!checkedUser.isSuccess()) {
            return checkedUser;
        }
        SysUser user = new SysUser();
        BeanUtils.copyProperties(userDTO, user);
        user.setCreateBy(SecurityUtil.getUserName());
        user.setCreateTime(new Date());
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        userRoleService.insertRelation(user.getUserId(), userDTO.getRoleIds(), true);
        userPostService.insertRelation(user.getUserId(), userDTO.getRoleIds(), true);
        if (!this.save(user)) {
            throw new BusinessException("保存用户异常");
        }
        return Result.ok("新增成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Void> update(UserUpdateDTO userDTO) {
        if (ObjectUtil.hasEmpty(userDTO)){
            return Result.fail("修改失败");
        }
        Result<Void> checkedUser = checkUser(userDTO);
        if (!checkedUser.isSuccess()) {
            return checkedUser;
        }
        SysUser dtoUser = new SysUser();
        BeanUtils.copyProperties(userDTO, dtoUser);
        dtoUser.setUpdateBy(SecurityUtil.getUserName());
        dtoUser.setUpdateTime(new Date());
        userRoleService.deleteRelation(dtoUser.getUserId(), true);
        userRoleService.insertRelation(dtoUser.getUserId(), userDTO.getRoleIds(), true);
        userPostService.deleteRelation(dtoUser.getUserId(), true);
        userPostService.insertRelation(dtoUser.getUserId(), userDTO.getRoleIds(), true);
        if (!this.saveOrUpdate(dtoUser)) {
            throw new BusinessException("更新用户异常");
        }
        return Result.ok();
    }

    @Override
    public Page<UserDTO> getUserList(UserQueryDTO userQueryDTO) {
        Page<UserDTO> page = new Page<>(userQueryDTO.getPageNum(), userQueryDTO.getPageSize());
        List<UserDTO> userList = userMapper.getUserList(userQueryDTO);
        page.setTotal(userList.size());
        page.setRecords(userList);
        return page;
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
                user.setUpdateBy(SecurityUtil.getUserName());
                saveOrUpdate(user);
                log.warn("--用户{}注销账户{}----", SecurityUtil.getUserName(), user.getUserName());
            }
        } else {
            if (userMapper.deleteById(id) != 0) {
                log.warn("--用户{}删除账户{}----", SecurityUtil.getUserName(), user.getUserName());
            }
        }
        return Result.ok();
    }

    @Override
    public Result<UserUpdateDTO> getUserInfo(Long userId) {
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
        userUpdateDTO.setPosts(postMapper.selectList(null));
        userUpdateDTO.setRoles(roleMapper.selectList(null));
        if (null != userId) {
            SysUser user = getById(userId);
            BeanUtils.copyProperties(user, userUpdateDTO);
            userUpdateDTO.setPostIds(postMapper.getPostIdsByUserId(userId));
            userUpdateDTO.setRoleIds(roleMapper.getRoleIdByUserId(userId));
        }
        return Result.ok(userUpdateDTO);
    }

    /**
     * 校验用户数据是否合法
     *
     * @return 结果
     */
    private Result<Void> checkUser(UserUpdateDTO user) {
        //查询用户名是否存在
        SysUser sysUser = userMapper.selectOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUserId, user.getUserId())
                .or()
                .eq(SysUser::getUserName, user.getUserName())
                .or()
                .eq(SysUser::getTel, user.getTel())
                .or()
                .eq(SysUser::getEmail, user.getEmail()));
        if (null != sysUser && !Objects.equals(sysUser.getUserId(), user.getUserId()) ) {
            if (user.getUserName().equals(sysUser.getUserName())) {
                return Result.fail("当前用户名已存在");
            } else if (user.getTel().equals(sysUser.getTel())) {
                return Result.fail("当前电话已存在");
            } else if (user.getEmail().equals(sysUser.getEmail())) {
                return Result.fail("当前输入邮箱账户已存在");
            }
        }
        return Result.ok();
    }
}




