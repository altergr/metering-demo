package com.github.altergr.meteringdemo.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

import javax.persistence.*;

@Getter
@Setter
@Builder
@Entity
@Table(name = "meter_reading")
public class MeterReading {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "meter_id")
    private Meter meter;
    @Column(name = "year")
    private Integer year;
    @Column(name = "month")
    private Integer month;
    @Column(name = "value")
    private Integer value;

    @Tolerate
    public MeterReading() {

    }

    public String getMonthName() {
        return month != null ? MonthName.of(month) : null;
    }

}
