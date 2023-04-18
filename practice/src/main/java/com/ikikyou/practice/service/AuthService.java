package com.ikikyou.practice.service;

import com.ikikyou.practice.utils.Result;

import java.util.Map;

/**
 * 认证接口
 * @author ikikyou
 * @date 2023/04/13 14:06
 */
public interface AuthService {

    /**
     * 获取登录图片认证
     *
     * @return 图片信息
     */
    Result<Map<String, Object>> getCaptchaImage();
}
