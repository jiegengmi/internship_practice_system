package com.ikikyou.practice.controller;

import com.ikikyou.practice.dto.UserDTO;
import com.ikikyou.practice.dto.query.UserQueryDTO;
import com.ikikyou.practice.entity.SysUser;
import com.ikikyou.practice.service.SysUserService;
import com.ikikyou.practice.utils.PageResult;
import com.ikikyou.practice.utils.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author hongx
 * @date 2023/03/24 08:48
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    final SysUserService userService;

    @PostMapping("/insert")
    public Result<Void> add(@RequestBody @Valid UserDTO userDTO){
        userService.insert(userDTO);
        return Result.ok();
    }

    /**
     * 修改用户
     * @param userDTO 用户信息
     */
    @PostMapping("/update")
    public Result<Void> update(@RequestBody @Valid UserDTO userDTO) {
        return userService.update(userDTO);
    }

    @GetMapping("/query")
    public Result<PageResult<SysUser>> getUsers(UserQueryDTO userQuery) {
        return Result.ok(userService.getUsers(userQuery));
    }

    @GetMapping("/getById")
    public Result<UserDTO> getById(@RequestParam Long id){
        return userService.getUserById(id);
    }

    @GetMapping("/getByName")
    public Result<UserDTO> getByName(@RequestParam String username) {
        return userService.getUserByName(username);
    }

    @DeleteMapping("/delete/{id}")
    public Result<Void> deleteById(@PathVariable Long id) {
        return userService.deleteById(id);
    }
}
