package com.github.altergr.meteringdemo.test.acceptance;

import com.github.altergr.meteringdemo.Application;
import com.github.altergr.meteringdemo.domain.entity.Meter;
import com.github.altergr.meteringdemo.domain.validation.ValidationErrorCode;
import com.github.altergr.meteringdemo.rest.error.ApiErrorResponse;
import com.github.altergr.meteringdemo.rest.meterreading.model.YearlyAggregatedReadingsResponse;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GetMeterReadingAggregatedTest {

    @Autowired
    private AcceptanceTestsFixture fixture;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void givenValidYear_shouldReturnSumForYear() {
        final int year = 2020;
        Meter meter = fixture.addAMeterWithReadings(Lists.newArrayList(
                fixture.newAddReadingRequest(year, 1, 5),
                fixture.newAddReadingRequest(year, 2, 25),
                fixture.newAddReadingRequest(year, 3, 30),
                fixture.newAddReadingRequest(year, 8, 40)
        ));
        final int expectedAggregatedValue = 100;

        ResponseEntity<String> responseEntity = testRestTemplate.getForEntity(
                "/meter/{meterId}/readings/{year}/aggregated",
                String.class,
                meter.getId(), year);

        YearlyAggregatedReadingsResponse response = fixture.assertAndGetResponse(
                responseEntity,
                HttpStatus.OK,
                YearlyAggregatedReadingsResponse.class);

        assertThat(response).isNotNull();
        assertThat(response.getYear()).isEqualTo(year);
        assertThat(response.getValue()).isEqualTo(expectedAggregatedValue);

    }

    @Test
    public void givenInvalidMeterAndYear_shouldFail() {

        ResponseEntity<String> responseEntity = testRestTemplate.getForEntity(
                "/meter/{meterId}/readings/{year}/aggregated",
                String.class,
                -1, 90000);

        ApiErrorResponse response = fixture.assertAndGetResponse(
                responseEntity,
                HttpStatus.BAD_REQUEST,
                ApiErrorResponse.class);

        fixture.assertCorrectValidationConstraints(response, "meterId", "year");

    }


    @Test
    public void givenNonExistingMeter_shouldFail() {

        Long meterId = 1000000L;
        ResponseEntity<String> responseEntity = testRestTemplate.getForEntity(
                "/meter/{meterId}/readings/{year}/aggregated",
                String.class,
                meterId, 2018);

        ApiErrorResponse response = fixture.assertAndGetResponse(
                responseEntity,
                HttpStatus.BAD_REQUEST,
                ApiErrorResponse.class);

        fixture.assertCorrectApiValidationErrorResponse(response, ValidationErrorCode.METER_DOES_NOT_EXIST);

    }


}
