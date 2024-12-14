package com.example.temperature.model;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class TemperatureStats {
    private double mean;
    private double min;
    private double max;

    public TemperatureStats() {
    }

    public TemperatureStats(double mean, double min, double max) {
        this.mean = mean;
        this.min = min;
        this.max = max;
    }

    public TemperatureStats(TemperatureStats other) {
        if (other != null) {
            this.mean = other.mean;
            this.min = other.min;
            this.max = other.max;
        }
    }

    public double getMean() {
        return mean;
    }

    public void setMean(double mean) {
        this.mean = mean;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }
}