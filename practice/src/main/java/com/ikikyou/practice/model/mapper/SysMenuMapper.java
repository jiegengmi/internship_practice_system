package com.ikikyou.practice.model.mapper;

import com.ikikyou.practice.model.query.MenuQuery;
import com.ikikyou.practice.model.entity.system.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author 25726
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Mapper
* @createDate 2023-04-18 09:41:22
* @Entity com.ikikyou.practice.model.entity.system.SysMenu
*/
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 根据角色获取权限
     * @param roleIds 角色列表
     * @return 菜单
     */
    List<SysMenu> getByRoleIds(List<Long> roleIds);

    /**
     * 查询菜单
     *
     * @param menuQuery 菜单查询对象
     */
    List<SysMenu> queryMenus(MenuQuery menuQuery);
}




