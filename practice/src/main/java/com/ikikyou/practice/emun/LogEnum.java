package com.ikikyou.practice.emun;

/**
 * @author hongx
 * @date 2023/03/30 11:52
 */
public enum LogEnum {
    DELETE("删除"), UPDATE("新增"), INSERT("新增"),
    SUCCESS("成功"), FAIL("失败"), ERROR("异常"),
    DEFAULT("");

    private String type;

    LogEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
