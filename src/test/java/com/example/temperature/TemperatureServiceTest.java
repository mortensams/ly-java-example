package com.example.temperature;

import com.example.temperature.model.AggregationResponse;
import com.example.temperature.service.TemperatureService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class TemperatureServiceTest {

    @Inject
    TemperatureService service;

    private static final Instant START_TIME = Instant.parse("2024-01-01T00:00:00Z");
    private static final Instant END_TIME = Instant.parse("2024-01-01T01:00:00Z");

    @BeforeAll
    public static void setup() {
        // Ensure data is generated before tests
        try {
            com.example.temperature.util.DataGenerator.main(new String[]{});
        } catch (Exception e) {
            fail("Failed to generate test data: " + e.getMessage());
        }
    }

    @Test
    public void testDataAggregation() {
        AggregationResponse response = service.aggregateData(START_TIME, END_TIME, 60);

        assertNotNull(response);
        assertEquals(60, response.getResolutionSeconds());
        assertEquals(START_TIME, response.getStartTime());
        assertEquals(END_TIME, response.getEndTime());
        assertTrue(response.getDataPoints() > 0);
        assertNotNull(response.getAggregatedData());
        assertFalse(response.getAggregatedData().isEmpty());
    }

    @Test
    public void testInvalidTimeRange() {
        assertThrows(IllegalArgumentException.class, () -> 
            service.aggregateData(END_TIME, START_TIME, 60)
        );
    }

    @Test
    public void testDataBoundaries() {
        AggregationResponse response = service.aggregateData(START_TIME, END_TIME, 60);

        response.getAggregatedData().forEach(point -> {
            assertNotNull(point.getTimestamp());
            assertNotNull(point.getAmbientTemperature());
            assertNotNull(point.getDeviceTemperature());
            
            assertTrue(point.getAmbientTemperature().getMean() >= point.getAmbientTemperature().getMin());
            assertTrue(point.getAmbientTemperature().getMean() <= point.getAmbientTemperature().getMax());
            
            assertTrue(point.getDeviceTemperature().getMean() >= point.getDeviceTemperature().getMin());
            assertTrue(point.getDeviceTemperature().getMean() <= point.getDeviceTemperature().getMax());
        });
    }
}