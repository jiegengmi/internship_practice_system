package com.ikikyou.practice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ikikyou.practice.entity.SysMenu;
import com.ikikyou.practice.service.SysMenuService;
import com.ikikyou.practice.mapper.SysMenuMapper;
import com.ikikyou.practice.utils.SecurityUtil;
import com.ikikyou.practice.dto.MenuDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author 25726
* @description 针对表【sys_menu(权限)】的数据库操作Service实现
* @createDate 2023-03-21 11:10:58
*/
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService{

    final SysMenuMapper menuMapper;

    @Override
    public List<MenuDTO> buildMenus() {

        List<SysMenu> userSysMenus = menuMapper.getByRoleIds(SecurityUtil.getRoles());
        //构建树级菜单
        List<MenuDTO> menuList = new ArrayList<>();
        // 先找到所有的一级菜单
        for (SysMenu menu : userSysMenus) {
            // 一级菜单没有parentId
            if (menu.getParentId() == null) {
                MenuDTO menuDTO = new MenuDTO();
                BeanUtils.copyProperties(menu, menuDTO);
                menuList.add(menuDTO);
            }
        }
        // 为一级菜单设置子菜单
        for (MenuDTO menuDTO : menuList) {
            menuDTO.setChildren(getChild(menuDTO.getId(), userSysMenus));
        }
        return menuList;
    }

    /***
     * 获取子菜单
     * @param id 菜单id
     * @param rootMenu 根
     * @return list
     */
    private List<MenuDTO> getChild(Long id, List<SysMenu> rootMenu) {
        List<MenuDTO> childList = new ArrayList<>();
        for (SysMenu menu : rootMenu) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (menu.getId() != null && menu.getParentId() != null) {
                if (menu.getParentId().equals(id)) {
                    MenuDTO menuDTO = new MenuDTO();
                    BeanUtils.copyProperties(menu, menuDTO);
                    childList.add(menuDTO);
                }
            }
        }
        for (MenuDTO menu : childList) {
            menu.setChildren(getChild(menu.getId(), rootMenu));
        }
        //退出循环
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }
}




