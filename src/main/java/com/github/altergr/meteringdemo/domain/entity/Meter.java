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
@Table(name = "meter")
public class Meter {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "serial_number")
    private String serialNumber;

    @Tolerate
    public Meter() {

    }

}
