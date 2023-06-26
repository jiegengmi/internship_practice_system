package com.ikikyou.practice.model.entity.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统操作日志
 * @TableName sys_log
 */
@TableName(value ="sys_log")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysLog implements Serializable {
    /**
     * 
     */
    @TableId
    private Long id;

    /**
     * 操作人
     */
    private Long userId;

    /**
     * 用户昵称
     */
    private String userNickName;

    /**
     * 用户操作ip地址
     */
    private String userIp;

    /**
     * 具体地址
     */
    private String userIpSource;

    /**
     * 日志类型
     */
    private String type;

    /**
     * 操作url
     */
    private String opUrl;

    /**
     * 方法名
     */
    private String opMethod;

    /**
     * 操作的参数
     */
    private String opParams;

    /**
     * 操作模块
     */
    private String opModule;

    /**
     * 操作模块
     */
    private String module;

    /**
     * 操作内容
     */
    private String content;

    /**
     * 操作结果
     */
    private String result;

    /**
     * 创建时间
     */
    private Date createTime;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}