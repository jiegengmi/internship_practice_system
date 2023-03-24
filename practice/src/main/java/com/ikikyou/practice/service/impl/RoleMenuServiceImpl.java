package com.ikikyou.practice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ikikyou.practice.entity.RoleMenu;
import com.ikikyou.practice.service.RoleMenuService;
import com.ikikyou.practice.mapper.RoleMenuMapper;
import org.springframework.stereotype.Service;

/**
* @author 25726
* @description 针对表【role_menu_link(角色权限中间表)】的数据库操作Service实现
* @createDate 2023-03-24 12:01:10
*/
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu>
    implements RoleMenuService{

}




