package com.ikikyou.practice.emun;

import lombok.Getter;

/**
 * 系统操作日志类型
 * @author hongx
 * @date 2023/03/30 11:52
 */
@Getter
public enum LogEnum {

    DELETE("删除"), UPDATE("新增"), INSERT("新增"),
    SUCCESS("成功"), FAIL("失败"), ERROR("异常"),
    DEFAULT("");

    private final String type;

    LogEnum(String type) {
        this.type = type;
    }
}
