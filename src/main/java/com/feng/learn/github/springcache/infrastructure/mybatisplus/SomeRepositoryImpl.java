package com.feng.learn.github.springcache.infrastructure.mybatisplus;

import static com.feng.learn.github.springcache.infrastructure.spring.cache.SpringCacheConfig.JVM_CACHE_MANAGE;

import com.feng.learn.github.springcache.domain.model.SomeModel;
import com.feng.learn.github.springcache.domain.repository.SomeRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

/**
 * @author zhanfeng.zhang
 * @date 2020/03/25
 */
@Repository
@CacheConfig(cacheNames = {"someCache"}, cacheManager = JVM_CACHE_MANAGE)
public class SomeRepositoryImpl implements SomeRepository {

    @Override
    @Cacheable(key = "'/sm/' + #root.args[0]")
    public SomeModel getById(Long id) {
        try {
            // simulate loading From Db
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            // ignore
        }
        return new SomeModel(id).setName("someModel");
    }

    @CachePut(cacheNames = {"someCache", "anotherCache"},
        key = "'/sm/' + #root.args[0]")
    @Override
    public int updateById(SomeModel someModel) {
        return 0;
    }

    @CacheEvict(cacheManager = JVM_CACHE_MANAGE,
        cacheNames = {"someCache", "anotherCache"},
        key = "'/sm/' + #root.args[0]")
    @Override
    public int deleteById(SomeModel someModel) {
        return 0;
    }
}
