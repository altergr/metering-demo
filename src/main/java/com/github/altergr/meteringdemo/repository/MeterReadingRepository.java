package com.github.altergr.meteringdemo.repository;

import com.github.altergr.meteringdemo.domain.entity.MeterReading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MeterReadingRepository extends JpaRepository<MeterReading, Long> {

    Optional<MeterReading> findByMeterIdAndYearAndMonthOrderByMonth(Long meterId, int year, int month);

    boolean existsByMeterIdAndYearAndMonthOrderByMonth(Long meterId, int year, int month);

    List<MeterReading> findByMeterIdAndYearOrderByMonth(Long meterId, int year);

    @Query("select sum(m.value) from MeterReading m where m.meter.id = :meterId and m.year = :year ")
    Integer getMeterReadingsSumForYear(Long meterId, int year);

}
