package com.feng.learn.github.springcache.infra.mysql;

import com.feng.learn.github.springcache.domain.model.SomeModel;
import com.feng.learn.github.springcache.domain.repository.SomeRepository;
import com.feng.learn.github.springcache.infra.spring.cache.SpringRedisCacheConfig;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;

/**
 * @author zhanfeng.zhang
 * @date 2020/03/26
 */
@Primary
// @Repository
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@CacheConfig(cacheNames = {"someCache"}, cacheManager = SpringRedisCacheConfig.REDIS_CACHE_MANAGE)
public class SomeRepositoryRedisCacheImpl implements SomeRepository {

    SomeRepository someRepository;

    public SomeRepositoryRedisCacheImpl(
        @Qualifier(DEFAULT_IMPL) SomeRepository someRepository) {
        this.someRepository = someRepository;
    }


    @Cacheable(key = "'/sm/' + #root.args[0]")
    @Override
    public SomeModel getById(Long id) {
        return someRepository.getById(id);
    }

    @Override
    public int updateById(SomeModel someModel) {
        return 0;
    }

    @Override
    public int deleteById(SomeModel someModel) {
        return 0;
    }
}
