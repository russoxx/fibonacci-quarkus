package com.labanca.fibonacci.service;

import com.labanca.fibonacci.dto.FibonacciResult;
import com.labanca.fibonacci.entity.FibonacciCache;
import com.labanca.fibonacci.exception.InvalidFibonacciInputException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.math.BigInteger;
import java.util.Optional;

@ApplicationScoped
public class FibonacciService {

    private static final int MAX_INPUT = 100000;

    @Transactional
    public FibonacciResult calculate(Integer n) {
        validateInput(n);

        Optional<FibonacciCache> cached = FibonacciCache.findByN(n);
        if (cached.isPresent()) {
            return new FibonacciResult(n, cached.get().fibonacciValue, true);
        }

        String value = computeFibonacci(n);

        FibonacciCache cache = new FibonacciCache();
        cache.n = n;
        cache.fibonacciValue = value;
        cache.persist();

        return new FibonacciResult(n, value, false);
    }

    private void validateInput(Integer n) {
        if (n == null) {
            throw new InvalidFibonacciInputException("Input cannot be null");
        }
        if (n < 0) {
            throw new InvalidFibonacciInputException("Input must be non-negative. Received: " + n);
        }
        if (n > MAX_INPUT) {
            throw new InvalidFibonacciInputException(
                "Input too large. Maximum allowed: " + MAX_INPUT + ", received: " + n
            );
        }
    }

    private String computeFibonacci(Integer n) {
        if (n == 0) return "0";
        if (n == 1 || n == 2) return "1";

        BigInteger prev = BigInteger.ONE;
        BigInteger current = BigInteger.ONE;

        for (int i = 3; i <= n; i++) {
            BigInteger next = prev.add(current);
            prev = current;
            current = next;
        }

        return current.toString();
    }
}
