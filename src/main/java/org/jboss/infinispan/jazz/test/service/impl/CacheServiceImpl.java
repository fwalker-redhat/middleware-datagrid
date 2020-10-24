package org.jboss.infinispan.jazz.test.service.impl;

import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.commons.util.CloseableIteratorSet;
import org.jboss.infinispan.jazz.test.service.CacheService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class CacheServiceImpl implements CacheService {

    private RemoteCacheManager remoteCacheManager;

    public CacheServiceImpl(RemoteCacheManager remoteCacheManager) {
        this.remoteCacheManager = remoteCacheManager;
    }

    @Override
    public boolean exists(String cacheName) {
        AtomicBoolean result = new AtomicBoolean(false);
        Optional.ofNullable(remoteCacheManager.getCache(cacheName)).ifPresent(c -> result.set(true));
        return result.get();
    }

    @Override
    public void put(String cacheName, String key, String value) {
        remoteCacheManager.getCache(cacheName).put(key, value);
    }

    @Override
    public void putAll(String cacheName, Map<?, ?> entries) {
        remoteCacheManager.getCache(cacheName).putAll(entries);
    }

    @Override
    public Object get(String cacheName, String key) {
        return remoteCacheManager.getCache(cacheName).get(key);
    }

    @Override
    public Map<Object, Object> getAll(String cacheName, Set<Object> keys) {
        return remoteCacheManager.getCache(cacheName).getAll(keys);
    }

    @Override
    public CloseableIteratorSet<Object> getAllKeys(String cacheName) {
        return remoteCacheManager.getCache(cacheName).keySet();
    }

}
