package com.ikikyou.practice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ikikyou.practice.entity.SysLog;
import com.ikikyou.practice.service.SysLogService;
import com.ikikyou.practice.mapper.SysLogMapper;
import org.springframework.stereotype.Service;

/**
* @author 25726
* @description 针对表【sys_log(系统操作日志)】的数据库操作Service实现
* @createDate 2023-03-30 11:36:05
*/
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog>
    implements SysLogService{

}




