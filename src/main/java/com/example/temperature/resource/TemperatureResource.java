package com.example.temperature.resource;

import com.example.temperature.model.AggregationResponse;
import com.example.temperature.service.TemperatureService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.time.Instant;

@Path("/api/v1/temperature")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TemperatureResource {

    private static final int MIN_RESOLUTION = 1;
    private static final int MAX_RESOLUTION = 86400; // 24 hours in seconds

    @Inject
    TemperatureService temperatureService;

    @GET
    @Path("/aggregate")
    public AggregationResponse aggregateTemperatures(
            @QueryParam("startTime") String startTime,
            @QueryParam("endTime") String endTime,
            @QueryParam("resolution") @DefaultValue("60") int resolution) {
        
        // Validate resolution
        if (resolution < MIN_RESOLUTION || resolution > MAX_RESOLUTION) {
            throw new BadRequestException("Resolution must be between " + MIN_RESOLUTION + 
                                       " and " + MAX_RESOLUTION + " seconds");
        }

        try {
            return temperatureService.aggregateData(
                Instant.parse(startTime),
                Instant.parse(endTime),
                resolution
            );
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @GET
    @Path("/health")
    public String healthCheck() {
        return "{\"status\":\"healthy\"}";
    }
}