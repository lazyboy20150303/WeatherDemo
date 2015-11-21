package com.example.weatherdemo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import android.graphics.BitmapFactory;

public class WeatherPicture {
	
	/**
	 * �ٶ������ӿ�url��תͼƬ;ʹ�þ�̬����ֱ��ת����̬��Ա����
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
	 * K780�ӿ�urlת��ͼƬ
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
	 * ��ȡͼƬ����
	 * @param urlString ͼƬurl
	 * @return ͼƬ��������
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
