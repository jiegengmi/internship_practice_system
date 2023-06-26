package com.ikikyou.practice.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ikikyou.practice.model.entity.system.SysLog;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * 用户首页信息流转对象
 *
 * @author ikikyou
 * @date 2023/05/18 17:01
 */
@Data
@Builder
public class UserIndexInfoDTO {

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户上一次登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date lastLoginTime;

    /**
     * 用户上一次登录ip
     */
    private String lastLoginIp;

    /**
     * 用户上一次登录地址
     */
    private String lastLoginAddress;

    //TODO 站内消息？ 未读邮件？
}
