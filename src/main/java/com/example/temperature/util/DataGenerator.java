package com.example.temperature.util;

import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class DataGenerator {
    private static final Random RANDOM = new Random(42);
    private static final double BASE_TEMP = 22.0;
    private static final double DAILY_VARIATION = 1.5;
    private static final double NOISE_AMPLITUDE = 0.2;
    private static final double BASE_DEVICE_TEMP = 55.0;
    private static final double DEVICE_TEMP_FACTOR = 2.0;
    private static final double DEVICE_NOISE = 0.5;

    public static void main(String[] args) {
        try {
            generateData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void generateData() throws IOException {
        Instant startTime = Instant.parse("2024-01-01T00:00:00Z");
        int totalSeconds = 172800; // 48 hours

        List<String[]> records = new ArrayList<>();
        records.add(new String[]{"timestamp", "ambient_temperature", "device_temperature"});

        // Generate temperature data
        for (int second = 0; second < totalSeconds; second++) {
            Instant timestamp = startTime.plus(second, ChronoUnit.SECONDS);
            double hourOfDay = (double) second / 3600.0;
            
            // Calculate ambient temperature
            double ambientTemp = BASE_TEMP +
                DAILY_VARIATION * Math.sin(2 * Math.PI * hourOfDay / 24) +
                NOISE_AMPLITUDE * RANDOM.nextGaussian();

            // Calculate device temperature
            double deviceTemp = BASE_DEVICE_TEMP +
                DEVICE_TEMP_FACTOR * (ambientTemp - BASE_TEMP) +
                DEVICE_NOISE * RANDOM.nextGaussian();

            records.add(new String[]{
                timestamp.toString(),
                String.format("%.2f", ambientTemp),
                String.format("%.2f", deviceTemp)
            });
        }

        // Write temperature data
        try (CSVWriter writer = new CSVWriter(new FileWriter("temperature_data.csv"))) {
            writer.writeAll(records);
        }

        // Generate and write spike times
        List<String[]> spikeRecords = new ArrayList<>();
        spikeRecords.add(new String[]{"spike_number", "start_time", "peak_time"});
        
        int numSpikes = 8;
        int spikeDuration = 300; // 5 minutes
        int minGap = 3600; // 1 hour
        
        Set<Integer> spikeStarts = generateSpikeStarts(totalSeconds, spikeDuration, minGap, numSpikes);
        
        int spikeNumber = 1;
        for (Integer start : spikeStarts) {
            Instant spikeStartTime = startTime.plus(start, ChronoUnit.SECONDS);
            Instant peakTime = spikeStartTime.plus(spikeDuration / 2, ChronoUnit.SECONDS);
            
            spikeRecords.add(new String[]{
                String.valueOf(spikeNumber++),
                spikeStartTime.toString(),
                peakTime.toString()
            });
        }

        try (CSVWriter writer = new CSVWriter(new FileWriter("spike_times.csv"))) {
            writer.writeAll(spikeRecords);
        }
    }

    private static Set<Integer> generateSpikeStarts(int totalSeconds, int spikeDuration, 
                                                  int minGap, int numSpikes) {
        Set<Integer> spikeStarts = new TreeSet<>();
        
        while (spikeStarts.size() < numSpikes) {
            int newStart = RANDOM.nextInt(totalSeconds - spikeDuration);
            
            boolean validGap = spikeStarts.stream()
                .noneMatch(existing -> Math.abs(newStart - existing) < minGap);
                
            if (validGap) {
                spikeStarts.add(newStart);
            }
        }
        
        return spikeStarts;
    }
}