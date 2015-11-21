package com.example.weatherdemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class ConfigActivity extends Activity {

	String cityname;
	
	EditText cityNameView;
	ListView citys;
	Button savebutton;
	Button updateButton;
	
	List<Map<String, String>> list;
	SimpleAdapter simpleAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.config);
		

		cityNameView=(EditText)findViewById(R.id.city);//配置城市
		
		citys=(ListView)findViewById(R.id.cityList);
		
		list=new ArrayList<Map<String,String>>();
		
		//TODO 从数据库获取
		for (int i = 0; i < 30; i++) {
			Map<String, String> map=new HashMap<String, String>();
			map.put("db_city", ""+i);
			list.add(map);
		}
		
		simpleAdapter=new SimpleAdapter(getApplicationContext(),
				this.list, R.layout.cityitem, 
				new String[]{"db_city"}, 
				new int[]{R.id.db_city});
		
		this.citys.setAdapter(simpleAdapter);
		this.citys.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				@SuppressWarnings("unchecked")
				Map<String, String> map=(HashMap<String, String>)ConfigActivity.this.citys.getItemAtPosition(position);
				ConfigActivity.this.cityname=map.get("db_city");
				cityNameView.setText(cityname);
			}
		});
		
		//保存配置信息
		savebutton=(Button)findViewById(R.id.savebutton);
		
		savebutton.setOnClickListener(new OnClickListener(){
			
			public void onClick(View view){
				
				String cityname=cityNameView.getText().toString().trim();
				
				Intent intent=ConfigActivity.this.getIntent();
				intent.putExtra("cityname", cityname);
				ConfigActivity.this.setResult(RESULT_OK, intent);
				ConfigActivity.this.finish();
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
			boolean flat=City.GetCity();
			if (!flat) {
				Toast.makeText(getApplicationContext(), "获取数据失败", Toast.LENGTH_SHORT).show();
			}
		}
	};
}
