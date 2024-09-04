package com.feng.learn.github.springcache.infra.spring.cache;

import com.feng.learn.github.springcache.domain.model.SomeModel;
import com.feng.learn.github.springcache.domain.repository.SomeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import static com.feng.learn.github.springcache.domain.repository.SomeRepository.CAFFEINE_CACHE_IMPL;

@SpringBootTest
class SpringCaffeineCacheConfigTest {

    @Autowired
    @Qualifier(CAFFEINE_CACHE_IMPL)
    SomeRepository someRepository;

    /**
     * 打断点 debug
     */
    @Test
    void given_when_then() {
        long id = 1L;
        SomeModel sm = someRepository.getById(id);
        // hit cache
        someRepository.getById(id);
        // delete cache
        someRepository.deleteById(sm);
        // miss cache
        someRepository.getById(id);
        // hit cache
        someRepository.getById(id);
    }

}