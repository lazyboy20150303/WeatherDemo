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
 * ����ҳ��ѡ�����
 * ���³�������
 * @author sunqing
 *���ڣ�2015.11.21
 */
public class ConfigActivity extends Activity {

	EditText cityNameView;//����input
	ListView citiesview;//�����б�
	Button savebutton;//��������
	Button updateButton;//���³�����Ϣ��

	List<Map<String, String>> list;//������Ϣ������
	CityAdapter cityAdapter;//list==>citiesview
	DB db;
	
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
		
		//����������Ϣ
		savebutton=(Button)findViewById(R.id.savebutton);

		savebutton.setOnClickListener(new OnClickListener(){

			public void onClick(View view){

				Intent intent=ConfigActivity.this.getIntent();
				Config.CityName=cityNameView.getText().toString().trim();
				db.open();
				int cityexist=Config.Weatid=db.findid(Config.CityName);//��ѯ���У���������ڷ���0
				db.close();
				intent.putExtra("weaid", cityexist);
				ConfigActivity.this.setResult(RESULT_OK, intent);
				ConfigActivity.this.finish();
			}
		});

		//���³������ݿ�
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
				Toast.makeText(getApplicationContext(), "��ȡ����ʧ��", Toast.LENGTH_SHORT).show();
			}else {
				Toast.makeText(getApplicationContext(), "���ݿ���³ɹ�", Toast.LENGTH_SHORT).show();
			}
			Looper.loop();
		}
	};
}
