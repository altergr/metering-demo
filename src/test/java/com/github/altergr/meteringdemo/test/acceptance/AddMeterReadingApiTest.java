package com.github.altergr.meteringdemo.test.acceptance;

import com.github.altergr.meteringdemo.Application;
import com.github.altergr.meteringdemo.domain.entity.Meter;
import com.github.altergr.meteringdemo.domain.validation.ValidationErrorCode;
import com.github.altergr.meteringdemo.rest.error.ApiErrorResponse;
import com.github.altergr.meteringdemo.rest.meterreading.model.AddReadingRequestPayload;
import com.github.altergr.meteringdemo.rest.meterreading.model.MonthlyReadingResponse;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddMeterReadingApiTest {

    @Autowired
    private AcceptanceTestsFixture fixture;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void givenNewReading_shouldCreateReading() {
        Meter meter = fixture.addAMeter();
        final int year = 2020;
        final int month = 5;
        AddReadingRequestPayload addMeterReadingRequest = fixture.newAddReadingRequest(year, month, 890);

        ResponseEntity<String> responseEntity = testRestTemplate.postForEntity(
                "/meter/{meterId}/readings",
                addMeterReadingRequest,
                String.class,
                meter.getId());

        Long addedReadingId = fixture.assertAndGetResponse(responseEntity, HttpStatus.CREATED, Long.class);
        assertThat(addedReadingId).isNotNull();

        MonthlyReadingResponse addedReading = fixture.getReadingForMonth(meter.getId(), year, month);

        assertThat(addedReading).isNotNull();
        assertThat(addedReading.getValue()).isEqualTo(890);
    }


    @Test
    public void givenAlreadyExistingReading_shouldFail() {
        final int year = 2020;
        final int month = 5;

        Meter meter = fixture.addAMeterWithReadings(Lists.newArrayList(
                fixture.newAddReadingRequest(year, month, 100)
        ));

        AddReadingRequestPayload addMeterReadingRequest = fixture.newAddReadingRequest(year, month, 900);

        ResponseEntity<String> response = testRestTemplate.postForEntity(
                "/meter/{meterId}/readings", addMeterReadingRequest, String.class, meter.getId());

        ApiErrorResponse apiErrorResponse = fixture.assertAndGetResponse(response, HttpStatus.BAD_REQUEST, ApiErrorResponse.class);

        fixture.assertCorrectApiValidationErrorResponse(apiErrorResponse, ValidationErrorCode.METER_READING_ALREADY_EXISTS);

    }

    @Test
    public void givenNonExistingMeter_shouldFail() {
        final Long meterId = 1000000L;

        AddReadingRequestPayload addMeterReadingRequest = fixture.newAddReadingRequest(2020, 7, 900);

        ResponseEntity<String> response = testRestTemplate.postForEntity(
                "/meter/{meterId}/readings", addMeterReadingRequest, String.class, meterId);

        ApiErrorResponse apiErrorResponse = fixture.assertAndGetResponse(
                response,
                HttpStatus.BAD_REQUEST,
                ApiErrorResponse.class);

        fixture.assertCorrectApiValidationErrorResponse(apiErrorResponse, ValidationErrorCode.METER_DOES_NOT_EXIST);

    }

    @Test
    public void givenInvalidRequestPayload_shouldFail() {

        Meter meter = fixture.addAMeter();
        AddReadingRequestPayload addMeterReadingRequest = fixture.newAddReadingRequest(-2020, -5, -99);

        ResponseEntity<String> responseEntity = testRestTemplate.postForEntity(
                "/meter/{meterId}/readings",
                addMeterReadingRequest,
                String.class,
                meter.getId());

        ResponseEntity<String> response = testRestTemplate.postForEntity(
                "/meter/{meterId}/readings", addMeterReadingRequest, String.class, meter.getId());

        ApiErrorResponse apiErrorResponse = fixture.assertAndGetResponse(response, HttpStatus.BAD_REQUEST, ApiErrorResponse.class);

        fixture.assertCorrectValidationConstraints(apiErrorResponse, "year", "month", "value");

    }


}
