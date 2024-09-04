package com.feng.learn.github.demo.cache.config.jvmredis;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.AbstractCacheManager;

import java.util.Collection;
import java.util.Collections;

/**
 * @author zhanfeng.zhang
 * @date 2020/04/15
 */
public class ComposeCacheManager extends AbstractCacheManager {

    final CacheManager localCacheManage;
    final CacheManager remoteCacheManage;

    public ComposeCacheManager(CacheManager localCacheManage,
                               CacheManager remoteCacheManage) {
        if (localCacheManage == null && remoteCacheManage == null) {
            throw new IllegalArgumentException();
        }
        this.localCacheManage = localCacheManage;
        this.remoteCacheManage = remoteCacheManage;
    }

    @Override
    protected Collection<? extends Cache> loadCaches() {
        return Collections.emptyList();
    }

    @Override
    protected Cache getMissingCache(String name) {
        Cache localCache = null, remoteCache = null;
        if (localCacheManage != null) {
            localCache = localCacheManage.getCache(name);
        }
        if (remoteCacheManage != null) {
            remoteCache = remoteCacheManage.getCache(name);
        }
        return new ComposeCache(name, localCache, remoteCache, true);
    }

}
