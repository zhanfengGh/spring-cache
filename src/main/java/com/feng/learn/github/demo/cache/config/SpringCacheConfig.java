package com.feng.learn.github.demo.cache.config;

import com.feng.learn.github.demo.cache.config.jvmredis.ComposeCacheManager;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.cache.support.NoOpCache;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

import java.time.Duration;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * @author zhanfeng.zhang
 * @date 2020/04/14
 */
@Configuration
@EnableCaching
public class SpringCacheConfig {

    public static final String REDIS_CACHE_MANAGER = "redisCacheManager";

    public static final String REDIS_CACHE_NAME_1 = "redis_1";
    public static final int REDIS_CACHE_NAME_1_TTL = 4;
    private static final RedisCacheConfiguration REDIS_CACHE_NAME_1_CONF = RedisCacheConfiguration
            .defaultCacheConfig()
            .serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
            .entryTtl(Duration.ofSeconds(REDIS_CACHE_NAME_1_TTL));

    public static final String REDIS_CACHE_NAME_2 = "redis_2";
    private static final RedisCacheConfiguration REDIS_CACHE_NAME_2_CONF = RedisCacheConfiguration
            .defaultCacheConfig()
            .serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
            .entryTtl(Duration.ofMinutes(2));


    public static final String LOCAL_REMOTE_CACHE_MANAGER = "localRemoteComposeCacheManager";
    public static final int LOCAL_REMOTE_CACHE_LOCAL_TTL = 2;
    public static final String CACHE_1 = "lr1";
    public static final String CACHE_2 = "lr2";

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration());
    }

    @Bean(LOCAL_REMOTE_CACHE_MANAGER)
    public ComposeCacheManager localRemoteComposeCacheManager(RedisConnectionFactory factory) {
        // local cache
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        Caffeine<Object, Object> caffeine = Caffeine.newBuilder()
                .expireAfterWrite(LOCAL_REMOTE_CACHE_LOCAL_TTL, TimeUnit.SECONDS)
                .maximumSize(1024);
        caffeineCacheManager.setCaffeine(caffeine);
        // remote cache
        RedisCacheConfiguration defaultConf = RedisCacheConfiguration.defaultCacheConfig()
                // 默认16分钟
                .entryTtl(Duration.ofMinutes(16))
                .serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
        RedisCacheManager redisCacheManager = RedisCacheManager.builder(factory)
                .cacheDefaults(defaultConf)
                // 特殊配置
                .withCacheConfiguration(CACHE_1, REDIS_CACHE_NAME_1_CONF)
                .withCacheConfiguration(CACHE_2, REDIS_CACHE_NAME_2_CONF)
                .build();
        // very important: do not forget to exec bean init logic.
        redisCacheManager.afterPropertiesSet();
        return new ComposeCacheManager(caffeineCacheManager, redisCacheManager);
    }

    @Bean
    public SimpleCacheManager simpleCacheManager() {
        // 需手动定义 Cache
        SimpleCacheManager manager = new SimpleCacheManager();
        //
        NoOpCache noOpCache = new NoOpCache("noOpCache");
        ConcurrentMapCache concurrentMapCache = new ConcurrentMapCache("concurrentMapCache");
        //
        manager.setCaches(Arrays.asList(noOpCache, concurrentMapCache));
        return manager;
    }

    @Bean
    @Primary
    public NoOpCacheManager noOpCacheManager() {
        return new NoOpCacheManager();
    }

    @Bean
    public CompositeCacheManager compositeCacheManager() {
        return new CompositeCacheManager();
    }

    @Bean(REDIS_CACHE_MANAGER)
    public RedisCacheManager redisCacheManager(RedisConnectionFactory factory) {
        RedisCacheConfiguration defaultConf = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(16))
                .serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
        return RedisCacheManager.builder(factory)
                .disableCreateOnMissingCache()
                .cacheDefaults(defaultConf)
                .initialCacheNames(Set.of(CACHE_1))
                .withCacheConfiguration(REDIS_CACHE_NAME_1, REDIS_CACHE_NAME_1_CONF)
                .withCacheConfiguration(REDIS_CACHE_NAME_2, REDIS_CACHE_NAME_2_CONF)
                .build();
    }

    @Bean
    public ConcurrentMapCacheManager concurrentMapCacheManager() {
        return new ConcurrentMapCacheManager();
    }

}
