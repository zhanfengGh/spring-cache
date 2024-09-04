package com.feng.learn.github.springcache.infra.spring.cache;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhanfeng.zhang
 * @date 2020/03/25
 */
@Configuration
@EnableCaching
public class SpringCacheConfig {

    public static final String JVM_MAP_CACHE_MANAGER = "jvmMapCacheManage";

    @Bean(JVM_MAP_CACHE_MANAGER)
    public CacheManager jvmCacheManager() {
        return new ConcurrentMapCacheManager();
    }

}
