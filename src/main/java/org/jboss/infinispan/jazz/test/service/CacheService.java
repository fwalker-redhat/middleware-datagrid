package org.jboss.infinispan.jazz.test.service;

import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.commons.util.CloseableIteratorSet;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
public class CacheService {

    public enum cache {
        APP_CONNECT("AppConnectCache"),
        CONFIGURATION("ConfigurationCache"),
        RESPONSE_CODE("ResponseCodeCache"),
        USER_PROFILE("UserProfileCache"),
        CUSTOMER("CustomerCache"),
        TRANSACTION("TransactionCache");

        public final String cacheName;

        cache(String cacheName) {
            this.cacheName = cacheName;
        }

    }

    private RemoteCacheManager remoteCacheManager;

    public CacheService(RemoteCacheManager remoteCacheManager) {
        this.remoteCacheManager = remoteCacheManager;
    }

    public void put(String cacheName, String key, String value) {
        remoteCacheManager.getCache(cacheName).put(key, value);
    }

    public void putAll(String cacheName, Map<?, ?> entries) {
        remoteCacheManager.getCache(cacheName).putAll(entries);
    }

    public Object get(String cacheName, String key) {
        return remoteCacheManager.getCache(cacheName).get(key);
    }

    public Map<Object,Object> getAll(String cacheName, Set<Object> keys) {
        return remoteCacheManager.getCache(cacheName).getAll(keys);
    }

    public CloseableIteratorSet<Object> getAllKeys(String cacheName) {
        return remoteCacheManager.getCache(cacheName).keySet();
    }

}
