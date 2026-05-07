package com.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler 
{
	// Validation Error
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleValidation(MethodArgumentNotValidException ex,
                                          HttpServletRequest request) {

        String message = ex.getBindingResult()
                           .getFieldErrors()
                           .get(0)
                           .getDefaultMessage();

        return new ErrorResponse(
                null, HttpStatus.BAD_REQUEST.value(),
                "BAD_REQUEST",
                message,
                request.getRequestURI()
        );
    }

    // Generic Exception
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleException(Exception ex,
                                         HttpServletRequest request) {

        return new ErrorResponse(
                null, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "INTERNAL_SERVER_ERROR",
                ex.getMessage(),
                request.getRequestURI()
        );
    }
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorResponse handleNotFound(ResourceNotFoundException ex,
                                        HttpServletRequest request) {

        return new ErrorResponse(
                null, HttpStatus.NOT_FOUND.value(),
                "NOT_FOUND",
                ex.getMessage(),
                request.getRequestURI()
        );
    }
}
