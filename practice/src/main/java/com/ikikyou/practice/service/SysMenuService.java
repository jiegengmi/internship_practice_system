package com.ikikyou.practice.service;

import com.ikikyou.practice.vo.MenuVO;
import com.ikikyou.practice.entity.system.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 25726
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Service
* @createDate 2023-04-18 09:41:22
*/
public interface SysMenuService extends IService<SysMenu> {

    List<MenuVO> buildMenus();
}
