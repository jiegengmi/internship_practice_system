package com.ikikyou.practice.service;

import com.ikikyou.practice.entity.system.SysConfig;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 25726
* @description 针对表【sys_config(参数配置表)】的数据库操作Service
* @createDate 2023-04-13 14:27:39
*/
public interface SysConfigService extends IService<SysConfig> {
    /**
     * 获取配置信息
     *
     * @param configKey 配置的名称
     * @return 配置信息
     */
    SysConfig getByKey(String configKey);
}
