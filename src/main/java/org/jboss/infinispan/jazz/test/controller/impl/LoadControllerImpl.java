package org.jboss.infinispan.jazz.test.controller.impl;

import org.jboss.infinispan.jazz.test.controller.LoadController;
import org.jboss.infinispan.jazz.test.model.LoadInput;
import org.jboss.infinispan.jazz.test.model.LoadResult;
import org.jboss.infinispan.jazz.test.service.LoadService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/load")
public class LoadControllerImpl implements LoadController {

    private static Logger LOG = Logger.getLogger(LoadController.class.getName());

    private LoadService loadService;

    public LoadControllerImpl(LoadService loadService) {
        this.loadService = loadService;
    }

    @Override
    @PostMapping("/{cacheName}")
    public LoadResult loadCache(@PathVariable(name = "cacheName") String cacheName,
                                @RequestParam(name = "count", required = false, defaultValue = "1") int count,
                                @RequestParam(name = "keyLength", required = false, defaultValue = "36") int keyLength,
                                @RequestParam(name = "valueLength", required = false, defaultValue = "36") int valueLength,
                                @RequestParam(name = "batchSize", required = false, defaultValue = "1") int batchSize) {
        LOG.fine(String.format("loadCache called: cacheName=%s, count=%s, keyLength=%s, valueLength=%s, batchSize=%s",
                cacheName, count, keyLength, valueLength, batchSize));
        LoadInput loadInput = new LoadInput(cacheName, count, keyLength, valueLength, batchSize);
        return loadService.createLoad(loadInput);
    }

    @Override
    @GetMapping("/")
    public List<LoadResult> getLoadResults() {
        return loadService.getAllLoadResults();
    }

    @Override
    @GetMapping("/{loadId}")
    public LoadResult getLoadResultById(@PathVariable(name = "loadId") int loadId) {
        return loadService.getLoadResultById(loadId);
    }
}
