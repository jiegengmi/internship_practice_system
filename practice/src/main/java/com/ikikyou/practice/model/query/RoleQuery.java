package com.ikikyou.practice.model.query;

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
public class RoleQuery extends BaseQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = -7224578716070533558L;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色标识
     */
    private String roleKey;


    private Long roleId;
}
