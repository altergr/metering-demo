package com.github.altergr.meteringdemo.domain.validation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

public class BeanValidatorTest {

    private BeanValidator beanValidator;


    @BeforeEach
    public void setUp() {

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        beanValidator = new BeanValidator(validator);
    }

    @Test
    public void givenEmptyJavaxErrors_returnsEmpty() {

        List<ValidationError> errors = beanValidator.validate(ValidatedDummy.valid());

        Assertions.assertThat(errors).isEmpty();

    }


    @Test
    public void givenJavaxErrors_transformsToValidationErrors() {
        ValidatedDummy validatedObj = ValidatedDummy.idNameInvalid();
        List<ValidationError> errors = beanValidator.validate(validatedObj);

        assertThat(errors.stream().map(it -> tuple(it.getErrorCode(), it.getMessage(), it.getPropertyPath())))
                .containsExactlyInAnyOrder(
                        tuple(ValidationErrorCode.CONSTRAINT_VALIDATION_EXCEPTION, ValidatedDummy.ID_MESSAGE, "id"),
                        tuple(ValidationErrorCode.CONSTRAINT_VALIDATION_EXCEPTION, ValidatedDummy.NAME_MESSAGE, "name"));

    }


    @Test
    public void givenJavaxErrorsOnNestedObjects_transformsToValidationErrors() {
        ValidatedDummy validatedObj = ValidatedDummy.nestedInvalid();
        List<ValidationError> errors = beanValidator.validate(validatedObj);

        assertThat(errors.stream().map(it -> tuple(it.getErrorCode(), it.getMessage(), it.getPropertyPath())))
                .containsExactlyInAnyOrder(
                        tuple(ValidationErrorCode.CONSTRAINT_VALIDATION_EXCEPTION, ValidatedDummy.NESTED_ID_MESSAGE, "nested.id"));

    }


}
