package com.github.altergr.meteringdemo.domain.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BeanValidator {

    private final Validator validator;

    public <T> List<ValidationError> validate(T validatedObj) {
        Set<ConstraintViolation<T>> errors = validator
                .validate(validatedObj);

        return errors.stream().map(
                violation -> new ValidationError(
                        ValidationErrorCode.CONSTRAINT_VALIDATION_EXCEPTION,
                        violation.getPropertyPath().toString(),
                        violation.getMessage()))
                .collect(Collectors.toList());

    }

    public <T> void assertValid(T validatedObject) {
        List<ValidationError> errors = validate(validatedObject);
        if (!errors.isEmpty()) {
            throw new ValidationException("Validation failed", errors);
        }

    }
}
