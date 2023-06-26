package com.ikikyou.practice.enums;

import lombok.Getter;

/**
 * @author ikikyou
 * @date 2023/03/28 12:51
 */
@Getter
public enum UserStatusEnums {

    ENABLE("1", "启用"),
    DISABLE("0", "禁用"),

    /**
     * 逻辑删除
     */
    CANCELLATION("-1", "注销");

    private final String code;

    private final String message;

    UserStatusEnums(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
