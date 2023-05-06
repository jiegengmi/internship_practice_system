package com.ikikyou.practice.service;


import java.util.List;

/**
 * 用户角色关联
 */
public interface SysUserRoleService  {

    /**
     * 删除用户和角色关联关系
     *
     * @param id     用户或者角色id
     * @param isUser 是否是删除用户的角色
     */
    void deleteRelation(Long id, boolean isUser);

    /**
     * 新增用户和角色关联
     *
     * @param id     用户或者角色id
     * @param ids    用户或者角色id集合
     * @param isUser 是否是新增用户的角色
     */
    void insertRelation(Long id, List<Long> ids, boolean isUser);
}
