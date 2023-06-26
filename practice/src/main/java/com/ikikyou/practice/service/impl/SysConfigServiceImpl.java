package com.ikikyou.practice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ikikyou.practice.model.entity.system.SysConfig;
import com.ikikyou.practice.service.SysConfigService;
import com.ikikyou.practice.mapper.SysConfigMapper;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
* @author 25726
* @description 针对表【sys_config(参数配置表)】的数据库操作Service实现
* @createDate 2023-04-13 14:27:39
*/
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService{

    @Override
    @Caching(cacheable = {
            @Cacheable( value = "sysConfig")
    }, put = {
            @CachePut(value = "sysConfig")
    })
    public SysConfig getByKey(String configKey) {
        Objects.requireNonNull(configKey, "配置名称不可为空");
        return getOne(new LambdaQueryWrapper<SysConfig>().eq(SysConfig::getConfigKey, configKey));
    }
}




