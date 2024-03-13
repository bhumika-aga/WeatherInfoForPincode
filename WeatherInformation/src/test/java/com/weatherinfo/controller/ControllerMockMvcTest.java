package com.weatherinfo.controller;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.weatherinfo.model.WeatherInfo;
import com.weatherinfo.service.WeatherService;

@AutoConfigureMockMvc
@ContextConfiguration
@SpringBootTest
public class ControllerMockMvcTest {

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private WeatherService weatherService;

	@InjectMocks
	private WeatherController weatherController;

	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(weatherController).build();
	}

	@Test
	public void testGetWeatherInfo() throws Exception {
		Integer pincode = 411014;
		LocalDate date = LocalDate.parse("2024-03-13");

		WeatherInfo expectedWeather = new WeatherInfo();

		expectedWeather.setPincode(pincode);
		expectedWeather.setLocation("XYZ");
		;
		expectedWeather.setDate(date);
		;
		expectedWeather.setTemperature(20.0);
		;
		expectedWeather.setHumidity(50);
		expectedWeather.setPressure(1000);
		expectedWeather.setWindSpeed(3.46);
		expectedWeather.setDescription("clear");

		Mockito.when(weatherService.getWeatherInfo(pincode, date)).thenReturn(expectedWeather);

		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/weather/getInfo").param("pincode", pincode.toString())
						.param("date", date.toString()))
				.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.jsonPath(".pincode").value(pincode))
				.andExpect(MockMvcResultMatchers.jsonPath(".temperature").value(expectedWeather.getTemperature()))
				.andExpect(MockMvcResultMatchers.jsonPath(".humidity").value(expectedWeather.getHumidity()))
				.andExpect(MockMvcResultMatchers.jsonPath(".pressure").value(expectedWeather.getPressure()))
				.andExpect(MockMvcResultMatchers.jsonPath(".windSpeed").value(expectedWeather.getWindSpeed()))
				.andExpect(MockMvcResultMatchers.jsonPath(".description").value(expectedWeather.getDescription()))
				.andExpect(MockMvcResultMatchers.jsonPath(".location").value(expectedWeather.getLocation()));
	}
}