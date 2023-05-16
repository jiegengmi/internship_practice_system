package com.ikikyou.practice.model.server;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 缓存信息
 *
 * @author ruoyi
 */
@Setter
@Getter
@NoArgsConstructor
public class SysCache {

    /**
     * 缓存名称
     */
    private String cacheName = "";

    /**
     * 缓存键名
     */
    private String cacheKey = "";

    /**
     * 缓存内容
     */
    private String cacheValue = "";

    /**
     * 备注
     */
    private String remark = "";

    public SysCache(String cacheName, String cacheKey, String cacheValue) {
        this.cacheName = cacheName;
        this.cacheKey = cacheKey;
        this.cacheValue = cacheValue;
    }

    public SysCache(String cacheName, String remark) {
        this.cacheName = cacheName;
        this.remark = remark;
    }
}
