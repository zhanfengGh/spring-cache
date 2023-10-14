package com.feng.learn.github.springcache.infrastructure.mybatisplus;

import com.feng.learn.github.springcache.domain.model.SomeModel;
import com.feng.learn.github.springcache.domain.repository.SomeRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import static com.feng.learn.github.springcache.domain.repository.SomeRepository.DEFAULT_IMPL;

/**
 * @author zhanfeng.zhang
 * @date 2020/03/25
 */
@Primary
@Repository(DEFAULT_IMPL)
public class SomeRepositoryImpl implements SomeRepository {

    @Override
    public SomeModel getById(Long id) {
        return new SomeModel(id).setName("someModel");
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
