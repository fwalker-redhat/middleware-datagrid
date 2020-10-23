package org.jboss.infinispan.jazz.test.controller;

import org.jboss.infinispan.jazz.test.service.CacheService;
import org.jboss.infinispan.jazz.test.utils.KeyValueGenerator;
import org.jboss.infinispan.jazz.test.utils.StringGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.logging.Logger;

@RestController
public class LoadController {

    private static Logger LOG = Logger.getLogger(LoadController.class.getName());

    private CacheService cacheService;

    public LoadController(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @GetMapping("/load/{cacheName}")
    public void loadCache(@PathVariable(name = "cacheName") String cacheName,
                          @RequestParam(name = "count", required = false, defaultValue = "1") Long count,
                          @RequestParam(name = "keyLength", required = false, defaultValue = "36") Long keyLength,
                          @RequestParam(name = "valueLength", required = false, defaultValue = "36") Long valueLength,
                          @RequestParam(name = "batchSize", required = false, defaultValue = "1") Long batchSize) {
        CacheService.cache cache = CacheService.cache.valueOf(cacheName);
        Long repititions = count / batchSize;
        Long remainingCount = count % batchSize;
        Date start = Date.from(Instant.now());

        for (int i = 0; i < repititions.intValue(); i++) {
            if (batchSize == 1) {
                cacheService.put(cache.cacheName, StringGenerator.value(keyLength), StringGenerator.value(valueLength));
            } else {
                cacheService.putAll(cache.cacheName, KeyValueGenerator.generateKeyValues(batchSize, keyLength, valueLength));
            }
            LOG.info(String.format("Loaded Entries: %s of %s", (i+1) * batchSize.intValue(), count));
        }

        if (remainingCount > 0) {
            cacheService.putAll(cache.cacheName, KeyValueGenerator.generateKeyValues(remainingCount, keyLength, valueLength));
        }
        Date end = Date.from(Instant.now());
        LOG.info("----------------------------------------------------------------------------------------------------");
        LOG.info("--------------------------------------------LOAD REPORT---------------------------------------------");
        LOG.info("----------------------------------------------------------------------------------------------------");
        LOG.info(String.format("Total Loaded Entries: %s", count));
        LOG.info(String.format("Key Size (Bytes): %s, Value Size (Bytes): %s", keyLength, valueLength));
        LOG.info("----------------------------------------------------------------------------------------------------");
        LOG.info(String.format("Total Keys Stored (Bytes): %s", count * keyLength));
        LOG.info(String.format("Total Values Stored (Bytes): %s", count * valueLength));
        LOG.info(String.format("Time Started: %s", start));
        LOG.info(String.format("Time Completed: %s", end));
        LOG.info(String.format("Total Run Time (Seconds): %s", ChronoUnit.SECONDS.between(start.toInstant(),end.toInstant())));
        LOG.info(String.format("Mean Entries / Second: %s", Double.valueOf(count) / Double.valueOf(ChronoUnit.SECONDS.between(start.toInstant(),end.toInstant()))));
        LOG.info("----------------------------------------------------------------------------------------------------");
        LOG.info("----------------------------------------------------------------------------------------------------");
    }

}
