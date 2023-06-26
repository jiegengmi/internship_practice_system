package com.ikikyou.practice.service;

import com.ikikyou.practice.model.entity.system.SysLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 25726
* @description 针对表【sys_log(系统操作日志)】的数据库操作Service
* @createDate 2023-03-30 11:36:05
*/
public interface SysLogService extends IService<SysLog> {

    /**
     * 获取当前登录用户最新登录日志
     *
     * @return {@link SysLog}
     */
    SysLog getByCurUserLoginLog();
}
