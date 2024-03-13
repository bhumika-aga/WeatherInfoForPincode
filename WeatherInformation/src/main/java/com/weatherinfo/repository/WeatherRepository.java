package com.weatherinfo.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.weatherinfo.model.WeatherInfo;

@Repository
public interface WeatherRepository extends JpaRepository<WeatherInfo, Long> {

	Optional<WeatherInfo> findByPincodeAndDate(Integer pincode, LocalDate date);
}