package com.ikikyou.practice.constant;

/**
 * @author ikikyou
 * @date 2023/03/21 13:52
 */
public class SecurityConstants {

    /**
     * 请求认证头
     */
    public static String AUTHORIZATION_HEADER = "Authorization";
    /**
     * 请求token前缀
     */
    public static String TOKEN_PREFIX = "Bearer ";

    /**
     * jwt的redis key
     */
    public static String JWT_USER_KEY = "ikikyou_practice";
    public static String USER_TOKEN_PREFIX = "ikikyou_com_";
    public static String BASIC_PREFIX = "Basic ";
    public static String BCRYPT = "{bcrypt}";

    /**
     * 角色前缀
     */
    public static String ROLE = "ROLE_";
    public static String USER_TYPE = "user_type";
}
