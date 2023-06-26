package com.ikikyou.practice.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 通知公告
 * @TableName notice
 */
@TableName(value ="notice")
@Data
public class Notice implements Serializable {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 8234366648492412653L;
    /**
     * 
     */
    @TableId
    private Long id;

    /**
     * 编号
     */
    private String noticeId;

    /**
     * 标题
     */
    private String noticeTitle;

    /**
     * 发布作者
     */
    private String noticeAuthorName;

    /**
     * 作者用户id
     */
    private Long noticeAuthorId;

    /**
     * 内容
     */
    private String noticeContent;

    /**
     * 类型
     */
    private String noticeType;

    /**
     * 告知形式（邮件、站内推送、弹窗等）
     */
    private Integer informType;

    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 最后一次修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 状态
     */
    private Integer status;
}