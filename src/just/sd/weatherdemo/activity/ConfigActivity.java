package just.sd.weatherdemo.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import just.sd.weatherdemo.adaptor.CityAdapter;
import just.sd.weatherdemo.config.CityConfig;
import just.sd.weatherdemo.config.Config;
import just.sd.weatherdemo.db.DB;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.weatherdemo.R;

/**
 * 配置页，选择城市
 * 更新城市数据
 * @author sunqing
 *日期：2015.11.21
 */
public class ConfigActivity extends Activity {

	EditText cityNameView;//城市input
	ListView citiesview;//城市列表
	Button savePreference;//设为常用
	Button savebutton;//保存配置
	Button updateButton;//更新城市信息表

	List<Map<String, String>> list;//城市信息表数据
	CityAdapter cityAdapter;//list==>citiesview
	DB db;

	private static String SP_FAVOURITECITY="favourite_city";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.config);

		cityNameView=(EditText)findViewById(R.id.city);

		citiesview=(ListView)findViewById(R.id.cityList);

		db=new DB(getApplicationContext());

		this.citiesview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				@SuppressWarnings("unchecked")
				Map<String, String> map=(HashMap<String, String>)ConfigActivity.this.citiesview.getItemAtPosition(position);
				ConfigActivity.this.cityNameView.setText(map.get("db_cityname"));
			}
		});

		cityAdapter=new CityAdapter(getApplicationContext(),db,citiesview);
		cityAdapter.showAllData();

		//该城市名保存到sharePreference中
		savePreference=(Button)findViewById(R.id.savePreference);

		savePreference.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SharedPreferences sp=getSharedPreferences(SP_FAVOURITECITY, Activity.MODE_PRIVATE);
				SharedPreferences.Editor editor=sp.edit();
				String cityname=cityNameView.getText().toString().trim();
				if(cityname.isEmpty()){
					Toast.makeText(getApplicationContext(), "请输入城市", Toast.LENGTH_SHORT).show();
				}else {
					db.open();
					int weatid=db.findid(cityname);
					db.close();
					if(weatid!=0){
						editor.putInt(cityname, weatid);//将cityname和weaid分别作为key,value写入sharepreferences
						editor.commit();
						Toast.makeText(getApplicationContext(), "设置偏好成功", Toast.LENGTH_SHORT).show();
					}else {
						Toast.makeText(getApplicationContext(), "该城市不存在", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});

		//保存配置信息
		savebutton=(Button)findViewById(R.id.savebutton);

		savebutton.setOnClickListener(new OnClickListener(){

			public void onClick(View view){

				Intent intent=ConfigActivity.this.getIntent();
				String cityname=Config.CityName=cityNameView.getText().toString().trim();
				if(cityname.isEmpty()){
					Toast.makeText(getApplicationContext(), "请输入城市", Toast.LENGTH_SHORT).show();
				}else {
					db.open();
					int cityexist=Config.Weatid=db.findid(Config.CityName);//查询城市，如果不存在返回0
					db.close();
					if (cityexist==0) {
						Toast.makeText(getApplicationContext(), "该城市不存在", Toast.LENGTH_SHORT).show();
					}else {
						intent.putExtra("weaid", cityexist);
						ConfigActivity.this.setResult(RESULT_OK, intent);
						ConfigActivity.this.finish();
					}
				}
			}
		});

		//更新城市数据库
		updateButton=(Button)findViewById(R.id.update_db);

		updateButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new Thread(runnable).start();
			}
		});
	}

	Runnable runnable=new Runnable() {
		@Override
		public void run() {
			boolean flat=CityConfig.GetCity(getApplicationContext());
			Looper.prepare();
			if (!flat) {
				Toast.makeText(getApplicationContext(), "获取数据失败", Toast.LENGTH_SHORT).show();
			}else {
				Toast.makeText(getApplicationContext(), "数据库更新成功", Toast.LENGTH_SHORT).show();
			}
			Looper.loop();
		}
	};
}
