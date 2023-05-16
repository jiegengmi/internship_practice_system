package com.ikikyou.practice.controller;

import com.ikikyou.practice.model.server.Server;
import com.ikikyou.practice.utils.Result;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 获取服务器相关信息
 *
 * @author ikikyou
 * @date 2023/05/12 11:13
 */
@RestController
@RequestMapping("/monitor/server")
public class ServerController {

    @PreAuthorize("@hasAuthority('monitor:server:list')")
    @GetMapping()
    public Result<Server> getInfo() {
        Server server = new Server();
        server.copyTo();
        return Result.ok(server);
    }
}
