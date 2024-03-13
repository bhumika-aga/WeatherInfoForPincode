package com.weatherinfo.service;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.weatherinfo.dto.GeoCodingAPIResponse;
import com.weatherinfo.dto.WeatherAPIResponse;
import com.weatherinfo.model.Pincode;
import com.weatherinfo.model.WeatherInfo;
import com.weatherinfo.repository.PincodeRepository;
import com.weatherinfo.repository.WeatherRepository;

@Service
public class WeatherService {

	@Autowired
	private WeatherRepository weatherRepository;

	@Autowired
	private PincodeRepository pincodeRepository;

	private String OPEN_WEATHER_API_KEY = "14406513cb1fcee2f36aacb7a56e0d36";

	public WeatherAPIResponse getResponse(double latitude, double longitude, LocalDate date) throws Exception {
		long unixTimestamp = date.atStartOfDay(ZoneOffset.UTC).toEpochSecond();
		String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid="
				+ OPEN_WEATHER_API_KEY + "&dt=" + unixTimestamp;

		RestTemplate template = new RestTemplate();
		ResponseEntity<WeatherAPIResponse> response = template.getForEntity(url, WeatherAPIResponse.class);

		if (response.getStatusCode().isError()) {
			throw new Exception(response.getStatusCode().toString());
		}

		return response.getBody();
	}

	public Pincode getPincode(Integer pincode) throws Exception {
		String url = "http://api.openweathermap.org/geo/1.0/zip?zip=" + pincode + ",in&appid=" + OPEN_WEATHER_API_KEY;
		RestTemplate template = new RestTemplate();
		ResponseEntity<GeoCodingAPIResponse> response = template.getForEntity(url, GeoCodingAPIResponse.class);

		double latitude = 0;
		double longitude = 0;

		if (!response.getStatusCode().is2xxSuccessful()) {
			throw new Exception(response.getStatusCode().toString());
		}

		latitude = response.getBody().getLat();
		longitude = response.getBody().getLon();

		return new Pincode(pincode, latitude, longitude);
	}

	public WeatherInfo getWeatherInfo(Integer pincode, LocalDate date) throws Exception {
		Optional<WeatherInfo> info = this.weatherRepository.findByPincodeAndDate(pincode, date);
		if (info.isPresent()) {
			return info.get();
		}

		double latitude;
		double longitude;

		Optional<Pincode> location = null;
		try {
			location = this.pincodeRepository.findById(pincode);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}

		if (location.isPresent()) {
			latitude = location.get().getLatitude();
			longitude = location.get().getLongitude();
		} else {
			Pincode thisPincode = getPincode(pincode);
			latitude = thisPincode.getLatitude();
			longitude = thisPincode.getLongitude();
			this.pincodeRepository.save(thisPincode);
		}

		WeatherAPIResponse response = getResponse(latitude, longitude, date);
		WeatherInfo weather = new WeatherInfo();

		if (response != null) {
			weather.setPincode(pincode);
			weather.setDate(date);
			weather.setTemperature(response.getMain().getTemp());
			weather.setHumidity(response.getMain().getHumidity());
			weather.setPressure(response.getMain().getPressure());
			weather.setWindSpeed(response.getWind().getSpeed());
			weather.setDescription(response.getWeather().get(0).getDescritpion());
			weather.setLocation(response.getName());

			this.weatherRepository.save(weather);
		}

		return weather;
	}
}