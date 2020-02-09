package com.github.altergr.meteringdemo.test.acceptance;

import com.github.altergr.meteringdemo.Application;
import com.github.altergr.meteringdemo.domain.entity.Meter;
import com.github.altergr.meteringdemo.domain.validation.ValidationErrorCode;
import com.github.altergr.meteringdemo.rest.error.ApiErrorResponse;
import com.github.altergr.meteringdemo.rest.meterreading.model.YearlyReadingsResponse;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GetMeterReadingsInYearApiTest {

    @Autowired
    private AcceptanceTestsFixture fixture;

    @Autowired
    private TestRestTemplate testRestTemplate;


    @Test
    public void givenValidYear_shouldReturnValuesPerMonth() {
        final int year = 2019;
        Meter meter = fixture.addAMeterWithReadings(Lists.newArrayList(
                fixture.newAddReadingRequest(year, 1, 10),
                fixture.newAddReadingRequest(year, 2, 20),
                fixture.newAddReadingRequest(year, 3, 30),
                fixture.newAddReadingRequest(year, 4, 40),
                fixture.newAddReadingRequest(year, 5, 50),
                fixture.newAddReadingRequest(year, 6, 60),
                fixture.newAddReadingRequest(year, 7, 70),
                fixture.newAddReadingRequest(year, 8, 80),
                fixture.newAddReadingRequest(year, 9, 90),
                fixture.newAddReadingRequest(year, 10, 100),
                fixture.newAddReadingRequest(year, 11, 110),
                fixture.newAddReadingRequest(year, 12, 120)
        ));

        ResponseEntity<String> responseEntity = testRestTemplate.getForEntity(
                "/meter/{meterId}/readings/{year}",
                String.class,
                meter.getId(), year);

        YearlyReadingsResponse response = fixture.assertAndGetResponse(responseEntity,
                HttpStatus.OK,
                YearlyReadingsResponse.class);

        assertThat(response).isNotNull();
        assertThat(response.getYear()).isEqualTo(year);
        assertThat(response.getReadings()).isNotNull();

        assertThat(response.getReadings()
                .stream()
                .map(reading -> tuple(reading.getMonth(), reading.getValue(), reading.getMonthName())))
                .containsExactly(
                        tuple(1, 10, "January"),
                        tuple(2, 20, "February"),
                        tuple(3, 30, "March"),
                        tuple(4, 40, "April"),
                        tuple(5, 50, "May"),
                        tuple(6, 60, "June"),
                        tuple(7, 70, "July"),
                        tuple(8, 80, "August"),
                        tuple(9, 90, "September"),
                        tuple(10, 100, "October"),
                        tuple(11, 110, "November"),
                        tuple(12, 120, "December")
                );
    }

    @Test
    public void givenInvalidPathParams_shouldFail() {

        ResponseEntity<String> responseEntity = testRestTemplate.getForEntity(
                "/meter/{meterId}/readings/{year}",
                String.class,
                -1, -2018);

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
                "/meter/{meterId}/readings/{year}",
                String.class,
                meterId, 2020);

        ApiErrorResponse response = fixture.assertAndGetResponse(
                responseEntity,
                HttpStatus.BAD_REQUEST,
                ApiErrorResponse.class);

        fixture.assertCorrectApiValidationErrorResponse(response, ValidationErrorCode.METER_DOES_NOT_EXIST);

    }


}
