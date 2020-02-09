package com.github.altergr.meteringdemo.rest.meterreading;

import com.github.altergr.meteringdemo.domain.validation.BeanValidator;
import com.github.altergr.meteringdemo.rest.meterreading.model.PathParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PathParamValidator {
    private final BeanValidator validator;

    public void validate(PathParams pathParams) {
        validator.assertValid(pathParams);
    }
}
