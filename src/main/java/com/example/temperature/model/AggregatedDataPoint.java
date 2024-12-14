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
        this.timestamp = timestamp;
        this.ambientTemperature = ambientTemperature;
        this.deviceTemperature = deviceTemperature;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public TemperatureStats getAmbientTemperature() {
        return ambientTemperature;
    }

    public void setAmbientTemperature(TemperatureStats ambientTemperature) {
        this.ambientTemperature = ambientTemperature;
    }

    public TemperatureStats getDeviceTemperature() {
        return deviceTemperature;
    }

    public void setDeviceTemperature(TemperatureStats deviceTemperature) {
        this.deviceTemperature = deviceTemperature;
    }
}