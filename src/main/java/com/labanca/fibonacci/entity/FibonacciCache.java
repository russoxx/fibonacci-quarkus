package com.labanca.fibonacci.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Table(name = "fibonacci_cache")
public class FibonacciCache extends PanacheEntity {

    @Column(unique = true, nullable = false)
    public Integer n;

    @Column(name = "fibonacci_value", nullable = false, columnDefinition = "TEXT")
    public String fibonacciValue;

    @Column(name = "created_at", nullable = false)
    public LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public static Optional<FibonacciCache> findByN(Integer n) {
        return find("n", n).firstResultOptional();
    }
}
