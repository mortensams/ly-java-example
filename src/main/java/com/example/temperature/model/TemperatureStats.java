package com.example.temperature.model;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public final class TemperatureStats {
    private final double mean;
    private final double min;
    private final double max;

    public TemperatureStats() {
        this(0.0, 0.0, 0.0);
    }

    public TemperatureStats(double mean, double min, double max) {
        this.mean = mean;
        this.min = min;
        this.max = max;
    }

    public TemperatureStats(TemperatureStats other) {
        this(other.mean, other.min, other.max);
    }

    public double getMean() {
        return mean;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }
}