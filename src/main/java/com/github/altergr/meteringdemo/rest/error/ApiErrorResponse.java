package com.github.altergr.meteringdemo.rest.error;

import com.github.altergr.meteringdemo.domain.validation.ValidationError;
import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ApiErrorResponse {

    private ApiErrorType errorType;
    private String message;
    @Builder.Default
    private List<ValidationError> validationErrors = Lists.newArrayList();
}
