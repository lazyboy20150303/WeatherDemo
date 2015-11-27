package just.sd.weatherdemo.config;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import just.sd.weatherdemo.db.DB;
import just.sd.weatherdemo.net.DataRequest;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

/**
 * 城市信息适配器
 * from:api.k780.com
 * @author sunqing
 *日期：2015.11.21
 */
public class CityConfig {

	/**
	 * 获取城市信息
	 * @param context 
	 * @param cityExist 
	 * @return 是否获取成功
	 */
	public static boolean GetCity(Context context){
		String requestUrl = "http://api.k780.com:88/?app=weather.city&format=json";
		String jsonString=DataRequest.request("POST", requestUrl);
		DB db=new DB(context);
		if (jsonString==null) {
			return false;
		}
		try {
			List<Map<String, Object>> cityList=parseJson(jsonString);
			db.open();
			for (Map<String, Object> map : cityList) {
				int weaid=(Integer) map.get("weaid");
				String citynm=(String) map.get("citynm");
				String cityno=(String) map.get("cityno");
				String cityid=(String) map.get("cityid");
				db.insert(weaid, citynm, cityno, cityid);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}finally{
			db.close();
		}
		return true;
	}

	/**
	 * 将城市表json数据转成list
	 * @param data json数据
	 * @return 数据列表
	 * @throws JSONException
	 */
	private static List<Map<String, Object>> parseJson(String data) throws JSONException{
		List<Map<String, Object>> cities=new ArrayList<Map<String,Object>>();
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
			cities.add(map);
		}
		return cities;
	}

}
