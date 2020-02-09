package com.github.altergr.meteringdemo.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "client")
public class Client {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToOne(optional = false)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToOne(optional = false)
    @JoinColumn(name = "meter_id")
    private Meter meter;
}
