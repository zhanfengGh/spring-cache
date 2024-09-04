package com.feng.learn.github.springcache.infra.mysql;

import com.feng.learn.github.springcache.domain.model.SomeModel;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import static com.feng.learn.github.springcache.domain.repository.SomeRepository.MAP_CACHE_IMPL;
import static com.feng.learn.github.springcache.infra.spring.cache.SpringCacheConfig.JVM_MAP_CACHE_MANAGER;

@Repository(MAP_CACHE_IMPL)
@CacheConfig(cacheNames = {"someCache"}, cacheManager = JVM_MAP_CACHE_MANAGER)
public class SomeRepositoryMapCacheImpl extends SomeRepositoryImpl {

    // 核心点：注意 super 和注入的区别

    @Override
    // @Cacheable(key = "'/sm/' + #root.args[0]") // same
    @Cacheable(key = "'/sm/' + #id")
    public SomeModel getById(Long id) {
        return super.getById(id);
    }

    @CachePut(
            cacheNames = {"someCache", "anotherCache"},
            key = "'/sm/' + #someModel.id"
    )
    @Override
    public int updateById(SomeModel someModel) {
        return super.updateById(someModel);
    }

    @CacheEvict(
            cacheManager = JVM_MAP_CACHE_MANAGER,
            cacheNames = {"someCache", "anotherCache"},
            key = "'/sm/' + #someModel.id"
    )
    @Override
    public int deleteById(SomeModel someModel) {
        return super.deleteById(someModel);
    }

}
