package com.feng.learn.github.demo.cache.config.degrade;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.NoOpCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * @author zhanfeng.zhang
 * @date 2020/6/7
 */
@Configuration("springCacheConfigSwitch")
@EnableCaching
public class SpringCacheConfig {

    public static final String JVM_CACHE = "jvmCache";
    public static final String CACHE_RESOLVER = "switchAbilityCacheResolver";
    private static final String NO_CACHE = "noOpCache";
    private static final String CACHE_MANAGE = "concurrentMapCacheManager";

    /**
     * 定义 CacheManager
     *
     * @return CacheManager
     */
    @Bean(CACHE_MANAGE)
    public SimpleCacheManager simpleCacheManager() {
        // 需手动定义 Cache
        SimpleCacheManager manager = new SimpleCacheManager();
        //
        NoOpCache noOpCache = new NoOpCache(NO_CACHE);
        ConcurrentMapCache concurrentMapCache = new ConcurrentMapCache(JVM_CACHE);
        //
        manager.setCaches(Arrays.asList(noOpCache, concurrentMapCache));
        return manager;
    }

    @Bean(CACHE_RESOLVER)
    public DegradeCacheResolver switchAbilityCacheResolver(
            @Qualifier(CACHE_MANAGE) CacheManager cacheManager) {
        return new DegradeCacheResolver(cacheManager);
    }

}
