package com.github.altergr.meteringdemo.service;

import com.github.altergr.meteringdemo.domain.entity.Meter;
import com.github.altergr.meteringdemo.domain.entity.MeterReading;
import com.github.altergr.meteringdemo.rest.meterreading.model.AddReadingRequestPayload;
import com.github.altergr.meteringdemo.rest.meterreading.model.MonthlyReadingResponse;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class ModelMapper {

    public MeterReading mapToMeterReading(Meter meter, AddReadingRequestPayload addMeterReadingRequest) {
        return MeterReading.builder()
                .meter(meter)
                .month(addMeterReadingRequest.getMonth())
                .year(addMeterReadingRequest.getYear())
                .value(addMeterReadingRequest.getValue())
                .build();
    }


    public MonthlyReadingResponse mapToMonthlyReadingResponse(MeterReading reading) {
        return MonthlyReadingResponse.builder()
                .year(reading.getYear())
                .month(reading.getMonth())
                .value(reading.getValue())
                .monthName(reading.getMonthName())
                .build();

    }

    public List<MonthlyReadingResponse> mapToMonthlyReadingResponseList(List<MeterReading> readings) {
        return readings.stream()
                .map(this::mapToMonthlyReadingResponse)
                .collect(toList());

    }


}
