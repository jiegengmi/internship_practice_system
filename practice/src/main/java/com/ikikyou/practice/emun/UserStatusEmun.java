package com.ikikyou.practice.emun;

/**
 * @author hongx
 * @date 2023/03/28 12:51
 */
public enum UserStatusEmun {

    ENABLE(1, "启用"),
    DISABLE(0, "禁用"),

    /**
     * 逻辑删除
     */
    CANCELLATION(-1, "注销");

    private int code;

    private String message;

    UserStatusEmun(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
