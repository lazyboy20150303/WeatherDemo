package com.example.weatherdemo;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class WeatherDemo<MainActivity> extends Activity {
	
	TextView[] week=new TextView[5];
	TextView[] temperature=new TextView[5];
	ImageView[] img=new ImageView[5];
	
	TextView current_weather;
	TextView current_wind;
	TextView city;
	TextView current_winp;
	TextView current_date;
	
	public Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
	        
			if(msg.obj!=null){
				@SuppressWarnings("unchecked")
				List<Weather> weathers=(List<Weather>) msg.obj;
				for (int i = 0; i < 5; i++) {
					temperature[i].setText(weathers.get(i).getTemperature());
					img[i].setImageBitmap(weathers.get(i).getWeather_day());
					week[i].setText(weathers.get(i).getWeek());
				}
				current_weather.setText(weathers.get(0).getWeather());
				current_wind.setText(weathers.get(0).getWind());
				current_winp.setText(weathers.get(0).getWinp());
				current_date.setText(weathers.get(0).getDays());
				city.setText(weathers.get(0).getCitynm());
			}else {
				Toast.makeText(WeatherDemo.this, "获取天气失败", Toast.LENGTH_SHORT).show();
			}
		}
		
	};
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.main);
		city=(TextView)findViewById(R.id.cityName);//当前城市
		week[0]=(TextView)findViewById(R.id.current_week);
		temperature[0]=(TextView)findViewById(R.id.current_temp);
		img[0]=(ImageView)findViewById(R.id.img_current);
		current_weather=(TextView)findViewById(R.id.currentWeather);
		current_wind=(TextView)findViewById(R.id.current_wind);
		current_winp=(TextView)findViewById(R.id.current_winp);
		current_date=(TextView)findViewById(R.id.current_date_time);

		temperature[1]=(TextView)findViewById(R.id.txt_secondday);
		week[1]=(TextView)findViewById(R.id.secondday);
		img[1]=(ImageView)findViewById(R.id.img_secondday);
		
		temperature[2]=(TextView)findViewById(R.id.txt_thirdday);
		week[2]=(TextView)findViewById(R.id.thirdday);
		img[2]=(ImageView)findViewById(R.id.img_thirdday);
		
		temperature[3]=(TextView)findViewById(R.id.txt_forthday);
		week[3]=(TextView)findViewById(R.id.forthday);
		img[3]=(ImageView)findViewById(R.id.img_forthday);
		
		temperature[4]=(TextView)findViewById(R.id.txt_fifthday);
		week[4]=(TextView)findViewById(R.id.fifthday);
		img[4]=(ImageView)findViewById(R.id.img_fifthday);
		
		city.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent config=new Intent(WeatherDemo.this,ConfigActivity.class);
				WeatherDemo.this.startActivityForResult(config, 1);
			}
		});
	}
	
	Runnable run = new Runnable() {
		
		@Override
		public void run() {
			Message msg=new Message();
			//msg.obj=WeatherAdaptor.getWeather(Config.CityName);//获取百度天气
			try {
				msg.obj=WeatherAdaptor.getWeatherFromK780(Config.CityName);//获取K780天气
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        handler.sendMessage(msg);
		}
	};

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case RESULT_OK:
			Config.CityName=data.getStringExtra("cityname");//城市名称给配置表
			
			new Thread(run).start();

		default:
			break;
		}
	};
}
