package org.jboss.infinispan.jazz.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CacheServiceTest {

    @Autowired
    private CacheService cacheService;

    @Test
    public void put() {
        cacheService.put(CacheService.cache.APP_CONNECT.cacheName,"key","value");
    }

    @Test
    public void getAllKeys() {
        cacheService.getAllKeys(CacheService.cache.APP_CONNECT.cacheName).forEach(o -> assertEquals("key", o));
    }
}