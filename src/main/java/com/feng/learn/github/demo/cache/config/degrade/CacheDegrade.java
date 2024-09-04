package com.feng.learn.github.demo.cache.config.degrade;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CacheDegrade {
    String value();
}
