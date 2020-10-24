package org.jboss.infinispan.jazz.test.controller;

import org.jboss.infinispan.jazz.test.model.LoadResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface LoadController {

    @PostMapping("/{cacheName}")
    LoadResult loadCache(@PathVariable(name = "cacheName") String cacheName,
                         @RequestParam(name = "count", required = false, defaultValue = "1") int count,
                         @RequestParam(name = "keyLength", required = false, defaultValue = "36") int keyLength,
                         @RequestParam(name = "valueLength", required = false, defaultValue = "36") int valueLength,
                         @RequestParam(name = "batchSize", required = false, defaultValue = "1") int batchSize);

    @GetMapping("/")
    List<LoadResult> getLoadResults();

    @GetMapping("/{loadId}")
    LoadResult getLoadResultById(@PathVariable(name = "loadId") int loadId);

}
