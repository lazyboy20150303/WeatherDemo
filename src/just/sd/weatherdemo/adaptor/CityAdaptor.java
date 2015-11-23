package just.sd.weatherdemo.adaptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import just.sd.weatherdemo.db.DBAdapter;
import just.sd.weatherdemo.net.DataRequest;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

/**
 * ������Ϣ������
 * from:api.k780.com
 * @author sunqing
 *���ڣ�2015.11.21
 */
public class CityAdaptor {

	/**
	 * ��ȡ������Ϣ
	 * @param context 
	 * @return �Ƿ��ȡ�ɹ�
	 */
	public static boolean GetCity(Context context){
		String requestUrl = "http://api.k780.com:88/?app=weather.city&format=json";
		String jsonString=DataRequest.request("POST", requestUrl);
		if (jsonString==null) {
			return false;
		}
		try {
			List<Map<String, Object>> cityList=parseJson(jsonString);
			DBAdapter adapter=new DBAdapter(context);
			adapter.open();
			for (Map<String, Object> map : cityList) {
				int weaid=(Integer) map.get("weaid");
				String citynm=(String) map.get("citynm");
				String cityno=(String) map.get("cityno");
				String cityid=(String) map.get("cityid");
				adapter.insert(weaid, citynm, cityno, cityid);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * �����б�json����ת��list
	 * @param data json����
	 * @return �����б�
	 * @throws JSONException
	 */
	private static List<Map<String, Object>> parseJson(String data) throws JSONException{
		List<Map<String, Object>> citys=new ArrayList<Map<String,Object>>();
		JSONObject result=new JSONObject(data);
		JSONObject o=result.getJSONObject("result");
		@SuppressWarnings("unchecked")
		Iterator<Integer> iterator=o.keys();
		while (iterator.hasNext()) {
			JSONObject item=o.getJSONObject(""+iterator.next());
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("weaid", item.getInt("weaid"));
			map.put("citynm", item.getString("citynm"));
			map.put("cityno", item.getString("cityno"));
			map.put("cityid", item.getString("cityid"));
			citys.add(map);
		}
		return citys;
	}

}
