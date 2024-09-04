package com.feng.learn.github.demo.cache;

import com.feng.learn.github.demo.cache.config.degrade.DegradeCacheResolver;
import com.feng.learn.github.demo.cache.config.degrade.SpringCacheConfig;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.function.Function;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

/**
 * @author zhanfeng.zhang
 * @date 2020/6/7
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {
    PersonServiceWithDegradeCacheResolver.class,
    PersonServiceWithDegradeCacheResolverTest.ContextMock.class,
    SpringCacheConfig.class,
})
@Ignore
@Disabled
public class PersonServiceWithDegradeCacheResolverTest {

  @Autowired
  PersonServiceWithDegradeCacheResolver personService;

  @Autowired
  @Qualifier("justForTest")
  Function justFotTest;

  @Autowired
  @Qualifier(SpringCacheConfig.CACHE_RESOLVER)
  DegradeCacheResolver cacheResolver;

  @Test
  public void givenCacheSwitchOff_then() {
    // given
    // turn cache off
    cacheResolver.switchCacheOff();
    // when
    PersonServiceWithDegradeCacheResolver.Person noCache = personService.getById(1L);
    PersonServiceWithDegradeCacheResolver.Person cache = personService.getById(1L);
    // then
    BDDMockito.then(justFotTest).should(times(2)).apply(any());
    then(cache).isEqualTo(noCache);
  }

  @Test
  public void givenCacheSwitchOn_then() {
    // when
    // first time no cache
    PersonServiceWithDegradeCacheResolver.Person noCache = personService.getById(1L);
    // second time use cache. so there is still one invoke to the mock
    PersonServiceWithDegradeCacheResolver.Person cache = personService.getById(1L);
    // then
    BDDMockito.then(justFotTest).should(times(1)).apply(any());
    then(cache).isEqualTo(noCache);
  }

  @Configuration
  static class ContextMock {

    @Bean
    public Function justForTest() {
      return Mockito.mock(Function.class);
    }
  }

}