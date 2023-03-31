package com.ikikyou.practice.dto;

import com.ikikyou.practice.entity.SysUser;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * 用户基本信息对象
 * @author ikikyou
 * @date 2023/03/21 10:40
 */
@Data
public class UserInfoDTO {

    private SysUser user;

    private List<Long> roleIds;

    private Set<String> permissions;
}
