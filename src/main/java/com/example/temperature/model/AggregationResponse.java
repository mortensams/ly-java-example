package com.example.temperature.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RegisterForReflection
public final class AggregationResponse {
    private final int resolutionSeconds;
    private final Instant startTime;
    private final Instant endTime;
    private final int dataPoints;
    private final List<AggregatedDataPoint> aggregatedData;

    public AggregationResponse() {
        this(0, Instant.EPOCH, Instant.EPOCH, 0, Collections.emptyList());
    }

    public AggregationResponse(int resolutionSeconds, Instant startTime, Instant endTime, 
                             int dataPoints, List<AggregatedDataPoint> aggregatedData) {
        this.resolutionSeconds = resolutionSeconds;
        this.startTime = startTime != null ? startTime : Instant.EPOCH;
        this.endTime = endTime != null ? endTime : Instant.EPOCH;
        this.dataPoints = dataPoints;
        this.aggregatedData = new ArrayList<>();
        if (aggregatedData != null) {
            for (AggregatedDataPoint point : aggregatedData) {
                this.aggregatedData.add(new AggregatedDataPoint(point));
            }
        }
    }

    public int getResolutionSeconds() {
        return resolutionSeconds;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public int getDataPoints() {
        return dataPoints;
    }

    public List<AggregatedDataPoint> getAggregatedData() {
        List<AggregatedDataPoint> copy = new ArrayList<>();
        for (AggregatedDataPoint point : aggregatedData) {
            copy.add(new AggregatedDataPoint(point));
        }
        return copy;
    }
}