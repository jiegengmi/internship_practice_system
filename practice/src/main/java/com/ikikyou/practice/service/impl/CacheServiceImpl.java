package com.ikikyou.practice.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.ikikyou.practice.constant.CacheConstants;
import com.ikikyou.practice.model.server.SysCache;
import com.ikikyou.practice.model.dto.CacheDTO;
import com.ikikyou.practice.service.CacheService;
import com.ikikyou.practice.utils.Result;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.connection.DefaultedRedisConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author ikikyou
 * @date 2023/05/12 09:13
 */
@Service
public class CacheServiceImpl implements CacheService {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    private final static List<SysCache> caches = new ArrayList<>();
    static {
        caches.add(new SysCache(CacheConstants.LOGIN_TOKEN_KEY, "用户信息"));
        caches.add(new SysCache(CacheConstants.SYS_CONFIG_KEY, "配置信息"));
        caches.add(new SysCache(CacheConstants.SYS_DICT_KEY, "数据字典"));
        caches.add(new SysCache(CacheConstants.CAPTCHA_CODE_KEY, "验证码"));
        caches.add(new SysCache(CacheConstants.REPEAT_SUBMIT_KEY, "防重提交"));
        caches.add(new SysCache(CacheConstants.RATE_LIMIT_KEY, "限流处理"));
        caches.add(new SysCache(CacheConstants.PWD_ERR_CNT_KEY, "密码错误次数"));
    }
    /**
     * 获取redis相关信息
     */
    @Override
    public Result<CacheDTO> getCacheInfo() {
        Properties info = (Properties) redisTemplate.execute((RedisCallback<Object>) DefaultedRedisConnection::info);
        Properties commandStats = (Properties) redisTemplate.execute((RedisCallback<Object>) connection -> connection.info("commandstats"));
        Object dbSize = redisTemplate.execute((RedisCallback<Object>) RedisConnection::dbSize);
        CacheDTO cacheDTO = new CacheDTO();
        cacheDTO.setInfo(info);
        cacheDTO.setDbSize(dbSize == null ? 0 : Integer.parseInt(dbSize.toString()));
        if (commandStats == null) {
            return Result.ok(cacheDTO);
        }
        if (CollectionUtil.isNotEmpty(commandStats.stringPropertyNames())) {
            List<Map<String, Object>> pieList = new ArrayList<>();
            commandStats.stringPropertyNames().forEach(key -> {
                Map<String, Object> data = new HashMap<>(2);
                String property = commandStats.getProperty(key);
                data.put("name", StringUtils.removeStart(key, "cmdstat_"));
                data.put("value", StringUtils.substringBetween(property, "calls=", ",usec"));
                pieList.add(data);
            });
            cacheDTO.setCommandStats(pieList);
        }
        return Result.ok(cacheDTO);
    }

    @Override
    public Result<List<SysCache>> getNames() {
        return Result.ok(caches);
    }

    /**
     * 获取cacheName开头的所有缓存key
     *
     * @param cacheName 缓存前缀
     * @return 缓存key
     */
    @Override
    public Result<Set<String>> getKeys(String cacheName) {
        return Result.ok(redisTemplate.keys(cacheName + "*"));
    }

    /**
     * @param cacheName 缓存名称
     * @param cacheKey 缓存key
     */
    @Override
    public Result<SysCache> getValue(String cacheName, String cacheKey) {
        String cacheValue = redisTemplate.opsForValue().get(cacheKey);
        return Result.ok(new SysCache(cacheName, cacheKey, cacheValue));
    }

    /**
     * 清除以cacheName开头的所有缓存
     *
     * @param cacheName 缓存名称
     * @return 清除结果
     */
    @Override
    public Result<Void> clearCacheName(String cacheName) {
        Collection<String> cacheKeys = redisTemplate.keys(cacheName + "*");
        if (CollectionUtil.isNotEmpty(cacheKeys)) {
            redisTemplate.delete(cacheKeys);
        }
        return Result.ok();
    }

    /**
     * 清除单个缓存
     *
     * @param cacheKey 缓存的键
     */
    @Override
    public Result<Void> clearCacheKey(String cacheKey) {
        return Boolean.TRUE.equals(redisTemplate.delete(cacheKey)) ? Result.ok() : Result.fail();
    }

    /**
     * 清除所有缓存
     */
    @Override
    public Result<Void> clearCacheAll() {
        Collection<String> cacheKeys = redisTemplate.keys("*");
        if (CollectionUtil.isEmpty(cacheKeys)) {
            return Result.ok();
        }
        redisTemplate.delete(cacheKeys);
        return Result.ok();
    }
}
