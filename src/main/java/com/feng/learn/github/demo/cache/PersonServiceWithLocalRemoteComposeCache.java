package com.feng.learn.github.demo.cache;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.function.Function;

import static com.feng.learn.github.demo.cache.config.SpringCacheConfig.CACHE_1;
import static com.feng.learn.github.demo.cache.config.SpringCacheConfig.LOCAL_REMOTE_CACHE_MANAGER;

/**
 * @author zhanfeng.zhang
 * @date 2020/04/16
 */
@Service
@CacheConfig(cacheManager = LOCAL_REMOTE_CACHE_MANAGER)
public class PersonServiceWithLocalRemoteComposeCache {

    final Function justForTest;

    public PersonServiceWithLocalRemoteComposeCache(Function justForTest) {
        this.justForTest = justForTest;
    }

    @Cacheable(key = "'/p/' + #root.args[0]", cacheNames = {CACHE_1})
    public Person getById(Long id) {
        justForTest.apply(null);
        return new Person().setId(id).setName("zhanfeng.zhang")
            .setHome(new Person.Address().setCity("Shanghai"))
            .setWork(new Person.Address().setStreet("Zhenbei road"));
    }

    @CacheEvict(key = "'/p/' + #root.args[0]", cacheNames = {CACHE_1})
    public void deleteById(Long id) {

    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Accessors(chain = true)
    public static class Person {

        Long id;
        String name;

        Address home;
        Address work;

        @Data
        @Accessors(chain = true)
        @FieldDefaults(level = AccessLevel.PRIVATE)
        public static class Address {

            String province;
            String city;
            String street;
        }
    }

}
