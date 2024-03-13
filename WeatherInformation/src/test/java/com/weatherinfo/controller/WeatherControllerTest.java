package com.weatherinfo.controller;

import static org.mockito.Mockito.verify;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.weatherinfo.model.WeatherInfo;
import com.weatherinfo.service.WeatherService;

@SpringBootTest
class WeatherControllerTest {

	@Mock
	private WeatherService weatherService;

	@Mock
	private WeatherInfo weather;

	@InjectMocks
	private WeatherController weatherController;

	@BeforeEach
	public void setUp() {
		this.weather = new WeatherInfo();
		weather.setPincode(12345);
		weather.setLocation("XYZ");
		LocalDate date = LocalDate.parse("2024-03-13");
		weather.setDate(date);
		weather.setTemperature(20.0);
		weather.setHumidity(50);
		weather.setPressure(1000);
		weather.setWindSpeed(3.46);
		weather.setDescription("clear");
	}

	@Test
	void testGetWeatherInfo() throws Exception {
		Mockito.when(weatherService.getWeatherInfo(12345, LocalDate.parse("2024-03-13"))).thenReturn(weather);
		ResponseEntity<WeatherInfo> actualWeather = weatherController.getWeatherInfo(12345,
				LocalDate.parse("2024-03-13"));

		Assertions.assertEquals(HttpStatus.OK, actualWeather.getStatusCode());
		Assertions.assertEquals(weather.getTemperature(), actualWeather.getBody().getTemperature());
		Assertions.assertEquals(weather.getHumidity(), actualWeather.getBody().getHumidity());

		verify(weatherService, Mockito.times(1)).getWeatherInfo(12345, LocalDate.parse("2024-03-13"));
	}
}