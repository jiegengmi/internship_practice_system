package com.ikikyou.practice.mapper;

import com.ikikyou.practice.entity.system.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 25726
* @description 针对表【sys_menu(权限)】的数据库操作Mapper
* @createDate 2023-03-21 11:10:58
* @Entity com.ikikyou.practice.entity.system.SysMenu
*/
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> getByRoleIds(@Param("roleIds") List<Long> roleIds);
}




