package com.github.altergr.meteringdemo.rest.meterreading.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class YearlyReadingsResponse {

    private Integer year;
    private List<MonthlyReadingResponse> readings;

}
