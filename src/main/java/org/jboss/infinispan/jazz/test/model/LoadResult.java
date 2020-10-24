package org.jboss.infinispan.jazz.test.model;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class LoadResult {

    private enum LoadStatus {
        SUBMITTED,
        RUNNING,
        COMPLETED,
        FAILED
    }

    public final LoadInput loadInput;
    private final int id;
    private Date startDate;
    private Date endDate;
    private LoadStatus loadStatus;
    private String failedReason;
    private AtomicInteger loadedEntries = new AtomicInteger(0);

    public LoadResult(int id, LoadInput loadInput) {
        this.id = id;
        this.loadInput = loadInput;
    }

    public int getId() {
        return id;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
        this.loadStatus = LoadStatus.RUNNING;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
        this.loadStatus = LoadStatus.COMPLETED;
    }

    public void setLoadStatus(LoadStatus loadStatus) {
        this.loadStatus = loadStatus;
    }

    public LoadStatus getLoadStatus() {
        return loadStatus;
    }

    public String getFailedReason() {
        return failedReason;
    }

    public void setFailedReason(String failedReason) {
        this.failedReason = failedReason;
        this.loadStatus = failedReason != null ? LoadStatus.FAILED : LoadStatus.SUBMITTED;
    }

    public AtomicInteger getLoadedEntries() {
        return loadedEntries;
    }

    public void addLoadedEntries(Integer loadedEntries) {
        this.loadedEntries.addAndGet(loadedEntries);
    }

    public Integer getTotalKeySize() {
        return loadedEntries.get() * this.loadInput.keyLength;
    }

    public Integer getTotalValueSize() {
        return loadedEntries.get() * this.loadInput.valueLength;
    }

    public Long getRunTime() {
        AtomicLong result = new AtomicLong(0);
        Optional.ofNullable(startDate).ifPresent(sd -> result.set(ChronoUnit.SECONDS
                .between(sd.toInstant(), Optional.ofNullable(this.endDate).orElse(Date.from(Instant.now()))
                        .toInstant())));
        return result.get();
    }

    public Double getMeanTPS() {
        return getRunTime() > 0 ? loadedEntries.doubleValue() / getRunTime() : 0.0;
    }

    public Double getBytesPS() {
        return getRunTime() > 0 ?
                loadedEntries.doubleValue() * (loadInput.keyLength + loadInput.valueLength) / getRunTime() : 0.0;
    }
}
