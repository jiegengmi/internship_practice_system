package com.ikikyou.practice.model.dto;

import com.ikikyou.practice.model.entity.system.SysDept;
import com.ikikyou.practice.model.entity.system.SysRole;
import com.ikikyou.practice.model.entity.system.SysUser;
import lombok.*;

import java.io.Serial;
import java.util.List;


/**
 * 用户信息传输对象
 *
 * @author ikikyou
 * @date 2023/03/24 09:04
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO extends SysUser {

    @Serial
    private static final long serialVersionUID = 9172970910712344008L;

    private SysDept dept;

    private List<SysRole> roles;
}
