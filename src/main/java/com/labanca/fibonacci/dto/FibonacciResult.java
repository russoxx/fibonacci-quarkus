package com.labanca.fibonacci.dto;

public record FibonacciResult(
    Integer n,
    String value,
    Boolean fromCache
) {}
