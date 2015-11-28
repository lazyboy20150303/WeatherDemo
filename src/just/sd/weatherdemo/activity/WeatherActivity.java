package just.sd.weatherdemo.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import just.sd.weatherdemo.adaptor.PictureAdaptor;
import just.sd.weatherdemo.adaptor.WeatherAdaptor;
import just.sd.weatherdemo.config.Config;
import just.sd.weatherdemo.model.Weather;
import just.sd.weatherdemo.view.MainView;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherdemo.R;

/**
 * app��ҳ�棬��ʾ������Ϣ
 * ʹ�õ�k780�ӿڵ�����
 * @author sunqing
 *���ڣ�2015.11.21
 * @param <MainActivity>
 */
public class WeatherActivity<MainActivity> extends Activity {

	private MainView mView;//���Ӵ�

	//�������ڿؼ�
	List<Weather> weathers=null;
	TextView[] week=new TextView[5];
	TextView[] temperature=new TextView[5];
	ImageView[] img=new ImageView[5];

	TextView current_weather;
	TextView current_wind;
	TextView city;
	TextView current_winp;
	TextView current_date;

	ImageButton refresh;

	//�˵����ڿؼ�(���ó���)
	ListView sp_listview;
	private static String SP_FAVOURITECITY="favourite_city";

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
				Toast.makeText(WeatherActivity.this, "��ȡ����ʧ��", Toast.LENGTH_SHORT).show();
			}
		}

	};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//�ֱ�����������Ӵ��Ͳ˵��Ӵ�(���ó���)
		mView=new MainView(this, 
				LayoutInflater.from(this).inflate(R.layout.main_content, null), 
				LayoutInflater.from(this).inflate(R.layout.main_menu, null));
		this.setContentView(mView);

		//��ʼ���������ڿؼ�
		city=(TextView)findViewById(R.id.cityName);//��ǰ����
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

		//���ó���
		sp_listview=(ListView)findViewById(R.id.common);
		initCommon();
		//����л�����
		sp_listview.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				@SuppressWarnings("unchecked")
				Map<String, String> city=(HashMap<String, String>)WeatherActivity.this.sp_listview.getItemAtPosition(position);
				String key=city.get("sp_name");
				String value=city.get("sp_weaid");
				Config.CityName=key;
				Config.Weatid=Integer.parseInt(value);
				new Thread(run).start();
			}
		});

		//���е���¼������ĳ���
		city.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent config=new Intent(WeatherActivity.this,ConfigActivity.class);
				WeatherActivity.this.startActivityForResult(config, 1);
			}
		});
		
		refresh=(ImageButton)findViewById(R.id.refresh);
		//ˢ��
		refresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new Thread(run).start();
				Toast.makeText(getApplicationContext(), "���³ɹ�", Toast.LENGTH_SHORT).show();
			}
		});

		Config.LoadDefaultConfig();
		new Thread(run).start();
	}

	//TODO��ʼ�����ó��б�ʹ��sharepreference����
	private void initCommon() {
		List<Map<String, String>> sp_list=new ArrayList<>();
		SharedPreferences share=getSharedPreferences(SP_FAVOURITECITY,Activity.MODE_PRIVATE);//����˽��
		Map<String, ?> map=share.getAll();
		Iterator<String> iterator= map.keySet().iterator();
		while (iterator.hasNext()) {
			String key=iterator.next().toString();//��ȡsharePreferences��key
			String value=map.get(key).toString();//��ȡsharePreferences��value
			Map<String, String> c=new HashMap<>();
			c.put("sp_name", key);
			c.put("sp_weaid", value);
			sp_list.add(c);
		}
		SimpleAdapter simpleAdapter=new SimpleAdapter(getApplicationContext(),
				sp_list, R.layout.cityitem, 
				new String[]{"sp_name","sp_weaid"}, 
				new int[]{R.id.db_cityname,R.id.db_weatid});
		sp_listview.setAdapter(simpleAdapter);
	}

	Runnable run = new Runnable() {

		@Override
		public void run() {
			Message msg=new Message();
			try {
				weathers=WeatherAdaptor.getWeatherFromK780(Config.Weatid);//��ȡK780����
				for (Weather weather : weathers) {//ͼƬת��
					weather.setWeather_day(new PictureAdaptor(getApplicationContext()).getBitMap(weather.getWeather_icon()));
					weather.setWeather_night(new PictureAdaptor(getApplicationContext()).getBitMap(weather.getWeather_icon1()));
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
			handler.sendMessage(msg);
		}
	};

	//����ش�����
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case RESULT_OK:
			initCommon();//ˢ��sp_listview�б�
			new Thread(run).start();
		default:
			break;
		}
	};
}
