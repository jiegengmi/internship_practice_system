package com.ikikyou.practice.service;

import com.ikikyou.practice.model.query.MenuQuery;
import com.ikikyou.practice.utils.Result;
import com.ikikyou.practice.model.dto.MenuRoleDTO;
import com.ikikyou.practice.model.dto.MenuDTO;
import com.ikikyou.practice.model.entity.system.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 25726
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Service
* @createDate 2023-04-18 09:41:22
*/
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 构建菜单路由
     */
    List<MenuDTO> buildRouteMenus();

    /**
     * 获取菜单下拉树列表
     */
    Result<List<MenuDTO>> getTreeMenu(MenuQuery menu);

    /**
     * 获取角色对应的菜单数树
     *
     * @param roleId 角色id
     * @return 菜单树
     */
    Result<MenuRoleDTO> getRoleMenus(Long roleId);

    /**
     * 获取全部菜单
     *
     * @param menuQuery 菜单查询对象
     */
    List<SysMenu> getAllMenu(MenuQuery menuQuery);
}
