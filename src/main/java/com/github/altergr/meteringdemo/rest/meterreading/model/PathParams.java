package com.github.altergr.meteringdemo.rest.meterreading.model;


import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Positive;

import static com.github.altergr.meteringdemo.domain.validation.ValidationConstants.*;

@Data
@Builder
public class PathParams {

    @Positive
    private Long meterId;

    @Range(min = YEAR_MIN, max = YEAR_MAX)
    private Integer year;

    @Range(min = MONTH_MIN, max = MONTH_MAX)
    private Integer month;

}
