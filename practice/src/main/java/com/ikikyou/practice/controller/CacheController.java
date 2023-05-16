package com.ikikyou.practice.controller;

import com.ikikyou.practice.model.server.SysCache;
import com.ikikyou.practice.service.CacheService;
import com.ikikyou.practice.utils.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 缓存监控
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/monitor/cache")
@RequiredArgsConstructor
public class CacheController {

    private final CacheService cacheService;

    @PreAuthorize("@hasAuthority('monitor:cache:list')")
    @GetMapping()
    public Result<?> getInfo() {
        return cacheService.getCacheInfo();
    }

    @PreAuthorize("@hasAuthority('monitor:cache:list')")
    @GetMapping("/getNames")
    public Result<?> cache() {
        return cacheService.getNames();
    }

    @PreAuthorize("@hasAuthority('monitor:cache:list')")
    @GetMapping("/getKeys/{cacheName}")
    public Result<Set<String>> getCacheKeys(@PathVariable String cacheName) {
        return cacheService.getKeys(cacheName);
    }

    @PreAuthorize("@hasAuthority('monitor:cache:list')")
    @GetMapping("/getValue/{cacheName}/{cacheKey}")
    public Result<SysCache> getCacheValue(@PathVariable String cacheName, @PathVariable String cacheKey) {
        return cacheService.getValue(cacheName, cacheKey);
    }

    @PreAuthorize("@hasAuthority('monitor:cache:list')")
    @DeleteMapping("/clearCacheName/{cacheName}")
    public Result<Void> clearCacheName(@PathVariable String cacheName) {
        return cacheService.clearCacheName(cacheName);
    }

    @PreAuthorize("@hasAuthority('monitor:cache:list')")
    @DeleteMapping("/clearCacheKey/{cacheKey}")
    public Result<Void> clearCacheKey(@PathVariable String cacheKey) {
        return cacheService.clearCacheKey(cacheKey);
    }

    @PreAuthorize("@hasAuthority('monitor:cache:list')")
    @DeleteMapping("/clearCacheAll")
    public Result<Void> clearCacheAll() {
        return cacheService.clearCacheAll();
    }
}
