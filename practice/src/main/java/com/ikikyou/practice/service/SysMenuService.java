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
     * 新增一个菜单
     *
     * @param menuDTO 菜单传递对象
     * @return 结果
     */
    Result<Void> save(MenuDTO menuDTO);

    /**
     * 修改一个菜单
     *
     * @param menuDTO 菜单传递对象
     * @return 结果
     */
    Result<Void> update(MenuDTO menuDTO);

    /**
     * 获取菜单列表
     *
     * @param menuQuery 菜单查询对象
     */
    Result<List<MenuDTO>> getMenuList(MenuQuery menuQuery);

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

    /**
     * 获取单个菜单
     *
     * @param menuId 菜单id
     * @return 菜单对象
     */
    Result<MenuDTO> getByMenuId(Long menuId);

    /**
     * 删除一个菜单对象
     *
     * @param menuId 菜单id
     * @return 菜单对象
     */
    Result<Void> deleteByMenuId(Long menuId);

    /**
     * 查看是否存在子节点
     *
     * @param menId 菜单id
     * @return true：存在
     */
    boolean checkHasChild(Long menId);

    /**
     * 查询菜单是否存在角色
     *
     * @param menuId 菜单ID
     * @return 结果 true 存在 false 不存在
     */
    boolean checkMenuExistRole(Long menuId);
}
