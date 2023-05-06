package com.ikikyou.practice.controller;

import com.ikikyou.practice.dto.query.MenuQueryDTO;
import com.ikikyou.practice.service.SysMenuService;
import com.ikikyou.practice.utils.Result;
import com.ikikyou.practice.vo.MenuVO;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 菜单操作实现
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
     * 构建用户菜单
     *
     * @return 返回当前用户的菜单列表（父id为空则为一级菜单）
     */
    @GetMapping("/build")
    public Result<List<MenuVO>> buildUserMenus() {
        return Result.ok(menuService.buildRouteMenus());
    }

    /**
     * 获取菜单下拉树列表
     */
    @GetMapping("/tree")
    public Result<List<MenuVO>> getTreeMenu(MenuQueryDTO menuQuery){
        return menuService.getTreeMenu(menuQuery);
    }
}
