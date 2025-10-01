package com.labanca.fibonacci.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class FibonacciResourceTest {

    @Test
    void testHealthEndpoint() {
        given()
            .when().get("/api/fibonacci/health")
            .then()
                .statusCode(200)
                .contentType(ContentType.TEXT)
                .body(is("Fibonacci API is running!"));
    }

    @Test
    void testGetFibonacci() {
        given()
            .when().get("/api/fibonacci/10")
            .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("n", is(10))
                .body("value", is("55"))
                .body("fromCache", is(false));
    }

    @Test
    void testGetFibonacciFromCache() {
        given().when().get("/api/fibonacci/5").then().statusCode(200);

        given()
            .when().get("/api/fibonacci/5")
            .then()
                .statusCode(200)
                .body("n", is(5))
                .body("value", is("5"))
                .body("fromCache", is(true));
    }

    @Test
    void testGetFibonacciZero() {
        given()
            .when().get("/api/fibonacci/0")
            .then()
                .statusCode(200)
                .body("n", is(0))
                .body("value", is("0"));
    }

    @Test
    void testGetFibonacciOne() {
        given()
            .when().get("/api/fibonacci/1")
            .then()
                .statusCode(200)
                .body("n", is(1))
                .body("value", is("1"));
    }

    @Test
    void testGetFibonacciLargeNumber() {
        given()
            .when().get("/api/fibonacci/50")
            .then()
                .statusCode(200)
                .body("n", is(50))
                .body("value", is("12586269025"));
    }

    @Test
    void testInvalidInputNegative() {
        given()
            .when().get("/api/fibonacci/-1")
            .then()
                .statusCode(400)
                .body("status", is(400))
                .body("message", containsString("non-negative"));
    }

    @Test
    void testInvalidInputTooLarge() {
        given()
            .when().get("/api/fibonacci/999999")
            .then()
                .statusCode(400)
                .body("status", is(400))
                .body("message", containsString("too large"));
    }

    @Test
    void testInvalidInputNotANumber() {
        int statusCode = given()
            .when().get("/api/fibonacci/abc")
            .then()
                .extract().statusCode();

        assertTrue(statusCode == 400 || statusCode == 404);
    }

    @Test
    void testGetStats() {
        given().when().get("/api/fibonacci/15").then().statusCode(200);
        given().when().get("/api/fibonacci/15").then().statusCode(200);
        given().when().get("/api/fibonacci/20").then().statusCode(200);

        given()
            .when().get("/api/fibonacci/stats?limit=5")
            .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", is(greaterThan(0)));
    }

    @Test
    void testGetAllStats() {
        given().when().get("/api/fibonacci/25").then().statusCode(200);

        given()
            .when().get("/api/fibonacci/stats/all")
            .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }
}
