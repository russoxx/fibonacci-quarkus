package com.labanca.fibonacci.resource;

import com.labanca.fibonacci.dto.FibonacciResult;
import com.labanca.fibonacci.dto.StatisticResponse;
import com.labanca.fibonacci.service.FibonacciService;
import com.labanca.fibonacci.service.StatisticsService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/fibonacci")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FibonacciResource {

    @Inject
    FibonacciService fibonacciService;

    @Inject
    StatisticsService statisticsService;

    @GET
    @Path("/{n}")
    public FibonacciResult getFibonacci(@PathParam("n") Integer n) {
        FibonacciResult result = fibonacciService.calculate(n);
        statisticsService.recordQuery(n);
        return result;
    }

    @GET
    @Path("/stats")
    public List<StatisticResponse> getStats(@QueryParam("limit") @DefaultValue("10") int limit) {
        return statisticsService.getTopN(limit);
    }

    @GET
    @Path("/stats/all")
    public List<StatisticResponse> getAllStats() {
        return statisticsService.getAllStatistics();
    }

    @GET
    @Path("/health")
    @Produces(MediaType.TEXT_PLAIN)
    public String health() {
        return "Fibonacci API is running!";
    }
}
