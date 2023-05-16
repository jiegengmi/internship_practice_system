package com.ikikyou.practice.controller;

import com.ikikyou.practice.model.query.MenuQuery;
import com.ikikyou.practice.service.SysMenuService;
import com.ikikyou.practice.utils.Result;
import com.ikikyou.practice.model.dto.MenuRoleDTO;
import com.ikikyou.practice.model.dto.MenuDTO;
import io.swagger.annotations.Api;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单操作实现
 *
 * @author ikikyou
 * @date 2023/03/24 12:37
 */
@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
@Api(tags = "菜单")
public class MenuController {

    final SysMenuService menuService;

    /**
     * 新增菜单
     *
     * @param menuDTO 菜单对象
     * @return 结果
     */
    @PreAuthorize("@hasAuthority('system:menu:add')")
    @PostMapping
    public Result<Void> save(@RequestBody @Valid MenuDTO menuDTO) {
        return menuService.save(menuDTO);
    }

    /**
     * 修改一个菜单
     *
     * @param menuDTO 菜单对象
     * @return 结果
     */
    @PreAuthorize("@hasAuthority('system:menu:update')")
    @PutMapping
    public Result<Void> update(@RequestBody @Valid MenuDTO menuDTO) {
        return menuService.update(menuDTO);
    }

    /**
     * 获取菜单列表
     *
     * @param menuQuery 菜单查询对象
     * @return 菜单列表
     */
    @GetMapping("/list")
    public Result<List<MenuDTO>> getMenuList(MenuQuery menuQuery) {
        return menuService.getMenuList(menuQuery);
    }

    /**
     * 获取单个菜单
     *
     * @param menuId 菜单id
     * @return 菜单对象
     */
    @GetMapping("/{menuId}")
    public Result<MenuDTO> getMenu(@PathVariable Long menuId) {
        return menuService.getByMenuId(menuId);
    }

    /**
     * 删除菜单
     *
     * @param menuId 菜单id
     * @return 菜单对象
     */
    @DeleteMapping("/{menuId}")
    public Result<Void> deleteMenu(@PathVariable Long menuId) {
        return menuService.deleteByMenuId(menuId);
    }

    /**
     * 构建用户菜单
     *
     * @return 返回当前用户的菜单列表（父id为空则为一级菜单）
     */
    @GetMapping("/build")
    public Result<List<MenuDTO>> buildUserMenus() {
        return Result.ok(menuService.buildRouteMenus());
    }

    /**
     * 获取菜单下拉树列表
     */
    @GetMapping("/tree")
    public Result<List<MenuDTO>> getTreeMenu(MenuQuery menuQuery) {
        return menuService.getTreeMenu(menuQuery);
    }

    /**
     * 获取角色对应的菜单
     *
     * @param roleId 角色id
     */
    @GetMapping(value = "/roleMenuTreeSelect/{roleId}")
    public Result<MenuRoleDTO> roleMenuTreeSelect(@PathVariable("roleId") Long roleId) {
        return menuService.getRoleMenus(roleId);
    }
}
