package com.ikikyou.practice.service;

import com.ikikyou.practice.entity.system.SysUserPost;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 25726
* @description 针对表【sys_user_post(用户与岗位关联表)】的数据库操作Service
* @createDate 2023-04-25 13:29:18
*/
public interface SysUserPostService {

    /**
     * 删除用户和角色关联关系
     *
     * @param id     用户或者职位id
     * @param isUser 是否是删除用户的职位
     */
    void deleteRelation(Long id, boolean isUser);

    /**
     * 新增用户和角色关联
     *
     * @param id     用户或者职位id
     * @param ids    用户或者角色id集合
     * @param isUser 是否是给用户赋职
     */
    void insertRelation(Long id, List<Long> ids, boolean isUser);
}
