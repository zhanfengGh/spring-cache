package com.feng.learn.github.springcache.infra.mysql;

import com.feng.learn.github.springcache.domain.model.SomeModel;
import com.feng.learn.github.springcache.domain.repository.SomeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import static com.feng.learn.github.springcache.domain.repository.SomeRepository.CAFFEINE_CACHE_IMPL;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Repository(CAFFEINE_CACHE_IMPL)
// @CacheConfig(cacheNames = {"someRepositoryCache"}, cacheManager = JVM_CAFFEINE_CACHE_MANAGE)
// JVM_CAFFEINE_CACHE_MANAGE is the default CacheManger. annotated with @Primary
@CacheConfig(cacheNames = {"someRepositoryCache"})
// 继承的好处：仅需要 Override 需要的方法
public class SomeRepositoryCaffeineCacheImpl extends SomeRepositoryImpl {

    @Qualifier(DEFAULT_IMPL)
    SomeRepository defaultSomeRepository;

    @Override
    // @Cacheable(key = "'/sm/' + #root.args[0]")
    @Cacheable(key = "'/sm/' + #id")
    public SomeModel getById(Long id) {
        return defaultSomeRepository.getById(id);
    }

    // @CacheEvict(key = "'/sm/' + #root.args[0].id")
    @CacheEvict(key = "'/sm/' + #someModel.id")
    @Override
    public int deleteById(SomeModel someModel) {
        return defaultSomeRepository.deleteById(someModel);
    }


}
