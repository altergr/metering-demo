package com.github.altergr.meteringdemo.service;

import com.github.altergr.meteringdemo.domain.validation.BeanValidator;
import com.github.altergr.meteringdemo.domain.validation.ValidationError;
import com.github.altergr.meteringdemo.domain.validation.ValidationErrorCode;
import com.github.altergr.meteringdemo.domain.validation.ValidationException;
import com.github.altergr.meteringdemo.repository.MeterReadingRepository;
import com.github.altergr.meteringdemo.rest.meterreading.model.AddReadingRequestPayload;
import com.github.altergr.meteringdemo.service.model.AddMeterReadingData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AddMeterReadingValidator {

    private final MeterExistsValidator meterExistsValidator;
    private final MeterReadingRepository meterReadingRepository;
    private final BeanValidator beanValidator;

    public void validate(AddMeterReadingData addMeterReadingData) {
        AddReadingRequestPayload requestPayload = addMeterReadingData.getRequestPayload();
        beanValidator.assertValid(requestPayload);

        Long meterId = addMeterReadingData.getMeterId();
        meterExistsValidator.validate(meterId, "meterId");

        if (meterReadingRepository.existsByMeterIdAndYearAndMonthOrderByMonth(
                meterId,
                requestPayload.getYear(),
                requestPayload.getMonth())) {

            throw new ValidationException(new ValidationError(ValidationErrorCode.METER_READING_ALREADY_EXISTS,
                    "meterId",
                    String.format("Meter reading already exists for %s/%s/%s",
                            meterId,
                            requestPayload.getYear(),
                            requestPayload.getMonth())));

        }

    }


}



