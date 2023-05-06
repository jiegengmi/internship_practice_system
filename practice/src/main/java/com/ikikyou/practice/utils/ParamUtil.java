package com.ikikyou.practice.utils;

import com.ikikyou.practice.common.BusinessException;

/**
 * 参数校验工具类
 *
 * @author hongx
 * @date 2023/04/25 14:15
 */
public class ParamUtil {

    public static void checkId(Long id) {
        if (null == id) {
            throw new BusinessException("目标id为空");
        }
    }
}
