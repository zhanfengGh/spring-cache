package com.feng.learn.github.springcache.domain.service;

import com.feng.learn.github.springcache.domain.model.SomeModel;
import com.feng.learn.github.springcache.domain.repository.SomeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author zhanfeng.zhang
 * @date 2020/03/25
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
@RequiredArgsConstructor
public class SomeService {

    @Qualifier(SomeRepository.CAFFEINE_CACHE_IMPL)
    SomeRepository someRepository;

    public SomeModel getFromRepository(Long id) {
        return someRepository.getById(id);
    }

}
