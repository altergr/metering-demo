package com.github.altergr.meteringdemo.domain.validation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidationError {
    private ValidationErrorCode errorCode;
    private String propertyPath;
    private String message;
}
