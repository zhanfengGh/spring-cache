package com.feng.learn.github.springcache;

import static org.assertj.core.api.Assertions.assertThat;

import com.feng.learn.github.springcache.domain.model.SomeModel;
import com.feng.learn.github.springcache.domain.service.SomeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringCacheApplicationTests {

    @Autowired
    SomeService someService;

    @Test
    void contextLoads() {
    }

    @Test
    @Timeout(11) // 要求方法在 11s 内完成
    void givenCache_whenCallServiceTwice_thenMethodUseCache() {
        SomeModel model1 = someService.getFromRepository(1L);
        SomeModel model2 = someService.getFromRepository(1L);
        assertThat(model1).isEqualTo(model2).isSameAs(model2);
    }

}
