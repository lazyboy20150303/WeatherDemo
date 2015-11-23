package just.sd.weatherdemo.model;

import android.graphics.Bitmap;

/**
 * 百度天气属性
 * from api.map.baidu.com
 * @author sunqing
 *日期：2015.11.21
 */
public class Forecast {
	
	public String day_of_week;
	public String dayPictureUrl;
	public String nightPictureUrl;
	public String weather;
	public String wind;
	public String temperature;
	
	public Bitmap dayPicture;
	public Bitmap nightPicture;
}
