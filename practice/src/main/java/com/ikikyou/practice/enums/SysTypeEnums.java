package com.ikikyou.practice.enums;

import lombok.Getter;

/**
 * @author kikyou
 * @date 2023/06/01 16:13
 */
@Getter
public enum SysTypeEnums {

    /**
     * 部门or组织 业务类型
     */
    TYPE_DEPT("dept");

    SysTypeEnums(String typeDefine) {
        this.typeDefine = typeDefine;
    }

    private final String typeDefine;
}
