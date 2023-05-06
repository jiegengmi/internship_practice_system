package com.ikikyou.practice.service.impl;

import com.ikikyou.practice.entity.system.SysUserPost;
import com.ikikyou.practice.mapper.EntityRelationMapper;
import com.ikikyou.practice.service.SysUserPostService;
import com.ikikyou.practice.utils.ParamUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author 25726
* @description 针对表【sys_user_post(用户与岗位关联表)】的数据库操作Service实现
* @createDate 2023-04-25 13:29:18
*/
@Service
@RequiredArgsConstructor
public class SysUserPostServiceImpl implements SysUserPostService{

    final EntityRelationMapper entityRelationMapper;

    @Override
    @Transactional
    public void deleteRelation(Long id, boolean isUser) {
        ParamUtil.checkId(id);
        entityRelationMapper.deleteUserPostRelation(id, isUser);
    }

    @Override
    @Transactional
    public void insertRelation(Long id, List<Long> ids, boolean isUser) {
        ParamUtil.checkId(id);
        List<SysUserPost> userPosts = ids.stream().map(e -> {
            SysUserPost userPost = new SysUserPost();
            if (isUser) {
                userPost.setUserId(id);
                userPost.setPostId(e);
            } else {
                userPost.setUserId(e);
                userPost.setPostId(id);
            }
            return userPost;
        }).collect(Collectors.toList());
        entityRelationMapper.insertUserPostRelation(userPosts);
    }
}




