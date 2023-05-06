package com.ikikyou.practice.mapper;

import com.ikikyou.practice.entity.system.SysRoleMenu;
import com.ikikyou.practice.entity.system.SysUserPost;
import com.ikikyou.practice.entity.system.SysUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 实体中间表操作mapper
 *
 * @author hongx
 * @date 2023/04/25 15:32
 */
public interface EntityRelationMapper {

    /**
     * 删除用户和角色关联的所有关联
     *
     * @param id     用户或者角色id
     * @param isUser true：删除用户的所有角色关联；false：删除角色的所有用户关联
     */
    void deleteUserRoleRelation(@Param("id") Long id, @Param("isUser") boolean isUser);

    /**
     * 新增用户和角色关联
     *
     * @param userRoleList 关联关系集合
     */
    void insertUserRoleRelation(List<SysUserRole> userRoleList);

    /**
     * 删除用户和职位关联的所有关联
     *
     * @param id     用户或者职位id
     * @param isUser true：删除用户的所有角色关联；false：删除角色的所有用户关联
     */
    void deleteUserPostRelation(@Param("id") Long id, @Param("isUser") boolean isUser);

    /**
     * 新增用户和职位关联
     *
     * @param userPostList 关联关系集合
     */
    void insertUserPostRelation(List<SysUserPost> userPostList);

    /**
     * 删除角色和菜单关联的所有关联
     *
     * @param id     菜单或者角色id
     * @param isRole true：删除角色的所有菜单关联；false：删除菜单的所有角色关联
     */
    void deleteRoleMenuRelation(@Param("id") Long id, @Param("isUser") boolean isRole);

    /**
     * 新增用户和职位关联
     *
     * @param roleMenuList 角色和菜单关联关系集合
     */
    void insertRoleMenuRelation(List<SysRoleMenu> roleMenuList);
}
