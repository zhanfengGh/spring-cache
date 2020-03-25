package com.feng.learn.github.springcache.infrastructure.mybatisplus;

import static com.feng.learn.github.springcache.infrastructure.spring.cache.SpringCacheConfig.JVM_CACHE_Manage;

import com.feng.learn.github.springcache.domain.model.SomeModel;
import com.feng.learn.github.springcache.domain.repository.SomeRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

/**
 * @author zhanfeng.zhang
 * @date 2020/03/25
 */
@Repository
public class SomeRepositoryImpl implements SomeRepository {

    @Override
    @Cacheable(cacheManager = JVM_CACHE_Manage,
        cacheNames = {"someCache", "anotherCache"},
        key = "'/sm/' + #root.args[0]")
    public SomeModel getById(Long id) {
        try {
            // simulate loading From Db
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            // ignore
        }
        return new SomeModel(id).setName("someModel");
    }

    @CachePut(cacheManager = JVM_CACHE_Manage,
        cacheNames = {"someCache", "anotherCache"},
        key = "'/sm/' + #root.args[0]")
    @Override
    public int updateById(SomeModel someModel) {
        return 0;
    }

    @CacheEvict(cacheManager = JVM_CACHE_Manage,
        cacheNames = {"someCache", "anotherCache"},
        key = "'/sm/' + #root.args[0]")
    @Override
    public int deleteById(SomeModel someModel) {
        return 0;
    }
}
