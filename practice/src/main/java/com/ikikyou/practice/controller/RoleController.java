package com.ikikyou.practice.controller;

import com.ikikyou.practice.dto.query.RoleQueryDTO;
import com.ikikyou.practice.entity.system.SysRole;
import com.ikikyou.practice.service.SysRoleService;
import com.ikikyou.practice.utils.Result;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色成立控制层
 * @author ikikyou
 * @date 2023/03/27 10:05
 */
@Slf4j
@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
@Api(tags = "角色")
public class RoleController {

    final SysRoleService roleService;

    /**
     * 查询角色
     *
     * @param roleQuery 角色查询对象
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('role:list')")
    public Result<?> getRoleList(RoleQueryDTO roleQuery){
        return roleService.getAllRoles(roleQuery);
    }

    /**
     * 新增角色
     *
     * @param role 角色相关信息
     */
    @PutMapping("/insert")
    public Result<Void> insert(SysRole role){
        return roleService.insertOrUpdate(role);
    }

    /**
     * 根据角色id进行角色的修改
     *
     * @param role 角色对象
     */
    @PostMapping("/update")
    public Result<Void> update(SysRole role){
        return roleService.insertOrUpdate(role);
    }

    /**
     * 删除一共角色
     *
     * @param roleId 角色id
     */
    @DeleteMapping("/delete/{roleId}")
    public Result<Void> delete(@PathVariable Long roleId){
        return roleService.deleteById(roleId);
    }

    /**
     * 根据角色id获取该角色的属性
     *
     * @param roleId 角色id
     */
    @GetMapping("/{roleId}")
    public Result<SysRole> getRoleById(@PathVariable Long roleId) {
        return roleService.getRoleById(roleId);
    }

    /**
     * 获取指定用户的所有角色
     *
     * @param uid 用户id
     */
    @GetMapping("/getByUserId")
    @PreAuthorize("hasAuthority('role:info')")
    public Result<List<SysRole>> getRolesByUid(@RequestParam Long uid) {
        return roleService.getRolesByUid(uid);
    }

    /**
     * 获取当前正常的所有角色
     */
    @GetMapping("/getEnableRoles")
    @PreAuthorize("hasAuthority('user:list')")
    public Result<List<SysRole>> getAllEnableRoles(){
        return roleService.getAllEnableRoles();
    }
}
