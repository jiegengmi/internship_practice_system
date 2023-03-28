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
import com.ikikyou.practice.entity.SysUser;
import com.ikikyou.practice.service.SysUserService;
import com.ikikyou.practice.mapper.SysUserMapper;
import com.ikikyou.practice.utils.PageResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.ikikyou.practice.constant.CommonConstant.BCRYPT_SALT;

/**
* @author 25726
* @description 针对表【sys_user】的数据库操作Service实现
* @createDate 2023-03-21 10:02:03
*/
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService{

    final SysUserMapper userMapper;

    @Override
    public SysUser findByName(String username) {
        Objects.requireNonNull(username);
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", username);
        return userMapper.selectOne(queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insert(UserDTO user) {
        if (ObjectUtil.isEmpty(user)) {
            throw new BusinessException("参数为空");
        }
        // 校验账号是否合法
        if (checkUser(user)) {
            throw new BusinessException("用户名已被注册！");
        }
        // 新增用户信息
        SysUser userInfo = new SysUser();
        BeanUtils.copyProperties(user, userInfo);
        userInfo.setId(System.currentTimeMillis());
        userInfo.setStatus(1);
        userInfo.setPassword(BCrypt.hashpw(user.getPassword(), BCRYPT_SALT));
        this.save(userInfo);
    }

    @Override
    public PageResult<UserDTO> getUsers(UserQueryDTO userQueryDTO) {
        Page<SysUser> page = new Page<>(userQueryDTO.getPageNum(), userQueryDTO.getPageSize());
        Page<SysUser> sysUserPage = userMapper.selectPage(page, new LambdaQueryWrapper<SysUser>()
                .like(StrUtil.isNotBlank(userQueryDTO.getNickName()), SysUser::getNickName, userQueryDTO.getNickName())
                .like(StrUtil.isNotBlank(userQueryDTO.getEmail()), SysUser::getEmail, userQueryDTO.getEmail())
                .like(StrUtil.isNotBlank(userQueryDTO.getUsername()), SysUser::getName, userQueryDTO.getUsername()));
        PageResult<UserDTO> pageResult = new PageResult<>();
        BeanUtils.copyProperties(sysUserPage, pageResult);
        return pageResult;
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




