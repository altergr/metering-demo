package com.github.altergr.meteringdemo.service;

import com.github.altergr.meteringdemo.domain.entity.Meter;
import com.github.altergr.meteringdemo.domain.entity.MeterReading;
import com.github.altergr.meteringdemo.domain.exception.EntityNotFoundException;
import com.github.altergr.meteringdemo.repository.MeterReadingRepository;
import com.github.altergr.meteringdemo.repository.MeterRepository;
import com.github.altergr.meteringdemo.rest.meterreading.model.MonthlyReadingResponse;
import com.github.altergr.meteringdemo.rest.meterreading.model.YearlyAggregatedReadingsResponse;
import com.github.altergr.meteringdemo.rest.meterreading.model.YearlyReadingsResponse;
import com.github.altergr.meteringdemo.service.model.AddMeterReadingData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MeterReadingService {

    private final MeterReadingRepository meterReadingRepository;
    private final MeterRepository meterRepository;
    private final AddMeterReadingValidator addReadingValidator;
    private final ModelMapper modelMapper;
    private final MeterExistsValidator meterExistsValidator;

    @Transactional
    public YearlyReadingsResponse listReadings(Long meterId, Integer year) {
        meterExistsValidator.validate(meterId);
        List<MeterReading> readings = meterReadingRepository.findByMeterIdAndYearOrderByMonth(
                meterId,
                year);

        return YearlyReadingsResponse.builder()
                .year(year)
                .readings(modelMapper.mapToMonthlyReadingResponseList(readings))
                .build();

    }


    @Transactional
    public MonthlyReadingResponse getReading(Long meterId, Integer year, Integer month) {
        meterExistsValidator.validate(meterId);
        Optional<MeterReading> reading = meterReadingRepository.findByMeterIdAndYearAndMonthOrderByMonth(
                meterId,
                year,
                month);

        return reading.map(modelMapper::mapToMonthlyReadingResponse)
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format("No reading found for %s/%s/%s", meterId, year, month)));

    }

    @Transactional
    public YearlyAggregatedReadingsResponse getAggregatedReadingsForYear(Long meterId, Integer year) {
        meterExistsValidator.validate(meterId);
        Integer readingsSum = meterReadingRepository.getMeterReadingsSumForYear(meterId, year);
        return YearlyAggregatedReadingsResponse.builder()
                .year(year)
                .value(readingsSum)
                .build();
    }


    @Transactional
    public Long addReading(AddMeterReadingData addMeterReadingData) {
        addReadingValidator.validate(addMeterReadingData);

        Meter meter = meterRepository.getOne(addMeterReadingData.getMeterId());
        MeterReading meterReading = modelMapper.mapToMeterReading(meter, addMeterReadingData.getRequestPayload());

        MeterReading addedReading = meterReadingRepository.save(meterReading);
        return addedReading.getId();

    }


}
