package com.example.temperature.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import java.time.Instant;

@RegisterForReflection
public class AggregatedDataPoint {
    private Instant timestamp;
    private TemperatureStats ambientTemperature;
    private TemperatureStats deviceTemperature;

    public AggregatedDataPoint() {
    }

    public AggregatedDataPoint(Instant timestamp, TemperatureStats ambientTemperature, TemperatureStats deviceTemperature) {
        this.timestamp = timestamp != null ? Instant.from(timestamp) : null;
        this.ambientTemperature = ambientTemperature != null ? new TemperatureStats(ambientTemperature) : null;
        this.deviceTemperature = deviceTemperature != null ? new TemperatureStats(deviceTemperature) : null;
    }

    public Instant getTimestamp() {
        return timestamp != null ? Instant.from(timestamp) : null;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp != null ? Instant.from(timestamp) : null;
    }

    public TemperatureStats getAmbientTemperature() {
        return ambientTemperature != null ? new TemperatureStats(ambientTemperature) : null;
    }

    public void setAmbientTemperature(TemperatureStats ambientTemperature) {
        this.ambientTemperature = ambientTemperature != null ? new TemperatureStats(ambientTemperature) : null;
    }

    public TemperatureStats getDeviceTemperature() {
        return deviceTemperature != null ? new TemperatureStats(deviceTemperature) : null;
    }

    public void setDeviceTemperature(TemperatureStats deviceTemperature) {
        this.deviceTemperature = deviceTemperature != null ? new TemperatureStats(deviceTemperature) : null;
    }
}