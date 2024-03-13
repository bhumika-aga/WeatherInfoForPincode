package com.weatherinfo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pincode")
public class Pincode {

	@Id
	@Column(name = "pincode", nullable = false)
	private int pincode;
	private double latitude;
	private double longitude;

	public int getPincode() {
		return pincode;
	}

	public void setPincode(int pincode) {
		this.pincode = pincode;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public Pincode() {
	}

	public Pincode(int pincode, double latitude, double longitude) {
		this.pincode = pincode;
		this.latitude = latitude;
		this.longitude = longitude;
	}
}