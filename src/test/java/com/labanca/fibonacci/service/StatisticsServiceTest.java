package com.labanca.fibonacci.service;

import com.labanca.fibonacci.dto.StatisticResponse;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class StatisticsServiceTest {

    @Inject
    StatisticsService statisticsService;

    @Test
    void testRecordQuery() {
        statisticsService.recordQuery(100);

        List<StatisticResponse> stats = statisticsService.getAllStatistics();
        assertTrue(stats.stream().anyMatch(s -> s.n().equals(100)));
    }

    @Test
    void testRecordQueryMultipleTimes() {
        statisticsService.recordQuery(101);
        statisticsService.recordQuery(101);
        statisticsService.recordQuery(101);

        List<StatisticResponse> stats = statisticsService.getAllStatistics();
        StatisticResponse stat = stats.stream()
            .filter(s -> s.n().equals(101))
            .findFirst()
            .orElse(null);

        assertNotNull(stat);
        assertTrue(stat.queryCount() >= 3);
    }

    @Test
    void testGetTopN() {
        statisticsService.recordQuery(102);
        statisticsService.recordQuery(103);

        List<StatisticResponse> stats = statisticsService.getTopN(5);

        assertNotNull(stats);
        assertTrue(stats.size() <= 5);
    }

    @Test
    void testGetAllStatistics() {
        statisticsService.recordQuery(104);

        List<StatisticResponse> stats = statisticsService.getAllStatistics();

        assertNotNull(stats);
        assertTrue(stats.size() > 0);
    }
}
