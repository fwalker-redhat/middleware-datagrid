package org.jboss.infinispan.jazz.test.model;

import java.time.Instant;
import java.util.Date;

public class LoadInput {

    public final String cacheName;
    public final int count;
    public final int keyLength;
    public final int valueLength;
    public final int batchSize;
    public final Date dateCreated;

    public LoadInput(String cacheName, int count, int keyLength, int valueLength, int batchSize) {
        this.cacheName = cacheName;
        this.count = count;
        this.keyLength = keyLength;
        this.valueLength = valueLength;
        this.batchSize = batchSize;
        this.dateCreated = Date.from(Instant.now());
    }

}
