package com.github.altergr.meteringdemo.service;

import com.github.altergr.meteringdemo.domain.validation.ValidationError;
import com.github.altergr.meteringdemo.domain.validation.ValidationErrorCode;
import com.github.altergr.meteringdemo.domain.validation.ValidationException;
import com.github.altergr.meteringdemo.repository.MeterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MeterExistsValidator {
    private final MeterRepository meterRepository;

    public void validate(Long meterId, String propertyName) {
        if (!meterRepository.existsById(meterId)) {
            throw new ValidationException(new ValidationError(ValidationErrorCode.METER_DOES_NOT_EXIST,
                    propertyName,
                    String.format("Meter with id %s does not exist", meterId)));
        }
    }

    public void validate(Long meterId) {
        validate(meterId, "meterId");
    }

}
