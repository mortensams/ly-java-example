package com.example.temperature;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.time.Instant;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.Assertions.fail;

@QuarkusTest
public class TemperatureResourceTest {

    private static final String START_TIME = "2024-01-01T00:00:00Z";
    private static final String END_TIME = "2024-01-01T01:00:00Z";

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
    public void testHealthEndpoint() {
        given()
            .when()
            .get("/api/v1/temperature/health")
            .then()
            .statusCode(200)
            .body("status", is("healthy"));
    }

    @Test
    public void testAggregateEndpoint() {
        given()
            .queryParam("startTime", START_TIME)
            .queryParam("endTime", END_TIME)
            .queryParam("resolution", 60)
            .when()
            .get("/api/v1/temperature/aggregate")
            .then()
            .statusCode(200)
            .body("resolutionSeconds", is(60))
            .body("startTime", notNullValue())
            .body("endTime", notNullValue())
            .body("dataPoints", notNullValue())
            .body("aggregatedData", notNullValue());
    }

    @Test
    public void testInvalidTimeRange() {
        given()
            .queryParam("startTime", END_TIME)
            .queryParam("endTime", START_TIME)
            .queryParam("resolution", 60)
            .when()
            .get("/api/v1/temperature/aggregate")
            .then()
            .statusCode(400);
    }

    @Test
    public void testInvalidResolution() {
        given()
            .queryParam("startTime", START_TIME)
            .queryParam("endTime", END_TIME)
            .queryParam("resolution", -1)
            .when()
            .get("/api/v1/temperature/aggregate")
            .then()
            .statusCode(400);
    }
}