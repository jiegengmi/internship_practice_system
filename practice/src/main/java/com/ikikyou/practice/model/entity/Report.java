package com.ikikyou.practice.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 报告（用于月报、实习报告、汇报内容等）
 * @TableName report
 */
@TableName(value ="report")
@Data
public class Report implements Serializable {
    /**
     * 
     */
    @TableId
    private Long id;

    /**
     * 报告年份
     */
    private Integer reportYear;

    /**
     * 报告期号
     */
    private Integer reportNum;

    /**
     * 报告名称
     */
    private String reportTitle;

    /**
     * 报告摘要
     */
    private String reportSummary;

    /**
     * 报告正文
     */
    private String reportBody;

    /**
     * 报告类型
     */
    private String reportType;

    /**
     * 报告作者id
     */
    private Long reportAuthorId;

    /**
     * 作者昵称
     */
    private String reportAuthorName;

    /**
     * 报告附件id
     */
    private Long reportFileId;

    /**
     * 报告文件名称（存储系统中的文件名）
     */
    private String reportFileName;

    /**
     * 附件原文件名称
     */
    private String reportFileDesc;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 状态
     */
    private Integer status;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}