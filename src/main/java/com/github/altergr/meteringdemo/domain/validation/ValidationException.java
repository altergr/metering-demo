package com.github.altergr.meteringdemo.domain.validation;

import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.List;

@Getter
public class ValidationException extends RuntimeException {
    private static final long serialVersionUID = 439150355475606182L;

    private List<ValidationError> errors;

    public ValidationException(String message, List<ValidationError> errors) {
        super(message);
        this.errors = errors;
    }

    public ValidationException(String message, ValidationError error) {
        super(message);
        this.errors = Lists.newArrayList(error);
    }

    public ValidationException(ValidationError error) {
        this("Validation error", error);
    }

}
