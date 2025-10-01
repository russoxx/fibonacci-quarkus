package com.labanca.fibonacci.exception;

import com.labanca.fibonacci.dto.ErrorResponse;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        if (exception instanceof InvalidFibonacciInputException) {
            ErrorResponse error = new ErrorResponse(
                400,
                exception.getMessage(),
                "/api/fibonacci"
            );
            return Response.status(400).entity(error).build();
        }

        if (exception instanceof NumberFormatException) {
            ErrorResponse error = new ErrorResponse(
                400,
                "Invalid input format: must be a valid integer",
                "/api/fibonacci"
            );
            return Response.status(400).entity(error).build();
        }

        if (exception instanceof NotFoundException) {
            ErrorResponse error = new ErrorResponse(
                404,
                "Resource not found",
                "/api/fibonacci"
            );
            return Response.status(404).entity(error).build();
        }

        if (exception instanceof WebApplicationException) {
            WebApplicationException webEx = (WebApplicationException) exception;

            if (webEx.getCause() instanceof NumberFormatException) {
                ErrorResponse error = new ErrorResponse(
                    400,
                    "Invalid input format: must be a valid integer",
                    "/api/fibonacci"
                );
                return Response.status(400).entity(error).build();
            }

            ErrorResponse error = new ErrorResponse(
                webEx.getResponse().getStatus(),
                exception.getMessage(),
                "/api/fibonacci"
            );
            return Response.status(webEx.getResponse().getStatus()).entity(error).build();
        }

        ErrorResponse error = new ErrorResponse(
            500,
            "Internal server error: " + exception.getMessage(),
            "/api/fibonacci"
        );
        return Response.status(500).entity(error).build();
    }
}
