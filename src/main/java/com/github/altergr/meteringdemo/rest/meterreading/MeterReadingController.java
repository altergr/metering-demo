package com.github.altergr.meteringdemo.rest.meterreading;

import com.github.altergr.meteringdemo.rest.meterreading.model.*;
import com.github.altergr.meteringdemo.service.MeterReadingService;
import com.github.altergr.meteringdemo.service.model.AddMeterReadingData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MeterReadingController {

    private final MeterReadingService meterReadingService;
    private final PathParamValidator pathParamValidator;

    @GetMapping("/meter/{meterId}/readings/{year}")
    public YearlyReadingsResponse getReadingsForYear(
            @PathVariable Long meterId,
            @PathVariable Integer year) {

        pathParamValidator.validate(PathParams.builder()
                .meterId(meterId)
                .year(year)
                .build());

        return meterReadingService.listReadings(meterId, year);
    }

    @Validated
    @GetMapping("/meter/{meterId}/readings/{year}/{month}")
    public MonthlyReadingResponse getReadingsForYearAndMonth(
            @PathVariable Long meterId,
            @PathVariable Integer year,
            @PathVariable Integer month) {

        pathParamValidator.validate(PathParams.builder()
                .meterId(meterId)
                .year(year)
                .month(month)
                .build());

        return meterReadingService.getReading(meterId, year, month);
    }

    @GetMapping("/meter/{meterId}/readings/{year}/aggregated")
    public YearlyAggregatedReadingsResponse getAggregatedReadingsForYear(
            @PathVariable Long meterId,
            @PathVariable Integer year) {

        pathParamValidator.validate(PathParams.builder()
                .meterId(meterId)
                .year(year)
                .build());

        return meterReadingService.getAggregatedReadingsForYear(meterId, year);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/meter/{meterId}/readings")
    public Long addReading(@PathVariable Long meterId, @RequestBody AddReadingRequestPayload requestPayload) {

        pathParamValidator.validate(PathParams.builder()
                .meterId(meterId)
                .build());

        return meterReadingService.addReading(new AddMeterReadingData(meterId, requestPayload));
    }
}
