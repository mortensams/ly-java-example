package com.example.temperature.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RegisterForReflection
public class AggregationResponse {
    private int resolutionSeconds;
    private Instant startTime;
    private Instant endTime;
    private int dataPoints;
    private List<AggregatedDataPoint> aggregatedData;

    public AggregationResponse() {
    }

    public AggregationResponse(int resolutionSeconds, Instant startTime, Instant endTime, 
                             int dataPoints, List<AggregatedDataPoint> aggregatedData) {
        this.resolutionSeconds = resolutionSeconds;
        this.startTime = startTime != null ? Instant.from(startTime) : null;
        this.endTime = endTime != null ? Instant.from(endTime) : null;
        this.dataPoints = dataPoints;
        this.aggregatedData = aggregatedData != null ? new ArrayList<>(aggregatedData) : new ArrayList<>();
    }

    public int getResolutionSeconds() {
        return resolutionSeconds;
    }

    public void setResolutionSeconds(int resolutionSeconds) {
        this.resolutionSeconds = resolutionSeconds;
    }

    public Instant getStartTime() {
        return startTime != null ? Instant.from(startTime) : null;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime != null ? Instant.from(startTime) : null;
    }

    public Instant getEndTime() {
        return endTime != null ? Instant.from(endTime) : null;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime != null ? Instant.from(endTime) : null;
    }

    public int getDataPoints() {
        return dataPoints;
    }

    public void setDataPoints(int dataPoints) {
        this.dataPoints = dataPoints;
    }

    public List<AggregatedDataPoint> getAggregatedData() {
        return new ArrayList<>(aggregatedData != null ? aggregatedData : new ArrayList<>());
    }

    public void setAggregatedData(List<AggregatedDataPoint> aggregatedData) {
        this.aggregatedData = aggregatedData != null ? new ArrayList<>(aggregatedData) : new ArrayList<>();
    }
}