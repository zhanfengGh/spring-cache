package com.feng.learn.github.springcache.domain.service;

import com.feng.learn.github.springcache.domain.model.SomeModel;
import com.feng.learn.github.springcache.domain.repository.SomeRepository;
import com.sun.istack.internal.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * @author zhanfeng.zhang
 * @date 2020/03/25
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class SomeService {

    SomeRepository someRepository;

    public SomeModel getFromRepository(@NotNull Long id) {
        return someRepository.getById(id);
    }

}
