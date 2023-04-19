package com.ikikyou.practice.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ikikyou.practice.constant.MenuConstants;
import com.ikikyou.practice.vo.MenuRouteVO;
import com.ikikyou.practice.vo.MenuVO;
import com.ikikyou.practice.entity.system.SysMenu;
import com.ikikyou.practice.service.SysMenuService;
import com.ikikyou.practice.mapper.SysMenuMapper;
import com.ikikyou.practice.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    public List<MenuVO> buildMenus() {
        List<Long> roles = SecurityUtil.getRoles();
        if (CollectionUtil.isEmpty(roles)) {
            return Collections.emptyList();
        }
        List<SysMenu> userSysMenus = menuMapper.getByRoleIds(roles);
        return getTreeMenu(userSysMenus);
    }

    /**
     * 生成菜单树
     *
     * @param menuList 一级菜单及其子菜单
     * @return 菜单树
     */
    private List<MenuVO> getTreeMenu(List<SysMenu> menuList) {
        List<MenuVO> rootMenu = new ArrayList<>();
        for (SysMenu menu : menuList) {
            if (menu.getParentId() == 0) {
                MenuVO menuVO = covertMenu(menu);
                menuVO.setChildren(getChild(menuVO.getMenuId(), menuList));
                rootMenu.add(getMenuInfo(menuVO));
            }
        }
        return rootMenu;
    }

    /**
     * 赋值
     *
     * @param menuVO 菜单对象
     * @return 菜单对象
     */
    private MenuVO getMenuInfo(MenuVO menuVO) {
        List<MenuVO> subMenuList = menuVO.getChildren();
        if (CollectionUtil.isNotEmpty(subMenuList) && MenuConstants.TYPE_DIR.equals(menuVO.getMenuType())) {
            menuVO.setAlwaysShow(true);
            menuVO.setRedirect("noRedirect");
        } else if (isMenuFrame(menuVO)) {
            menuVO.setMeta(null);
            List<MenuVO> childrenList = new ArrayList<>();
            MenuVO children = new MenuVO();
            children.setPath(menuVO.getPath());
            children.setComponent(menuVO.getComponent());
            children.setName(StringUtils.capitalize(menuVO.getPath()));
            children.setMeta(new MenuRouteVO(menuVO.getName(), menuVO.getIcon(), menuVO.getIsCache() == 1, menuVO.getPath()));
            children.setQuery(menuVO.getQuery());
            childrenList.add(children);
            menuVO.setChildren(childrenList);
        } else if (menuVO.getParentId().intValue() == 0 && isInnerLink(menuVO)) {
            menuVO.setMeta(new MenuRouteVO(menuVO.getName(), menuVO.getIcon()));
            menuVO.setPath("/");
            List<MenuVO> childrenList = new ArrayList<>();
            MenuVO children = new MenuVO();
            String menuVOPath = innerLinkReplaceEach(menuVO.getPath());
            children.setPath(menuVOPath);
            children.setComponent(MenuConstants.INNER_LINK);
            children.setName(StringUtils.capitalize(menuVOPath));
            children.setMeta(new MenuRouteVO(menuVO.getName(), menuVO.getIcon(), menuVO.getPath()));
            childrenList.add(children);
            menuVO.setChildren(childrenList);
        }
        return menuVO;
    }

    public String innerLinkReplaceEach(String path) {
        return StringUtils.replaceEach(path, new String[]{MenuConstants.HTTP, MenuConstants.HTTPS, MenuConstants.WWW, "."},
                new String[]{"", "", "", "/"});
    }

    private boolean isInnerLink(MenuVO menuVO) {
        return (menuVO.getIsFrame() == MenuConstants.NO_FRAME) && StringUtils.startsWithAny(menuVO.getPath(), MenuConstants.HTTP, MenuConstants.HTTPS);
    }

    private boolean isInnerLink(SysMenu menuVO) {
        return (menuVO.getIsFrame() == MenuConstants.NO_FRAME) && StringUtils.startsWithAny(menuVO.getPath(), MenuConstants.HTTP, MenuConstants.HTTPS);
    }

    private boolean isMenuFrame(MenuVO menuVO) {
        return menuVO.getParentId().intValue() == 0 && MenuConstants.TYPE_MENU.equals(menuVO.getMenuType())
                && menuVO.getIsFrame().equals(MenuConstants.NO_FRAME);
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
        } else if (StringUtils.isEmpty(menu.getComponent()) && menu.getParentId().intValue() != 0 && isInnerLink(menu)) {
            component = MenuConstants.INNER_LINK;
        } else if (StringUtils.isEmpty(menu.getComponent()) && menu.getParentId().intValue() != 0 && MenuConstants.TYPE_DIR.equals(menu.getMenuType())) {
            component = MenuConstants.PARENT_VIEW;
        }
        return component;
    }


    /***
     * 获取子菜单
     *
     * @param id    菜单id
     * @param systemMenuList 根
     * @return list
     */
    private List<MenuVO> getChild(Long id, List<SysMenu> systemMenuList) {
        List<MenuVO> childList = new ArrayList<>();
        for (SysMenu menu : systemMenuList) {
            if (MenuConstants.TYPE_BUTTON.equals(menu.getMenuType())) {
                continue;
            }
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (menu.getParentId() != 0 && menu.getParentId().equals(id)) {
                MenuVO menuVO = covertMenu(menu);
                menuVO.setChildren(getChild(menuVO.getMenuId(), systemMenuList));
                childList.add(getMenuInfo(menuVO));
            }
        }
        if (childList.isEmpty()) {
            return Collections.emptyList();
        }
        return childList;
    }

    private MenuVO covertMenu(SysMenu menu) {
        MenuVO menuVO = new MenuVO();
        BeanUtils.copyProperties(menu, menuVO);
        menuVO.setHidden("1".equals(menu.getVisible()));
        menuVO.setName(getRouteName(menu));
        menuVO.setPath(getRoutePath(menu));
        menuVO.setComponent(getComponent(menu));
        menuVO.setQuery(menu.getQuery());
        String link = isInnerLink(menu) ? menu.getPath() : null;
        menuVO.setMeta(new MenuRouteVO(menu.getMenuName(), menu.getIcon(), menu.getIsCache() == 1, link));
        return menuVO;
    }
}




