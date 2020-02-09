package com.github.altergr.meteringdemo.rest.meterreading.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MonthlyReadingResponse {

    private Integer year;
    private Integer month;
    private String monthName;
    private Integer value;

}
