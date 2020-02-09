package com.github.altergr.meteringdemo.test.acceptance;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.altergr.meteringdemo.domain.entity.Meter;
import com.github.altergr.meteringdemo.domain.entity.MeterReading;
import com.github.altergr.meteringdemo.domain.validation.ValidationError;
import com.github.altergr.meteringdemo.domain.validation.ValidationErrorCode;
import com.github.altergr.meteringdemo.repository.MeterReadingRepository;
import com.github.altergr.meteringdemo.repository.MeterRepository;
import com.github.altergr.meteringdemo.rest.error.ApiErrorResponse;
import com.github.altergr.meteringdemo.rest.error.ApiErrorType;
import com.github.altergr.meteringdemo.rest.meterreading.model.AddReadingRequestPayload;
import com.github.altergr.meteringdemo.rest.meterreading.model.MonthlyReadingResponse;
import com.github.altergr.meteringdemo.service.ModelMapper;
import lombok.RequiredArgsConstructor;
import org.assertj.core.groups.Tuple;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;


@Component
@RequiredArgsConstructor
public class AcceptanceTestsFixture {

    private final ObjectMapper objectMapper;
    private final MeterReadingRepository meterReadingRepository;
    private final MeterRepository meterRepository;
    private final ModelMapper modelMapper;
    private final TestRestTemplate testRestTemplate;


    public <T> T assertAndGetResponse(ResponseEntity<String> response, HttpStatus status, Class<T> responseType) {
        assertThat(response.getStatusCode()).isEqualTo(status);
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
        assertThat(response.getBody()).isNotNull();
        return fromJson(response.getBody(), responseType);
    }

    private <T> T fromJson(String value, Class<T> responseType) {
        try {
            return objectMapper.readValue(value, responseType);
        } catch (JsonProcessingException e) {
            throw new AssertionError("Cannot deserialize response body", e);
        }
    }


    @Transactional
    public List<MeterReading> addMeterReadings(Meter meter, List<AddReadingRequestPayload> readings) {
        List<MeterReading> meterReadings = readings.stream()
                .map(reading -> modelMapper.mapToMeterReading(meter, reading))
                .collect(Collectors.toList());

        return meterReadingRepository.saveAll(meterReadings);
    }

    @Transactional
    public Meter addAMeterWithReadings(List<AddReadingRequestPayload> readings) {
        Meter meter = addAMeter();
        addMeterReadings(meter, readings);
        return meter;
    }

    @Transactional
    public Meter addAMeter() {
        Meter meter = Meter.builder()
                .serialNumber(String.format("EL-%s", System.currentTimeMillis()))
                .build();
        return meterRepository.save(meter);
    }

    public AddReadingRequestPayload newAddReadingRequest(int year, int month, int value) {
        return AddReadingRequestPayload.builder()
                .year(year)
                .month(month)
                .value(value)
                .build();
    }


    public MonthlyReadingResponse getReadingForMonth(Long meterId, Integer year, Integer month) {
        return testRestTemplate.getForEntity(
                "/meter/{meterId}/readings/{year}/{month}",
                MonthlyReadingResponse.class,
                meterId, year, month).getBody();
    }


    public void assertCorrectApiValidationErrorResponse(ApiErrorResponse apiErrorResponse, ValidationErrorCode validationErrorCode) {
        assertThat(apiErrorResponse).isNotNull();
        assertThat(apiErrorResponse.getErrorType()).isEqualTo(ApiErrorType.VALIDATION);
        assertThat(apiErrorResponse.getValidationErrors()).hasSize(1);

        ValidationError validationError = apiErrorResponse.getValidationErrors().get(0);
        assertThat(validationError.getErrorCode()).isEqualTo(validationErrorCode);
    }

    public void assertCorrectValidationConstraints(ApiErrorResponse apiErrorResponse, String... expectedProperties) {
        assertThat(apiErrorResponse).isNotNull();
        Tuple[] expectedErrorCodeAndProperties = Arrays.stream(expectedProperties)
                .map(p -> tuple(ValidationErrorCode.CONSTRAINT_VALIDATION_EXCEPTION, p))
                .toArray(Tuple[]::new);

        assertThat(apiErrorResponse
                .getValidationErrors().stream()
                .map(v -> tuple(v.getErrorCode(), v.getPropertyPath()))
                .distinct()
                .collect(Collectors.toList()))
                .containsExactlyInAnyOrder(expectedErrorCodeAndProperties);
    }

}
