package com.github.altergr.meteringdemo.rest.error;

import com.github.altergr.meteringdemo.domain.exception.EntityNotFoundException;
import com.github.altergr.meteringdemo.domain.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class ApiErrorHandler {


    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleBusinessException(ValidationException e) {
        log.warn("Business exception:", e);

        return ApiErrorResponse.builder()
                .errorType(ApiErrorType.VALIDATION)
                .message("Validation error")
                .validationErrors(e.getErrors())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleEntityNotFoundException(EntityNotFoundException e) {
        log.warn("Entity not found:", e);

        return ApiErrorResponse.builder()
                .errorType(ApiErrorType.NOT_FOUND)
                .message(e.getMessage())
                .build();

    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse handleExceptionDefault(Exception e) {
        log.error("Internal error", e);

        return ApiErrorResponse.builder()
                .errorType(ApiErrorType.ERROR)
                .message("Internal server error")
                .build();
    }


}
