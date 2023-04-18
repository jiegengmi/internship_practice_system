package com.ikikyou.practice.service;

import com.ikikyou.practice.entity.system.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ikikyou.practice.dto.MenuDTO;

import java.util.List;

/**
* @author 25726
* @description 针对表【sys_menu(权限)】的数据库操作Service
* @createDate 2023-03-21 11:10:58
*/
public interface SysMenuService extends IService<SysMenu> {

    List<MenuDTO> buildMenus();
}
