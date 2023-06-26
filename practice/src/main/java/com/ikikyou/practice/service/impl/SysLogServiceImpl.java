package com.ikikyou.practice.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ikikyou.practice.enums.LogEnums;
import com.ikikyou.practice.model.entity.system.SysLog;
import com.ikikyou.practice.service.SysLogService;
import com.ikikyou.practice.mapper.SysLogMapper;
import com.ikikyou.practice.utils.SecurityUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 25726
* @description 针对表【sys_log(系统操作日志)】的数据库操作Service实现
* @createDate 2023-03-30 11:36:05
*/
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService{

    /**
     * 获取当前登录用户最新登录日志
     *
     * @return {@link SysLog}
     */
    @Override
    public SysLog getByCurUserLoginLog() {
        List<SysLog> logList = list(new LambdaQueryWrapper<SysLog>()
                .eq(SysLog::getUserId, SecurityUtil.getUserId())
                .eq(SysLog::getType, LogEnums.LOGIN_IN.getType())
                .orderBy(true, false, SysLog::getCreateTime)
                .last(" limit 1")
        );
        if (CollectionUtil.isNotEmpty(logList)) {
            return logList.get(0);
        }
        return null;
    }
}




