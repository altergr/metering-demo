package com.github.altergr.meteringdemo.repository;

import com.github.altergr.meteringdemo.domain.entity.Meter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeterRepository extends JpaRepository<Meter, Long> {

}
