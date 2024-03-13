package com.weatherinfo.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weatherinfo.model.WeatherInfo;
import com.weatherinfo.service.WeatherService;

@RestController
@CrossOrigin("*")
@RequestMapping("/weather")
public class WeatherController {

	@Autowired
	private WeatherService weatherService;

	@GetMapping("/getInfo")
	public ResponseEntity<WeatherInfo> getWeatherInfo(@RequestParam Integer pincode,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
		WeatherInfo weather = null;

		try {
			weather = this.weatherService.getWeatherInfo(pincode, date);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(weather, HttpStatus.OK);
	}
}