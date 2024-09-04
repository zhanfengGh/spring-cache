package com.feng.learn.github.demo.cache.config.degrade;

import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.AbstractCacheResolver;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;

import java.lang.reflect.Method;
import java.util.Collection;

public class DegradeCacheResolver extends AbstractCacheResolver {

    private volatile boolean cacheSwitchOn = true;

    /**
     * Construct a new {@code SimpleCacheResolver} for the given {@link CacheManager}.
     *
     * @param cacheManager the CacheManager to use
     */
    public DegradeCacheResolver(CacheManager cacheManager) {
        super(cacheManager);
    }

    public void switchCacheOff() {
        cacheSwitchOn = false;
    }

    @Override
    protected Collection<String> getCacheNames(CacheOperationInvocationContext<?> context) {
        String key = getCacheSwitchKey(context.getMethod());
        // 根据 key 判断是否关闭 cache
        if (cacheSwitchOn) {
            return context.getOperation().getCacheNames();
        }
        // 返回 NoOpCache
        return null;
    }

    private String getCacheSwitchKey(Method method) {
        String switchKey;
        CacheDegrade cacheSwitch = method.getDeclaringClass().getDeclaredAnnotation(CacheDegrade.class);
        if (cacheSwitch != null && !("".equals(cacheSwitch.value()))) {
            switchKey = cacheSwitch.value();
        } else {
            switchKey = method.getDeclaringClass().getName();
        }
        return switchKey;
    }
}
