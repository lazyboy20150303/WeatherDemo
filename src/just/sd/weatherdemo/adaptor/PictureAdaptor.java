package just.sd.weatherdemo.adaptor;

import java.io.BufferedInputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * 图片适配器
 * 图片url转换为assets文件夹下图片
 * @author sunqing
 *日期：2015.11.22
 */
public class PictureAdaptor {

	Context context;
	public PictureAdaptor(Context context) {
		super();
		this.context=context;
	}
	
	/**
	 * 映射本地图片
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public Bitmap getBitMap(String url) throws IOException{
		//http://api.k780.com:88/upload/weather/d/1.gif
		int i=url.lastIndexOf("/")+1;
		int j=url.lastIndexOf(".");
		String imgindex=url.substring(i,j);
	    BufferedInputStream bis =
	    		new BufferedInputStream(context.getAssets().open("img/b/"+imgindex+".png"));
	    Bitmap bm = BitmapFactory.decodeStream(bis);
	    return bm;
	}
	
	/**
	 * 动态设置背景天气
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public Bitmap getbgBitMap(String url) throws IOException{
		int i=url.lastIndexOf("/")+1;
		int j=url.lastIndexOf(".");
		String imgindex=url.substring(i,j);
	    BufferedInputStream bis =
	    		new BufferedInputStream(context.getAssets().open("img/bg/"+imgindex+".jpg"));
	    Bitmap bm = BitmapFactory.decodeStream(bis);
	    return bm;
	}
}
