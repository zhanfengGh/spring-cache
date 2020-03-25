package com.feng.learn.github.springcache.domain.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

/**
 * @author zhanfeng.zhang
 * @date 2020/03/25
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Accessors(chain = true)
public class SomeModel {

    final Long id;
    String name;

}
