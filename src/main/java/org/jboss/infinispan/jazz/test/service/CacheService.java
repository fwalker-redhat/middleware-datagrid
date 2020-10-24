package org.jboss.infinispan.jazz.test.service;

import org.infinispan.commons.util.CloseableIteratorSet;

import java.util.Map;
import java.util.Set;

public interface CacheService {

    boolean exists(String cacheName);

    void put(String cacheName, String key, String value);

    void putAll(String cacheName, Map<?, ?> entries);

    Object get(String cacheName, String key);

    Map<Object, Object> getAll(String cacheName, Set<Object> keys);

    CloseableIteratorSet<Object> getAllKeys(String cacheName);

}
