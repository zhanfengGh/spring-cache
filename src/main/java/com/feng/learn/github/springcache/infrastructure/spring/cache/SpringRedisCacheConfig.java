package com.feng.learn.github.springcache.infrastructure.spring.cache;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

/**
 * @author zhanfeng.zhang
 * @date 2020/03/25
 */
//@Configuration
@EnableCaching
public class SpringRedisCacheConfig {

    public static final String REDIS_CACHE_MANAGE = "redisCacheManage";

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration());
    }

    @Primary
    @Bean(REDIS_CACHE_MANAGE)
    public CacheManager cacheManager(RedisConnectionFactory factory) {
       return  RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(factory).build();
    }


}
