package com.ikikyou.practice.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ikikyou.practice.constant.MenuConstants;
import com.ikikyou.practice.model.query.MenuQuery;
import com.ikikyou.practice.utils.ParamUtil;
import com.ikikyou.practice.utils.Result;
import com.ikikyou.practice.model.dto.MenuRoleDTO;
import com.ikikyou.practice.model.dto.MenuRouteDTO;
import com.ikikyou.practice.model.dto.MenuDTO;
import com.ikikyou.practice.model.entity.system.SysMenu;
import com.ikikyou.practice.service.SysMenuService;
import com.ikikyou.practice.model.mapper.SysMenuMapper;
import com.ikikyou.practice.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 25726
 * @description 针对表【sys_menu(菜单权限表)】的数据库操作Service实现
 * @createDate 2023-04-18 09:41:22
 */
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    final SysMenuMapper menuMapper;

    @Override
    public List<MenuDTO> buildRouteMenus() {
        List<Long> roles = SecurityUtil.getRoles();
        if (CollectionUtil.isEmpty(roles)) {
            return Collections.emptyList();
        }
        List<SysMenu> userSysMenus = menuMapper.getByRoleIds(roles);
        return getRouteTreeMenu(userSysMenus);
    }

    @Override
    public Result<List<MenuDTO>> getTreeMenu(MenuQuery menu) {
        Long userId = SecurityUtil.getUserId();
        List<SysMenu> menuList;
        if (SecurityUtil.isAdmin(userId)) {
            menuList = getAllMenu(menu);
        } else {
            menu.setUserId(SecurityUtil.getUserId());
            menuList = menuMapper.queryMenus(menu);
        }
        return Result.ok(buildMenuTree(menuList));
    }

    @Override
    public Result<MenuRoleDTO> getRoleMenus(Long roleId) {
        ParamUtil.checkId(roleId);
        MenuRoleDTO menuRoleDTO = new MenuRoleDTO();
        menuRoleDTO.setMenus(getTreeMenu(new MenuQuery()).getData());
        menuRoleDTO.setCheckedKeys(getRoleIds(menuMapper.getByRoleIds(List.of(roleId))));
        return Result.ok(menuRoleDTO);
    }

    @Override
    public List<SysMenu> getAllMenu(MenuQuery menuQuery) {
        return list(new LambdaQueryWrapper<SysMenu>()
                .eq(StringUtils.isNotEmpty(menuQuery.getVisible()), SysMenu::getVisible, menuQuery.getVisible())
                .eq(StringUtils.isNotEmpty(menuQuery.getStatus()), SysMenu::getStatus, menuQuery.getStatus())
                .like(StringUtils.isNotEmpty(menuQuery.getMenuName()), SysMenu::getMenuName, menuQuery.getMenuName())
                .orderBy(true, true, SysMenu::getParentId)
                .orderBy(true, true, SysMenu::getOrderNum)
        );
    }

    private List<Long> getRoleIds(List<SysMenu> menus) {
        return menus.stream().map(SysMenu::getMenuId).toList();
    }

    public List<MenuDTO> buildMenuTree(List<SysMenu> menus) {
        if (CollectionUtil.isEmpty(menus)) {
            return Collections.emptyList();
        }
        List<Long> tempList = menus.stream().map(SysMenu::getMenuId).toList();
        return menus.stream()
                //如果是顶级节点, 遍历该父节点的所有子节点
                .filter(menu -> !tempList.contains(menu.getParentId()))
                .map(menu -> {
                    MenuDTO menuDTO = new MenuDTO();
                    BeanUtils.copyProperties(menu, menuDTO);
                    menuDTO.setName(menu.getMenuName());
                    recursionFn(menus, menuDTO);
                    return menuDTO;
                }).collect(Collectors.toList());
    }

    private void recursionFn(List<SysMenu> list, MenuDTO parentMenu) {
        // 得到子节点列表
        List<MenuDTO> childList = getChildList(list, parentMenu);
        parentMenu.setChildren(childList);
        for (MenuDTO tChild : childList) {
            if (!hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    private boolean hasChild(List<SysMenu> list, MenuDTO parentMenu) {
        return list.stream()
                .filter(menu -> menu.getParentId().equals(parentMenu.getMenuId()))
                .findAny()
                .isEmpty();
    }

    private List<MenuDTO> getChildList(List<SysMenu> list, MenuDTO parentMenu) {
        return list.stream()
                .filter(menu -> menu.getParentId().equals(parentMenu.getMenuId()))
                .map(menu -> {
                    MenuDTO menuDTO = new MenuDTO();
                    BeanUtils.copyProperties(menu, menuDTO);
                    menuDTO.setName(menu.getMenuName());
                    return menuDTO;
                }).collect(Collectors.toList());
    }

    /**
     * 生成路由菜单树
     *
     * @param menuList 一级菜单及其子菜单
     * @return 菜单树
     */
    private List<MenuDTO> getRouteTreeMenu(List<SysMenu> menuList) {
        return menuList.stream()
                .filter(menu -> menu.getParentId() == 0)
                .map(menu -> {
                    MenuDTO menuDTO = covertMenu(menu);
                    menuDTO.setChildren(getChild(menuDTO.getMenuId(), menuList));
                    return getMenuInfo(menuDTO);
                }).collect(Collectors.toList());
    }

    /**
     * 赋值
     *
     * @param menuDTO 菜单对象
     * @return 菜单对象
     */
    private MenuDTO getMenuInfo(MenuDTO menuDTO) {
        List<MenuDTO> subMenuList = menuDTO.getChildren();
        if (CollectionUtil.isNotEmpty(subMenuList) && MenuConstants.TYPE_DIR.equals(menuDTO.getMenuType())) {
            menuDTO.setAlwaysShow(true);
            menuDTO.setRedirect("noRedirect");
        } else if (isMenuFrame(menuDTO)) {
            menuDTO.setMeta(null);
            List<MenuDTO> childrenList = new ArrayList<>();
            MenuDTO children = new MenuDTO();
            children.setPath(menuDTO.getPath());
            children.setComponent(menuDTO.getComponent());
            children.setName(StringUtils.capitalize(menuDTO.getPath()));
            children.setMeta(new MenuRouteDTO(menuDTO.getName(), menuDTO.getIcon(), menuDTO.getIsCache() == 1, menuDTO.getPath()));
            children.setQuery(menuDTO.getQuery());
            childrenList.add(children);
            menuDTO.setChildren(childrenList);
        } else if (menuDTO.getParentId().intValue() == 0 && isInnerLink(menuDTO)) {
            menuDTO.setMeta(new MenuRouteDTO(menuDTO.getName(), menuDTO.getIcon()));
            menuDTO.setPath("/");
            List<MenuDTO> childrenList = new ArrayList<>();
            MenuDTO children = new MenuDTO();
            String menuVOPath = innerLinkReplaceEach(menuDTO.getPath());
            children.setPath(menuVOPath);
            children.setComponent(MenuConstants.INNER_LINK);
            children.setName(StringUtils.capitalize(menuVOPath));
            children.setMeta(new MenuRouteDTO(menuDTO.getName(), menuDTO.getIcon(), menuDTO.getPath()));
            childrenList.add(children);
            menuDTO.setChildren(childrenList);
        }
        return menuDTO;
    }

    /***
     * 获取子菜单
     *
     * @param id    菜单id
     * @param systemMenuList 根
     * @return list
     */
    private List<MenuDTO> getChild(Long id, List<SysMenu> systemMenuList) {
        List<MenuDTO> childList = systemMenuList.stream()
                //非根路径 且 是id的子目录
                .filter(menu -> menu.getParentId() != 0 && menu.getParentId().equals(id))
                //非按钮
                .filter(menu -> !MenuConstants.TYPE_BUTTON.equals(menu.getMenuType()))
                .map(menu -> {
                    MenuDTO menuDTO = covertMenu(menu);
                    menuDTO.setChildren(getChild(menuDTO.getMenuId(), systemMenuList));
                    return getMenuInfo(menuDTO);
                }).collect(Collectors.toList());
        if (childList.isEmpty()) {
            return Collections.emptyList();
        }
        return childList;
    }

    private MenuDTO covertMenu(SysMenu menu) {
        MenuDTO menuDTO = new MenuDTO();
        BeanUtils.copyProperties(menu, menuDTO);
        menuDTO.setHidden("1".equals(menu.getVisible()));
        menuDTO.setName(getRouteName(menu));
        menuDTO.setPath(getRoutePath(menu));
        menuDTO.setComponent(getComponent(menu));
        menuDTO.setQuery(menu.getQuery());
        String link = isInnerLink(menu) ? menu.getPath() : null;
        menuDTO.setMeta(new MenuRouteDTO(menu.getMenuName(), menu.getIcon(), menu.getIsCache() == 1, link));
        return menuDTO;
    }


    public String innerLinkReplaceEach(String path) {
        return StringUtils.replaceEach(path,
                new String[]{MenuConstants.HTTP, MenuConstants.HTTPS, MenuConstants.WWW, "."},
                new String[]{"", "", "", "/"});
    }

    private boolean isInnerLink(MenuDTO menuDTO) {
        return (menuDTO.getIsFrame() == MenuConstants.NO_FRAME)
                && StringUtils.startsWithAny(menuDTO.getPath(), MenuConstants.HTTP, MenuConstants.HTTPS);
    }

    private boolean isInnerLink(SysMenu menuVO) {
        return (menuVO.getIsFrame() == MenuConstants.NO_FRAME)
                && StringUtils.startsWithAny(menuVO.getPath(), MenuConstants.HTTP, MenuConstants.HTTPS);
    }

    private boolean isMenuFrame(MenuDTO menuDTO) {
        return menuDTO.getParentId().intValue() == 0 && MenuConstants.TYPE_MENU.equals(menuDTO.getMenuType())
                && menuDTO.getIsFrame().equals(MenuConstants.NO_FRAME);
    }

    private boolean isMenuFrame(SysMenu menu) {
        return menu.getParentId().intValue() == 0 && MenuConstants.TYPE_MENU.equals(menu.getMenuType())
                && menu.getIsFrame().equals(MenuConstants.NO_FRAME);
    }

    public String getRouteName(SysMenu menu) {
        return StringUtils.capitalize(menu.getPath());
    }

    /**
     * 获取路由地址
     *
     * @param menu 菜单信息
     * @return 路由地址
     */
    public String getRoutePath(SysMenu menu) {
        String path = menu.getPath();
        // 内链打开外网方式
        if (menu.getParentId().intValue() != 0 && isInnerLink(menu)) {
            path = innerLinkReplaceEach(path);
        }
        // 非外链并且是一级目录（类型为目录）
        if (0 == menu.getParentId().intValue() && MenuConstants.TYPE_DIR.equals(menu.getMenuType())
                && MenuConstants.NO_FRAME == menu.getIsFrame()) {
            path = "/" + menu.getPath();
        }
        // 非外链并且是一级目录（类型为菜单）
        else if (isMenuFrame(menu)) {
            path = "/";
        }
        return path;
    }

    /**
     * 获取组件信息
     *
     * @param menu 菜单信息
     * @return 组件信息
     */
    public String getComponent(SysMenu menu) {
        String component = MenuConstants.LAYOUT;
        if (StringUtils.isNotEmpty(menu.getComponent()) && !isMenuFrame(menu)) {
            component = menu.getComponent();
        } else if (StringUtils.isEmpty(menu.getComponent()) && menu.getParentId().intValue() != 0
                && isInnerLink(menu)) {
            component = MenuConstants.INNER_LINK;
        } else if (StringUtils.isEmpty(menu.getComponent()) && menu.getParentId().intValue() != 0
                && MenuConstants.TYPE_DIR.equals(menu.getMenuType())) {
            component = MenuConstants.PARENT_VIEW;
        }
        return component;
    }

}




