package com.ikikyou.practice.enums;

import lombok.Getter;

/**
 * 文件类型枚举
 *
 * @author kikyou
 * @date 2023/06/07 09:08
 */
@Getter
public enum FileTypeEnums {

    /**
     * 通知公告文件
     */
    NOTICE(1),

    /**
     * 报告（月报等）
     */
    REPORT(2),

    /**
     * 其他文件
     */
    OTHER(99);

    FileTypeEnums(int fileType) {
        this.fileType = fileType;
    }

    private final int fileType;
}
