package com.ikikyou.practice.constant;

/**
 * @author ikikyou
 * @date 2023/03/21 13:52
 */
public interface SecurityConstants {

    String AUTHORIZATION_HEADER = "Authorization";
    String TOKEN_PREFIX = "Bearer ";

    String JWT_USER_KEY = "ikikyou_practice";
    String USER_TOKEN_PREFIX = "ikikyou_com_";
    String BASIC_PREFIX = "Basic ";
    String BCRYPT = "{bcrypt}";
    String ROLE = "ROLE_";
    String USER_TYPE = "user_type";
}
