package com.feng.learn.github.springcache.infrastructure.mybatisplus;

import com.feng.learn.github.springcache.domain.model.SomeModel;
import com.feng.learn.github.springcache.domain.repository.SomeRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zhanfeng.zhang
 * @date 2020/03/25
 */
@Repository
public class SomeRepositoryImpl implements SomeRepository {

    @Override
    public SomeModel getById(Long id) {
        try {
            // simulate loading From Db
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            // ignore
        }
        return new SomeModel(id).setName("someModel");
    }
}
