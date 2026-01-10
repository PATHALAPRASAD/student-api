package com.example.student_api.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private Throwable extractCause(Throwable ex) {
        while(ex.getCause() != null && ex.getCause() != ex) {
            ex = ex.getCause();
        }
        return ex;
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex, HttpServletRequest request) {
        logger.warn("Exception thrown at endpoint; {}", request.getRequestURI());
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), "Unsupported Content-Type. Please use a supported media type (e.g., application/json).", null), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> badRequestExceptionHandler(BadRequestException exception) {
        logger.warn("Bad request exception: {}", exception.getMessage());
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), null, List.of(exception.getMessage())), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException exception) {
        logger.warn("Type mismatch exception: {}", exception.getMessage());
        String requiredType = exception.getRequiredType() == null ? "Integer" : Arrays.stream(String.valueOf(exception.getRequiredType()).split("\\.")).reduce((first, second) -> second).orElse("");
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), null, List.of(String.format("%s field has invalid parameter, please provide required type: %s", exception.getName(), requiredType))), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(MethodArgumentNotValidException exception) {
        logger.warn("Method argument not valid exception: {}", exception.getMessage());
        List<String> errorMessages = exception.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.toList());
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), null, errorMessages), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> conflictExceptionHandler(ConflictException exception) {
        logger.warn("Conflict exception: {}", exception.getMessage());
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.CONFLICT.value(), exception.getMessage(), null), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> illegalArgumentExceptionHandler(IllegalArgumentException exception) {
        logger.warn("Illegal argument exception: {}", exception.getMessage());
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<ErrorResponse> webExchangeBindExceptionHandler(WebExchangeBindException exception) {
        logger.warn("Invalid argument exception: {}", exception.getMessage());
        var errors = exception.getFieldErrors().stream().map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage()).collect(Collectors.toList());
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), null, errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    public ResponseEntity<ErrorResponse> invalidDataAccessApiUsageExceptionHandler(InvalidDataAccessApiUsageException exception) {
        logger.warn("Invalid DB property: {}", exception.getMessage());
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), extractInvalidColumnName(exception.getMessage()), null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ErrorResponse> entityNotFoundExceptionHandler(StudentNotFoundException exception) {
        logger.warn("Not found exception: {}", exception.getMessage());
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage(), null), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException exception) {
        logger.warn("Conflict exception: {}", exception.getMessage());
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.CONFLICT.value(), null, null), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleReqConstraintViolationException(jakarta.validation.ConstraintViolationException exception) {
        logger.warn("Request constraint violation exception: {}", exception.getMessage());
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), null, List.of(extractQueryErrorName(exception.getMessage()))), HttpStatus.BAD_REQUEST);
    }

    // 2 more methods pending + below 3 private static methods

    private static String extractFieldName(String errorMessage) {
        return "";
    }

    private static String extractQueryErrorName(String errorMessage) {
        return "";
    }

    private static String extractInvalidColumnName(String errorMessage) {
        return "";
    }

}
