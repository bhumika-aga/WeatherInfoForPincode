package com.weatherinfo.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.weatherinfo.dto.Coord;
import com.weatherinfo.dto.GeoCodingAPIResponse;
import com.weatherinfo.dto.Weather;
import com.weatherinfo.dto.WeatherAPIResponse;
import com.weatherinfo.model.Pincode;
import com.weatherinfo.model.WeatherInfo;
import com.weatherinfo.repository.PincodeRepository;
import com.weatherinfo.repository.WeatherRepository;

@SpringBootTest
public class WeatherServiceTest {

	@Mock
	private RestTemplate template;

	@Mock
	private PincodeRepository pincodeRepository;

	@Mock
	private WeatherRepository weatherRepository;

	@InjectMocks
	private WeatherService weatherService;

	@Test
	void testGetPincode() throws Exception {
		GeoCodingAPIResponse mockResponse = new GeoCodingAPIResponse("411014", "Pune", 18.5685, 73.9158, "IN");
		ResponseEntity<GeoCodingAPIResponse> mockResponseEntity = new ResponseEntity<>(mockResponse, HttpStatus.OK);
		Mockito.when(template.getForEntity(anyString(), eq(GeoCodingAPIResponse.class))).thenReturn(mockResponseEntity);

		Pincode actualPincode = weatherService.getPincode(411014);

		Assertions.assertEquals(mockResponse.getZip(), Integer.toString(actualPincode.getPincode()));
		Assertions.assertEquals(mockResponse.getLat(), (actualPincode.getLatitude()));
		Assertions.assertEquals(mockResponse.getLon(), (actualPincode.getLongitude()));
	}

	@Test
	void testGetResponse() throws Exception {
		WeatherAPIResponse mockResponse = new WeatherAPIResponse();
		mockResponse.setCoord(new Coord(73.9158, 18.5685));

		List<Weather> temp = new ArrayList<>();
		temp.add(new Weather(800, "Clear", "clear sky", "01d"));
		mockResponse.setWeather(temp);

		Mockito.when(template.getForEntity(anyString(), eq(WeatherAPIResponse.class)))
				.thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

		WeatherAPIResponse actualResponse = weatherService.getResponse(18.5658, 73.9158, LocalDate.parse("2020-10-14"));

		Assertions.assertEquals(mockResponse.getCoord().getLat(), actualResponse.getCoord().getLat());
		Assertions.assertEquals(mockResponse.getCoord().getLon(), actualResponse.getCoord().getLon());
		Assertions.assertEquals(mockResponse.getWeather().get(0).getMain(),
				actualResponse.getWeather().get(0).getMain());
	}

	@Test
	void testGetWeatherInfo() throws Exception {
		Pincode mockPincode = new Pincode(411014, 18.5685, 73.9158);

		WeatherInfo mockWeatherInfo = new WeatherInfo();
		mockWeatherInfo.setPincode(411014);
		mockWeatherInfo.setDate(LocalDate.parse("2020-10-15"));
		mockWeatherInfo.setLocation("Viman Nagar");
		mockWeatherInfo.setDescription("clear sky");

		WeatherAPIResponse mockResponse = new WeatherAPIResponse();
		mockResponse.setCoord(new Coord(73.9158, 18.5685));
		List<Weather> temp = new ArrayList<>();
		temp.add(new Weather(800, "Clear", "clear sky", "01d"));
		mockResponse.setWeather(temp);

		when(pincodeRepository.findById(411014)).thenReturn(Optional.of(mockPincode));
		when(weatherRepository.findByPincodeAndDate(411014, LocalDate.parse("2020-10-15")))
				.thenReturn(Optional.of(mockWeatherInfo));
		when(weatherRepository.save(mockWeatherInfo)).thenReturn(mockWeatherInfo);
		when(template.getForEntity(anyString(), eq(WeatherAPIResponse.class)))
				.thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

		WeatherInfo weather = weatherService.getWeatherInfo(411014, LocalDate.parse("2020-10-15"));
		assertNotNull(weather);

		Assertions.assertEquals(mockWeatherInfo.getPincode(), weather.getPincode());
		Assertions.assertEquals(mockWeatherInfo.getDate(), weather.getDate());
		Assertions.assertEquals(mockWeatherInfo.getLocation(), weather.getLocation());

		Mockito.verify(weatherRepository).findByPincodeAndDate(411014, LocalDate.parse("2020-10-15"));
		Mockito.verifyNoMoreInteractions(weatherRepository);
	}
}