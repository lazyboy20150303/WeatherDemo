package just.sd.weatherdemo.adaptor;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import just.sd.weatherdemo.model.Weather;
import just.sd.weatherdemo.net.DataRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

/**
 * 天气数据适配器
 * from api.map.baidu.com/api.k780.com
 * @author sunqing
 *	日期：2015.11.21
 */
public class WeatherAdaptor {

	
	/**
	 * 从百度接口获取数据实例化天气对象(使用天气的静态成员)
	 * 使用pull解析xml
	 * @param city	城市名称
	 * @throws IOException
	 * @throws Throwable
	 */
	public static void getWeatherDataFromBaidu(String city) throws IOException,Throwable{
		String requestUrl = "http://api.map.baidu.com/telematics/v3/weather?location="
				+city+"&output=xml&ak=A72e372de05e63c8740b2622d0ed8ab1";
		String result = DataRequest.request("POST",requestUrl);
		InputStream streamTemp = new ByteArrayInputStream(result.getBytes());
		//解析XML
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(true);
		XmlPullParser parser = factory.newPullParser();
		parser.setInput(streamTemp,"UTF-8");
		while(parser.next()!=XmlPullParser.END_DOCUMENT){
			String element = parser.getName();
			if(element != null && element.equals("weather_data")){
				for(int dayCount = 0;dayCount<4;++dayCount){
					if(dayCount == 0){
						Weather.city = city;
					}
					while(parser.next()!=XmlPullParser.END_DOCUMENT){
						element = parser.getName();
						if((element != null)){
							if (element.equals("date")) {
								if(dayCount == 0)
									Weather.current_date_time = parser.nextText();
								else
									Weather.day[dayCount-1].day_of_week = parser.nextText();
							} else if (element.equals("dayPictureUrl")) {
								if(dayCount == 0)
									Weather.current_dayPictureUrl = parser.nextText();
								else
									Weather.day[dayCount-1].dayPictureUrl = parser.nextText();
							} else if (element.equals("nightPictureUrl")) {
								if(dayCount == 0)
									Weather.current_nightPictureUrl = parser.nextText();
								else
									Weather.day[dayCount-1].nightPictureUrl = parser.nextText();
							} else if (element.equals("weather")) {
								if(dayCount == 0)
									Weather.current_weather = parser.nextText();
								else
									Weather.day[dayCount-1].weather = parser.nextText();
							} else if (element.equals("wind")) {
								if(dayCount == 0)
									Weather.current_wind = parser.nextText();
								else
									Weather.day[dayCount-1].wind = parser.nextText();
							} else if (element.equals("temperature")) {
								if(dayCount == 0)
									Weather.current_temp = parser.nextText();
								else
									Weather.day[dayCount-1].temperature = parser.nextText();
								break;
							}
						}
					}
				}
				break;
			}
		}
	}

	/**
	 * 从k780接口获取天气数据
	 * 解析json数据
	 * @param city 城市名称
	 * @return 未来7天天气列表
	 */
	public static List<Weather> getWeatherFromK780(String city) throws JSONException{

		String requestUrl = "http://api.k780.com:88/?app=weather.future&weaid="+
				city+"&&appkey=16506&sign=07e825a58434df5d65b32d92a84d14f4&format=json";
		String result = DataRequest.request("POST",requestUrl);
		JSONObject data;
		List<Weather> weathers=new ArrayList<Weather>();
		data = new JSONObject(result);
		JSONArray array=data.getJSONArray("result");
		for (int i = 0; i < array.length(); i++) {
			JSONObject o=array.getJSONObject(i);
			Weather weather=new Weather();
			weather.setWeaid(o.getInt("weaid"));
			weather.setDays(o.getString("days"));
			weather.setWeek(o.getString("week"));
			weather.setCityno(o.getString("cityno"));
			weather.setCitynm(o.getString("citynm"));
			weather.setCityid(o.getString("cityid"));
			weather.setTemperature(o.getString("temperature"));
			weather.setHumidity(o.getString("humidity"));
			weather.setWeather(o.getString("weather"));
			weather.setWeather_icon(o.getString("weather_icon"));
			weather.setWeather_icon1(o.getString("weather_icon1"));
			weather.setWind(o.getString("wind"));
			weather.setWinp(o.getString("winp"));
			weather.setTemp_high(o.getString("temp_high"));
			weather.setTemp_low(o.getString("temp_low"));
			weather.setHumi_high(o.getString("humi_high"));
			weather.setHumi_low(o.getString("humi_low"));
			weather.setWeaid(o.getInt("weatid"));
			weather.setWeatid1(o.getInt("weatid1"));
			weather.setWindid(o.getInt("windid"));
			weather.setWinpid(o.getInt("winpid"));
			weathers.add(weather);
		}
		return weathers;
	}

}