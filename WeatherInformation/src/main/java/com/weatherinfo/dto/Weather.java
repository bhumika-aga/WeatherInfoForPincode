package com.weatherinfo.dto;

public class Weather {

	private int id;
	private String main;
	private String descritpion;
	private String icon;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMain() {
		return main;
	}

	public void setMain(String main) {
		this.main = main;
	}

	public String getDescritpion() {
		return descritpion;
	}

	public void setDescritpion(String descritpion) {
		this.descritpion = descritpion;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Weather() {
	}

	public Weather(int id, String main, String descritpion, String icon) {
		this.id = id;
		this.main = main;
		this.descritpion = descritpion;
		this.icon = icon;
	}
}