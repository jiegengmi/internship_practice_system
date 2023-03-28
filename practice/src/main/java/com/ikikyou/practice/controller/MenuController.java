package com.ikikyou.practice.controller;

import com.ikikyou.practice.service.SysMenuService;
import com.ikikyou.practice.utils.Result;
import com.ikikyou.practice.dto.MenuDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author hongx
 * @date 2023/03/24 12:37
 */
@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {

    final SysMenuService menuService;

    /**
     * 构建用户菜单
     */
    @GetMapping("/build")
    public Result<List<MenuDTO>> buildUserMenus() {
        return Result.ok(menuService.buildMenus());
    }
}
