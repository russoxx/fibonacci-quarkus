package com.labanca.fibonacci.service;

import com.labanca.fibonacci.dto.StatisticResponse;
import com.labanca.fibonacci.entity.FibonacciStatistic;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class StatisticsService {

    @Transactional
    public void recordQuery(Integer n) {
        Optional<FibonacciStatistic> statOpt = FibonacciStatistic.findByN(n);

        if (statOpt.isPresent()) {
            FibonacciStatistic stat = statOpt.get();
            stat.incrementQueryCount();
        } else {
            FibonacciStatistic stat = new FibonacciStatistic();
            stat.n = n;
            stat.queryCount = 1L;
            stat.lastAccessed = LocalDateTime.now();
            stat.persist();
        }
    }

    public List<StatisticResponse> getTopN(int limit) {
        return FibonacciStatistic.findTopN(limit)
            .stream()
            .map(stat -> new StatisticResponse(stat.n, stat.queryCount, stat.lastAccessed))
            .collect(Collectors.toList());
    }

    public List<StatisticResponse> getAllStatistics() {
        return FibonacciStatistic.findAllOrderedByQueryCount()
            .stream()
            .map(stat -> new StatisticResponse(stat.n, stat.queryCount, stat.lastAccessed))
            .collect(Collectors.toList());
    }
}
