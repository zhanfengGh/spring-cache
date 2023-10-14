package com.feng.learn.github.springcache.infrastructure.spring.cache;

import com.github.benmanes.caffeine.cache.CaffeineSpec;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@EnableCaching
public class SpringCaffeineCacheConfig {

    private String caffeineSpec = "maximumSize=256,expireAfterAccess=2m,expireAfterWrite=8m";

    public static final String JVM_CAFFEINE_CACHE_MANAGE = "jvmCaffeineCacheManage";

    @Bean(JVM_CAFFEINE_CACHE_MANAGE)
    @Primary // 存在多个 CacheManager 时必须制定一个默认的 CacheManager
    public CacheManager jvmCacheManager() {
        CaffeineCacheManager m = new CaffeineCacheManager();
        m.setCaffeineSpec(CaffeineSpec.parse(caffeineSpec));
        return m;
    }

}
