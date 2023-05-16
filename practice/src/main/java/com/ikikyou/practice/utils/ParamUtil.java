package com.ikikyou.practice.utils;

import com.ikikyou.practice.common.exception.BusinessException;
import com.ikikyou.practice.constant.MenuConstants;
import org.apache.commons.lang3.StringUtils;

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

    public static boolean ishttp(String link) {
        return StringUtils.startsWithAny(link, MenuConstants.HTTP, MenuConstants.HTTPS);
    }
}
