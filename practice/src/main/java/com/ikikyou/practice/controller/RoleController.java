package com.ikikyou.practice.controller;

import com.ikikyou.practice.entity.SysRole;
import com.ikikyou.practice.service.SysRoleService;
import com.ikikyou.practice.utils.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 角色成立控制层
 * @author hongx
 * @date 2023/03/27 10:05
 */
@Slf4j
@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {

    final SysRoleService roleService;

    @GetMapping("/getByUserId")
    public Result<List<SysRole>> getRolesByUid(@RequestParam Long uid) {
        return roleService.getRolesByUid(uid);
    }

    @GetMapping("/getEnableRoles")
    public Result<List<SysRole>> getAllEnableRoles(){
        return roleService.getAllEnableRoles();
    }
}
