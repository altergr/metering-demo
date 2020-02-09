package com.github.altergr.meteringdemo.rest.meterreading.model;

import com.github.altergr.meteringdemo.domain.validation.ValidationConstants;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Builder
public class AddReadingRequestPayload {

    @NotNull
    @Range(min = ValidationConstants.YEAR_MIN, max = ValidationConstants.YEAR_MAX)
    private Integer year;

    @NotNull
    @Range(min = ValidationConstants.MONTH_MIN, max = ValidationConstants.MONTH_MAX)
    private Integer month;

    @NotNull
    @Positive
    private Integer value;
}
