package com.example.temperature.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import java.time.Instant;
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
        this.startTime = startTime;
        this.endTime = endTime;
        this.dataPoints = dataPoints;
        this.aggregatedData = aggregatedData;
    }

    public int getResolutionSeconds() {
        return resolutionSeconds;
    }

    public void setResolutionSeconds(int resolutionSeconds) {
        this.resolutionSeconds = resolutionSeconds;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public int getDataPoints() {
        return dataPoints;
    }

    public void setDataPoints(int dataPoints) {
        this.dataPoints = dataPoints;
    }

    public List<AggregatedDataPoint> getAggregatedData() {
        return aggregatedData;
    }

    public void setAggregatedData(List<AggregatedDataPoint> aggregatedData) {
        this.aggregatedData = aggregatedData;
    }
}