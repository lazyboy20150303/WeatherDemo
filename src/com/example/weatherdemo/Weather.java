package com.example.weatherdemo;

import android.graphics.Bitmap;


public class Weather {
	
	private  int weaid;
	private  String days;
	private  String week;
	private  String cityno;
	private  String citynm;
	private  String cityid;
	private  String temperature;
	private  String humidity;
	private  String weather;
	private  String weather_icon;
	private  String weather_icon1;
	private  String wind;
	private  String winp;
	private  String temp_high;
	private  String temp_low;
	private  String humi_high;
	private  String humi_low;
	private  int weatid;
	private  int weatid1;
	private  int windid;
	private  int winpid;
	private Bitmap weather_day;
	private Bitmap weather_night;
	
	
	public static String city;
	public static String current_date_time;
	public static String current_dayPictureUrl;
	public static String current_nightPictureUrl;
	public static String current_weather;
	public static String current_wind;
	public static String current_temp;

	public static Bitmap current_dayPicture;
	public static Bitmap current_nightPicture;
	
	public static Forecast[] day = new Forecast[3];//未来三天天气
	
	static{
		for(int i = 0;i<day.length;i++){
			day[i] = new Forecast();
		}
	}

	public int getWeaid() {
		return weaid;
	}

	public void setWeaid(int weaid) {
		this.weaid = weaid;
	}

	public String getCityno() {
		return cityno;
	}

	public void setCityno(String cityno) {
		this.cityno = cityno;
	}

	public String getCitynm() {
		return citynm;
	}

	public void setCitynm(String citynm) {
		this.citynm = citynm;
	}

	public String getCityid() {
		return cityid;
	}

	public void setCityid(String cityid) {
		this.cityid = cityid;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public String getWeather_icon() {
		return weather_icon;
	}

	public void setWeather_icon(String weather_icon) {
		this.weather_icon = weather_icon;
	}

	public String getWeather_icon1() {
		return weather_icon1;
	}

	public void setWeather_icon1(String weather_icon1) {
		this.weather_icon1 = weather_icon1;
	}

	public String getWind() {
		return wind;
	}

	public void setWind(String wind) {
		this.wind = wind;
	}

	public String getWinp() {
		return winp;
	}

	public void setWinp(String winp) {
		this.winp = winp;
	}

	public String getTemp_high() {
		return temp_high;
	}

	public void setTemp_high(String temp_high) {
		this.temp_high = temp_high;
	}

	public String getTemp_low() {
		return temp_low;
	}

	public void setTemp_low(String temp_low) {
		this.temp_low = temp_low;
	}

	public String getHumi_high() {
		return humi_high;
	}

	public void setHumi_high(String humi_high) {
		this.humi_high = humi_high;
	}

	public String getHumi_low() {
		return humi_low;
	}

	public void setHumi_low(String humi_low) {
		this.humi_low = humi_low;
	}

	public int getWeatid() {
		return weatid;
	}

	public void setWeatid(int weatid) {
		this.weatid = weatid;
	}

	public int getWeatid1() {
		return weatid1;
	}

	public void setWeatid1(int weatid1) {
		this.weatid1 = weatid1;
	}

	public int getWindid() {
		return windid;
	}

	public void setWindid(int windid) {
		this.windid = windid;
	}

	public int getWinpid() {
		return winpid;
	}

	public void setWinpid(int winpid) {
		this.winpid = winpid;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public Bitmap getWeather_day() {
		return weather_day;
	}

	public void setWeather_day(Bitmap weather_day) {
		this.weather_day = weather_day;
	}

	public Bitmap getWeather_night() {
		return weather_night;
	}

	public void setWeather_night(Bitmap weather_night) {
		this.weather_night = weather_night;
	}
	
}
