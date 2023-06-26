package com.ikikyou.practice.model.query;

import lombok.Getter;
import lombok.Setter;

/**
 * @author hongx
 * @date 2023/06/16 09:14
 */
@Setter
@Getter
public class NoticeQuery extends BaseQuery{


    private Long noticeId;

    /**
     * 标题
     */
    private String noticeTitle;

    /**
     * 内容
     */
    private String noticeContent;

    /**
     * 发布人
     */
    private String noticePublishUser;
}
