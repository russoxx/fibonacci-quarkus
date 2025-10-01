package com.labanca.fibonacci.dto;

import java.time.LocalDateTime;

public record StatisticResponse(
    Integer n,
    Long queryCount,
    LocalDateTime lastAccessed
) {}
