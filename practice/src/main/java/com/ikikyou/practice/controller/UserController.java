package com.ikikyou.practice.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ikikyou.practice.dto.UserDTO;
import com.ikikyou.practice.dto.UserInfoDTO;
import com.ikikyou.practice.dto.UserUpdateDTO;
import com.ikikyou.practice.dto.query.UserQueryDTO;
import com.ikikyou.practice.entity.system.SysUser;
import com.ikikyou.practice.service.SysUserService;
import com.ikikyou.practice.service.UserInfoService;
import com.ikikyou.practice.utils.PageResult;
import com.ikikyou.practice.utils.Result;
import com.ikikyou.practice.utils.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author ikikyou
 * @date 2023/03/24 08:48
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Api(tags = "用户")
public class UserController {

    final SysUserService userService;
    final UserInfoService userInfoService;

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('user:list')")
    public Result<Page<UserDTO>> getUserList(UserQueryDTO userQueryDTO){
        return Result.ok(userService.getUserList(userQueryDTO));
    }

    @GetMapping({"/{userId}", "/"})
    @PreAuthorize("hasAuthority('user:info')")
    public Result<UserUpdateDTO> getUserInfo(@PathVariable(required = false) Long userId){
        return userService.getUserInfo(userId);
    }

    /**
     * 获取当前登录用户、角色、权限
     */
    @GetMapping("/info")
    public Result<UserInfoDTO> getByCurrUser(){
        return userInfoService.getInfoByUserName(SecurityUtil.getUserName());
    }


    @PostMapping("/insert")
    @ApiOperation(value = "新增用户")
    @PreAuthorize("hasAuthority('user:insert')")
    public Result<Void> add(@RequestBody @Valid UserUpdateDTO userDTO){
        return userService.insert(userDTO);
    }

    /**
     * 修改用户
     * @param userDTO 用户信息
     */
    @PutMapping("/update")
    @ApiOperation(value = "修改用户")
    @PreAuthorize("hasAuthority('user:update')")
    public Result<Void> update(@RequestBody @Valid UserUpdateDTO userDTO) {
        return userService.update(userDTO);
    }

    /**
     * 根据用户id获取单个用户信息
     * @param id 用户id
     */
    @GetMapping("/getById")
    @PreAuthorize("hasAuthority('user:info')")
    public Result<UserDTO> getById(@RequestParam Long id){
        return userService.getUserById(id);
    }

    /**
     * 根据用户名获取单个用户信息
     * @param username 用户账户
     */
    @GetMapping("/getByName")
    @PreAuthorize("hasAuthority('user:info')")
    public Result<UserDTO> getByName(@RequestParam String username) {
        return userService.getUserByName(username);
    }

    /**
     * 删除用户
     * @param id 用户id
     * @return 是否删除成功
     */
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除用户")
    @PreAuthorize("hasAuthority('user:delete')")
    public Result<Void> deleteById(@PathVariable Long id) {
        return userService.deleteById(id);
    }
}
