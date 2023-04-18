package com.ikikyou.practice.dto.query;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 角色查询对象
 * @author ikikyou
 * @date 2023/03/28 09:25
 */
@Setter
@Getter
public class RoleQueryDTO extends BaseQueryDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -7224578716070533558L;
    private String roleName;
}
