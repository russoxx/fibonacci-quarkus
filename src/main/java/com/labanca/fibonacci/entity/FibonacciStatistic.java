package com.labanca.fibonacci.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "fibonacci_statistics")
public class FibonacciStatistic extends PanacheEntity {

    @Column(unique = true, nullable = false)
    public Integer n;

    @Column(name = "query_count", nullable = false)
    public Long queryCount;

    @Column(name = "last_accessed", nullable = false)
    public LocalDateTime lastAccessed;

    public void incrementQueryCount() {
        this.queryCount++;
        this.lastAccessed = LocalDateTime.now();
    }

    public static Optional<FibonacciStatistic> findByN(Integer n) {
        return find("n", n).firstResultOptional();
    }

    public static List<FibonacciStatistic> findTopN(int limit) {
        return find("order by queryCount desc").page(0, limit).list();
    }

    public static List<FibonacciStatistic> findAllOrderedByQueryCount() {
        return find("order by queryCount desc").list();
    }
}
