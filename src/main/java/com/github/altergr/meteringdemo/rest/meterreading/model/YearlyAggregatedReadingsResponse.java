package com.github.altergr.meteringdemo.rest.meterreading.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class YearlyAggregatedReadingsResponse {

    private Integer year;
    private Integer value;

}
