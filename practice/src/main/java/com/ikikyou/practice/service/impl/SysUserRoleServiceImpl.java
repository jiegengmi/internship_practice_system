package com.ikikyou.practice.service.impl;

import com.ikikyou.practice.model.entity.system.SysUserRole;
import com.ikikyou.practice.mapper.EntityRelationMapper;
import com.ikikyou.practice.service.SysUserRoleService;
import com.ikikyou.practice.utils.ParamUtil;
import com.ikikyou.practice.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SysUserRoleServiceImpl  implements SysUserRoleService{

    final EntityRelationMapper entityRelationMapper;

    @Override
    @Transactional
    public void deleteRelation(Long id, boolean isUserId) {
        ParamUtil.checkId(id);
        entityRelationMapper.deleteUserRoleRelation(id, isUserId);
        log.warn("用户{}删除{}的所有关联{}", SecurityUtil.getUserName(), isUserId ? "用户" : "角色", isUserId);
    }

    @Override
    @Transactional
    public void insertRelation(Long id, List<Long> ids, boolean isUser) {
        ParamUtil.checkId(id);
        List<SysUserRole> userRoles = ids.stream().map(e -> {
            SysUserRole userRole = new SysUserRole();
            if (isUser) {
                userRole.setUserId(id);
                userRole.setRoleId(e);
            } else {
                userRole.setUserId(e);
                userRole.setRoleId(id);
            }
            return userRole;
        }).collect(Collectors.toList());
        entityRelationMapper.insertUserRoleRelation(userRoles);
    }
}




