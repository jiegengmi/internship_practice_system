package com.ikikyou.practice.model.entity.system;

import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

@Data
public class SysRoleMenu implements Serializable {
    /**
     * 
     */
    private Long roleId;

    /**
     * 
     */
    private Long menuId;

    @Serial
    private static final long serialVersionUID = 1L;
}