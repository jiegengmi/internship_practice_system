package com.ikikyou.practice.model.dto;

import com.ikikyou.practice.model.entity.system.SysUser;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * 用户基本信息对象
 *
 * @author ikikyou
 * @date 2023/03/21 10:40
 */
@Data
public class UserInfoDTO {

    private SysUser user;

    private List<Long> roleIds;

    private Set<String> roles;

    private Set<String> permissions;
}
