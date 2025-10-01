package com.labanca.fibonacci.service;

import com.labanca.fibonacci.dto.FibonacciResult;
import com.labanca.fibonacci.exception.InvalidFibonacciInputException;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class FibonacciServiceTest {

    @Inject
    FibonacciService fibonacciService;

    @Test
    void testCalculateFibonacci() {
        FibonacciResult result = fibonacciService.calculate(17);

        assertNotNull(result);
        assertEquals(17, result.n());
        assertEquals("1597", result.value());
        assertFalse(result.fromCache());
    }

    @Test
    void testCalculateFibonacciFromCache() {
        fibonacciService.calculate(5);

        FibonacciResult result = fibonacciService.calculate(5);

        assertNotNull(result);
        assertEquals(5, result.n());
        assertEquals("5", result.value());
        assertTrue(result.fromCache());
    }

    @Test
    void testCalculateFibonacciZero() {
        FibonacciResult result = fibonacciService.calculate(0);

        assertEquals("0", result.value());
    }

    @Test
    void testCalculateFibonacciOne() {
        FibonacciResult result = fibonacciService.calculate(1);

        assertEquals("1", result.value());
    }

    @Test
    void testCalculateFibonacciTwo() {
        FibonacciResult result = fibonacciService.calculate(2);

        assertEquals("1", result.value());
    }

    @Test
    void testCalculateFibonacciLargeNumber() {
        FibonacciResult result = fibonacciService.calculate(50);

        assertEquals("12586269025", result.value());
    }

    @Test
    void testInvalidInputNull() {
        assertThrows(InvalidFibonacciInputException.class, () -> {
            fibonacciService.calculate(null);
        });
    }

    @Test
    void testInvalidInputNegative() {
        InvalidFibonacciInputException exception = assertThrows(
            InvalidFibonacciInputException.class,
            () -> fibonacciService.calculate(-1)
        );

        assertTrue(exception.getMessage().contains("non-negative"));
    }

    @Test
    void testInvalidInputTooLarge() {
        InvalidFibonacciInputException exception = assertThrows(
            InvalidFibonacciInputException.class,
            () -> fibonacciService.calculate(100001)
        );

        assertTrue(exception.getMessage().contains("too large"));
    }
}
