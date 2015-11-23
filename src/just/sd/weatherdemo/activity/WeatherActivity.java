package just.sd.weatherdemo.activity;

import java.io.IOException;
import java.util.List;

import just.sd.weatherdemo.adaptor.PictureAdaptor;
import just.sd.weatherdemo.adaptor.WeatherAdaptor;
import just.sd.weatherdemo.model.Config;
import just.sd.weatherdemo.model.Weather;

import com.example.weatherdemo.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * app主页面，显示天气信息
 * 使用的k780接口的数据
 * @author sunqing
 *日期：2015.11.21
 * @param <MainActivity>
 */
public class WeatherActivity<MainActivity> extends Activity {

	List<Weather> weathers=null;
	TextView[] week=new TextView[5];
	TextView[] temperature=new TextView[5];
	ImageView[] img=new ImageView[5];

	TextView current_weather;
	TextView current_wind;
	TextView city;
	TextView current_winp;
	TextView current_date;

	public Handler handler = new Handler(){

		@SuppressWarnings("deprecation")
		@Override
		public void handleMessage(Message msg) {
			if(weathers!=null){
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
				Drawable drawable = null;
				try {
					drawable = new BitmapDrawable(new PictureAdaptor(getApplicationContext()).getbgBitMap(weathers.get(0).getWeather_icon()));
				} catch (IOException e) {
					e.printStackTrace();
				}
				findViewById(R.id.layout01).setBackgroundDrawable(drawable);
			}else {
				Toast.makeText(WeatherActivity.this, "获取天气失败", Toast.LENGTH_SHORT).show();
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
				Intent config=new Intent(WeatherActivity.this,ConfigActivity.class);
				WeatherActivity.this.startActivityForResult(config, 1);
			}
		});
		Config.LoadDefaultConfig();
		new Thread(run).start();
	}

	Runnable run = new Runnable() {

		@Override
		public void run() {
			Message msg=new Message();
			try {
				weathers = WeatherAdaptor.getWeatherFromK780(Config.CityName);//获取K780天气
				for (Weather weather : weathers) {//图片转换
					weather.setWeather_day(new PictureAdaptor(getApplicationContext()).getBitMap(weather.getWeather_icon()));
					weather.setWeather_night(new PictureAdaptor(getApplicationContext()).getBitMap(weather.getWeather_icon1()));
				}
			} catch (Exception e) {
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
