package com.example.temperature.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import java.time.Instant;

@RegisterForReflection
public final class AggregatedDataPoint {
    private final Instant timestamp;
    private final TemperatureStats ambientTemperature;
    private final TemperatureStats deviceTemperature;

    public AggregatedDataPoint() {
        this(Instant.EPOCH, new TemperatureStats(), new TemperatureStats());
    }

    public AggregatedDataPoint(Instant timestamp, TemperatureStats ambientTemperature, TemperatureStats deviceTemperature) {
        this.timestamp = timestamp != null ? timestamp : Instant.EPOCH;
        this.ambientTemperature = new TemperatureStats(ambientTemperature != null ? ambientTemperature : new TemperatureStats());
        this.deviceTemperature = new TemperatureStats(deviceTemperature != null ? deviceTemperature : new TemperatureStats());
    }

    public AggregatedDataPoint(AggregatedDataPoint other) {
        this(other.timestamp, other.ambientTemperature, other.deviceTemperature);
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public TemperatureStats getAmbientTemperature() {
        return new TemperatureStats(ambientTemperature);
    }

    public TemperatureStats getDeviceTemperature() {
        return new TemperatureStats(deviceTemperature);
    }
}