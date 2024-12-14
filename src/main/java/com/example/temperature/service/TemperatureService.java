package com.example.temperature.service;

import com.example.temperature.model.*;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.annotation.PostConstruct;

import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class TemperatureService {
    private List<TemperatureRecord> temperatureData;

    @PostConstruct
    void init() {
        loadData();
    }

    private void loadData() {
        try (FileInputStream fis = new FileInputStream("temperature_data.csv");
             InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
             CSVReader reader = new CSVReader(isr)) {
             
            // Skip header
            reader.skip(1);
            
            temperatureData = reader.readAll().stream()
                .map(row -> new TemperatureRecord(
                    Instant.parse(row[0]),
                    Double.parseDouble(row[1]),
                    Double.parseDouble(row[2])
                ))
                .sorted(Comparator.comparing(TemperatureRecord::timestamp))
                .collect(Collectors.toList());
                
        } catch (IOException | CsvException e) {
            throw new RuntimeException("Failed to load temperature data", e);
        }
    }

    public AggregationResponse aggregateData(Instant startTime, Instant endTime, int resolutionSeconds) {
        if (endTime.isBefore(startTime)) {
            throw new IllegalArgumentException("End time must be after start time");
        }

        List<TemperatureRecord> filteredData = temperatureData.stream()
            .filter(record -> !record.timestamp().isBefore(startTime) && record.timestamp().isBefore(endTime))
            .collect(Collectors.toList());

        if (filteredData.isEmpty()) {
            throw new IllegalArgumentException("No data found for the specified time range");
        }

        Map<Instant, List<TemperatureRecord>> buckets = new TreeMap<>();
        
        for (TemperatureRecord record : filteredData) {
            Instant bucketKey = record.timestamp().truncatedTo(ChronoUnit.SECONDS)
                .minus(record.timestamp().getEpochSecond() % resolutionSeconds, ChronoUnit.SECONDS);
            buckets.computeIfAbsent(bucketKey, k -> new ArrayList<>()).add(record);
        }

        List<AggregatedDataPoint> aggregatedPoints = buckets.entrySet().stream()
            .map(entry -> {
                List<TemperatureRecord> bucketData = entry.getValue();
                
                TemperatureStats ambientStats = calculateStats(
                    bucketData.stream().map(TemperatureRecord::ambientTemperature).collect(Collectors.toList())
                );
                
                TemperatureStats deviceStats = calculateStats(
                    bucketData.stream().map(TemperatureRecord::deviceTemperature).collect(Collectors.toList())
                );
                
                return new AggregatedDataPoint(entry.getKey(), ambientStats, deviceStats);
            })
            .collect(Collectors.toList());

        return new AggregationResponse(
            resolutionSeconds,
            startTime,
            endTime,
            aggregatedPoints.size(),
            aggregatedPoints
        );
    }

    private TemperatureStats calculateStats(List<Double> values) {
        DoubleSummaryStatistics stats = values.stream().mapToDouble(Double::doubleValue).summaryStatistics();
        return new TemperatureStats(
            Math.round(stats.getAverage() * 100.0) / 100.0,
            Math.round(stats.getMin() * 100.0) / 100.0,
            Math.round(stats.getMax() * 100.0) / 100.0
        );
    }

    private record TemperatureRecord(Instant timestamp, double ambientTemperature, double deviceTemperature) {}
}