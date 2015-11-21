package com.example.weatherdemo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import android.graphics.BitmapFactory;

public class WeatherPicture {
	
	/**
	 * 百度天气接口url成转图片;使用静态方法直接转换静态成员变量
	 */
	public static void doConverter() {
		try {
			byte data[]=getUrlData(Weather.current_dayPictureUrl);
			Weather.current_dayPicture=BitmapFactory.decodeByteArray(data, 0, data.length);
			data=getUrlData(Weather.current_nightPictureUrl);
			Weather.current_nightPicture=BitmapFactory.decodeByteArray(data, 0, data.length);
			for (int i = 0; i < Weather.day.length; i++) {
				data=getUrlData(Weather.day[i].dayPictureUrl);
				Weather.day[i].dayPicture=BitmapFactory.decodeByteArray(data, 0, data.length);
				data=getUrlData(Weather.day[i].nightPictureUrl);
				Weather.day[i].nightPicture=BitmapFactory.decodeByteArray(data, 0, data.length);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * K780接口url转成图片
	 * @throws IOException 
	 */
	public static void setPictures(Weather weather) throws IOException{
		String dayicon=weather.getWeather_icon();
		byte data[]=getUrlData(dayicon);
		weather.setWeather_day(BitmapFactory.decodeByteArray(data, 0, data.length));
		
		String nighticon=weather.getWeather_icon1();
		byte data1[]=getUrlData(nighticon);
		weather.setWeather_night(BitmapFactory.decodeByteArray(data1, 0, data1.length));
	}
	
	/**
	 * 获取图片数据
	 * @param urlString 图片url
	 * @return 图片数据数组
	 * @throws IOException
	 */
	private static byte[] getUrlData(String urlString) throws IOException {
		ByteArrayOutputStream bos=null;
		try {
			URL url=new URL(urlString);
			bos=new ByteArrayOutputStream();
			byte[] data=new byte[1024];
			HttpURLConnection conn=(HttpURLConnection)url.openConnection();
			InputStream input=conn.getInputStream();
			int len=0;
			while ((len=input.read(data))!=-1) {
				bos.write(data,0,len);
			}
			return bos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(bos!=null){
				bos.close();
			}
		}
		return null;
	};
}
