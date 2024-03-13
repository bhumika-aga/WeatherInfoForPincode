package com.weatherinfo.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "weather")
public class WeatherInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer pincode;
	private String location;
	private LocalDate date;
	private double temperature;
	private int humidity;
	private int pressure;
	private double windSpeed;
	private String description;

	public Integer getPincode() {
		return pincode;
	}

	public void setPincode(Integer pincode) {
		this.pincode = pincode;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public int getHumidity() {
		return humidity;
	}

	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}

	public int getPressure() {
		return pressure;
	}

	public void setPressure(int pressure) {
		this.pressure = pressure;
	}

	public double getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(double windSpeed) {
		this.windSpeed = windSpeed;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public WeatherInfo() {
		super();
	}

	public WeatherInfo(Long id, Integer pincode, String location, LocalDate date, double temperature, int humidity,
			int pressure, double windSpeed, String description) {
		super();
		this.id = id;
		this.pincode = pincode;
		this.location = location;
		this.date = date;
		this.temperature = temperature;
		this.humidity = humidity;
		this.pressure = pressure;
		this.windSpeed = windSpeed;
		this.description = description;
	}

	@Override
	public String toString() {
		return "Weather [id=" + id + ", pincode=" + pincode + ", location=" + location + ", date=" + date
				+ ", temperature=" + temperature + ", humidity=" + humidity + ", pressure=" + pressure + ", windSpeed="
				+ windSpeed + ", description=" + description + "]";
	}
}