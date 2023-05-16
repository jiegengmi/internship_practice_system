package com.ikikyou.practice.model.server;

import lombok.Data;

/**
 * 系统相关信息
 *
 * @author hongx
 * @date 2023/05/12 10:47
 */
@Data
public class ServerSystem {

    /**
     * 服务器名称
     */
    private String computerName;

    /**
     * 服务器Ip
     */
    private String computerIp;

    /**
     * 项目路径
     */
    private String userDir;

    /**
     * 操作系统
     */
    private String osName;

    /**
     * 系统架构
     */
    private String osArch;
}
