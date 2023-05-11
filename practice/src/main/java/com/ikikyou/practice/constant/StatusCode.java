package com.ikikyou.practice.constant;

/**
 * 枚举状态码
 *
 * @author apoco
 */
public class StatusCode {
    /**
     * 成功
     */
    public static final int OK = 200;
    /**
     * 失败
     */
    public static final int ERROR = 500;

    public static final int A = 401;
    /**
     * 用户名未绑定
     */
    public static final int USER_NOT_BIND = 2;

    /**
     * 请求被限制
     */
    public static final int REQUEST_LIMITED = 3;
}
