package com.github.altergr.meteringdemo.service.model;

import com.github.altergr.meteringdemo.rest.meterreading.model.AddReadingRequestPayload;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddMeterReadingData {

    private Long meterId;
    private AddReadingRequestPayload requestPayload;

}
