package com.ikikyou.practice.constant;

/**
 * 公共常量
 *
 * @author ikikyou
 * @date 2023/03/24 09:36
 */
public class CommonConstant {

    /**
     * BCRYPT加解密所用的盐
     */
    public static final String BCRYPT_SALT = "ikikyou";

    /**
     * json响应
     */
    public static final String JSON_CONTENT_TYPE = "application/json;charset=utf-8";

    /**
     * 表单请求类型
     */
    public static final String FORM_CONTENT_TYPE = "application/x-www-form-urlencoded";

    /**
     * 登录接口
     */
    public static final String LOGIN_URL = "/login";
}
