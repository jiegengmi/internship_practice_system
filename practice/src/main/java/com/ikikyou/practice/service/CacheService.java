package com.ikikyou.practice.service;

import com.ikikyou.practice.model.server.SysCache;
import com.ikikyou.practice.model.dto.CacheDTO;
import com.ikikyou.practice.utils.Result;

import java.util.List;
import java.util.Set;

/**
 * @author ikikyou
 * @date 2023/05/12 09:05
 */
public interface CacheService {

    /**
     * 获取redis相关信息
     */
    Result<CacheDTO> getCacheInfo();

    /**
     * 获取系统缓存键
     */
    Result<List<SysCache>> getNames();

    /**
     * 获取cacheName开头的所有缓存key
     *
     * @param cacheName 缓存前缀
     * @return 缓存key
     */
    Result<Set<String>> getKeys(String cacheName);

    /**
     * 获取key对应值
     *
     * @param cacheName 缓存名称
     * @param cacheKey  缓存key
     */
    Result<SysCache> getValue(String cacheName, String cacheKey);

    /**
     * 清除以cacheName开头的所有缓存
     *
     * @param cacheName 缓存名称
     * @return 清除结果
     */
    Result<Void> clearCacheName(String cacheName);

    /**
     * 清除单个缓存
     *
     * @param cacheKey 缓存的键
     */
    Result<Void> clearCacheKey(String cacheKey);

    /**
     * 清除所有缓存
     */
    Result<Void> clearCacheAll();
}
