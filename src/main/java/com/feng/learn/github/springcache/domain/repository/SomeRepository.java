package com.feng.learn.github.springcache.domain.repository;

import com.feng.learn.github.springcache.domain.model.SomeModel;

/**
 * @author zhanfeng.zhang
 * @date 2020/03/25
 */
public interface SomeRepository {

    String DEFAULT_IMPL = "defaultSomeRepositoryImpl";
    String MAP_CACHE_IMPL = "mapCacheSomeRepositoryImpl";
    String CAFFEINE_CACHE_IMPL = "caffeineCacheSomeRepositoryImpl";

    SomeModel getById(Long id);

    int updateById(SomeModel someModel);

    int deleteById(SomeModel someModel);
}
