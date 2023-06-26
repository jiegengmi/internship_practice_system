package com.ikikyou.practice.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 通知公共流转对象
 *
 * @author kikyou
 * @date 2023/06/16 09:12
 */
@Data
public class NoticeDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -4451386989004981768L;

    private Long id;

    /**
     * 编号
     */
    @NotNull(message = "编号不可为空")
    private String noticeId;

    /**
     * 标题
     */
    @NotNull(message = "标题不可为空")
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
    private Date createTime;

    /**
     * 最后一次修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 状态
     */
    private Integer status;
}
