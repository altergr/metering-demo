package com.github.altergr.meteringdemo.test.acceptance;

import com.github.altergr.meteringdemo.Application;
import com.github.altergr.meteringdemo.domain.entity.Meter;
import com.github.altergr.meteringdemo.domain.validation.ValidationErrorCode;
import com.github.altergr.meteringdemo.rest.error.ApiErrorResponse;
import com.github.altergr.meteringdemo.rest.meterreading.model.MonthlyReadingResponse;
import com.google.common.collect.Lists;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GetSingleMeterReadingApiTest {

    @Autowired
    private AcceptanceTestsFixture fixture;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void givenValidMeterYearAndMonth_shouldReturnCorrectReading() {
        final int year = 2018;
        final int month = 9;

        Meter meter = fixture.addAMeterWithReadings(Lists.newArrayList(
                fixture.newAddReadingRequest(year, month, 99)
        ));

        ResponseEntity<String> responseEntity = testRestTemplate.getForEntity(
                "/meter/{meterId}/readings/{year}/{month}",
                String.class,
                meter.getId(), year, month);

        MonthlyReadingResponse response = fixture.assertAndGetResponse(
                responseEntity,
                HttpStatus.OK,
                MonthlyReadingResponse.class);

        assertThat(response).isNotNull();
        assertThat(response.getYear()).isEqualTo(year);
        assertThat(response.getMonth()).isEqualTo(month);
        assertThat(response.getMonthName()).isEqualTo("September");
        assertThat(response.getValue()).isEqualTo(99);

    }

    @Test
    public void givenInvalidMeterYearAndMonth_shouldFail() {

        ResponseEntity<String> responseEntity = testRestTemplate.getForEntity(
                "/meter/{meterId}/readings/{year}/{month}",
                String.class,
                -1, -2018, 100);

        ApiErrorResponse response = fixture.assertAndGetResponse(
                responseEntity,
                HttpStatus.BAD_REQUEST,
                ApiErrorResponse.class);

        fixture.assertCorrectValidationConstraints(response, "meterId", "year", "month");

    }

    @Test
    public void givenNonExistingMeter_shouldFail() {

        Long meterId = 1000000L;
        ResponseEntity<String> responseEntity = testRestTemplate.getForEntity(
                "/meter/{meterId}/readings/{year}/{month}",
                String.class,
                meterId, 2018, 1);

        ApiErrorResponse response = fixture.assertAndGetResponse(
                responseEntity,
                HttpStatus.BAD_REQUEST,
                ApiErrorResponse.class);

        fixture.assertCorrectApiValidationErrorResponse(response, ValidationErrorCode.METER_DOES_NOT_EXIST);

    }


    @Test
    public void givenNonExistingMeterReading_shouldFail() {
        Meter meter = fixture.addAMeter();

        ResponseEntity<String> responseEntity = testRestTemplate.getForEntity(
                "/meter/{meterId}/readings/{year}/{month}",
                String.class,
                meter.getId(), 2018, 1);

        ApiErrorResponse response = fixture.assertAndGetResponse(
                responseEntity,
                HttpStatus.NOT_FOUND,
                ApiErrorResponse.class);

        assertThat(response).isNotNull();
        Assertions.assertThat(response.getValidationErrors()).isEmpty();

    }

}
