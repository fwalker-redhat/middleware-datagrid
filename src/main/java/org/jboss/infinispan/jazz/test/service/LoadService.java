package org.jboss.infinispan.jazz.test.service;

import org.jboss.infinispan.jazz.test.model.LoadInput;
import org.jboss.infinispan.jazz.test.model.LoadResult;
import org.jboss.infinispan.jazz.test.utils.KeyValueGenerator;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

@Service
public class LoadService {

    private static Logger LOG = Logger.getLogger(LoadService.class.getName());

    private CacheService cacheService;
    private ExecutorService executorService = Executors.newFixedThreadPool(3);
    private ConcurrentHashMap<Integer, LoadResult> loadRuns = new ConcurrentHashMap<>();


    public LoadService(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    public LoadResult createLoad(LoadInput loadInput) {
        int loadId = loadRuns.size() + 1;
        loadRuns.put(loadId, new LoadResult(loadId, loadInput));
        if (cacheService.exists(loadInput.cacheName)) {
            CompletableFuture<Void> loadFuture = CompletableFuture.runAsync(() -> {
                LoadResult lr = loadRuns.get(loadId);
                lr.setStartDate(Date.from(Instant.now()));
                int repititions = lr.loadInput.count / lr.loadInput.batchSize;
                int remainingCount = lr.loadInput.count % lr.loadInput.batchSize;

                for (int i = 0; i < repititions; i++) {
                    cacheService.putAll(lr.loadInput.cacheName,
                            KeyValueGenerator.generateKeyValues(lr.loadInput.batchSize, lr.loadInput.keyLength, lr.loadInput.valueLength));
                    lr.addLoadedEntries(lr.loadInput.batchSize);
                }

                if (remainingCount > 0) {
                    cacheService.putAll(lr.loadInput.cacheName,
                            KeyValueGenerator.generateKeyValues(remainingCount, lr.loadInput.keyLength, lr.loadInput.valueLength));
                    lr.addLoadedEntries(remainingCount);
                }

                lr.setEndDate(Date.from(Instant.now()));
            });
        } else {
            loadRuns.get(loadId).setFailedReason("Cache does not exist");
        }
        return loadRuns.get(loadId);
    }

    public List<LoadResult> getAllLoadResults() {
        return new ArrayList<>(loadRuns.values());
    }

    public LoadResult getLoadResultById(Integer id) {
        return loadRuns.get(id);
    }


}
