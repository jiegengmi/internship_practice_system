package com.ikikyou.practice.controller;

import com.ikikyou.practice.dto.UserDTO;
import com.ikikyou.practice.service.SysUserService;
import com.ikikyou.practice.utils.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
