package just.sd.weatherdemo.adaptor;

import java.io.BufferedInputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * ͼƬ������
 * ͼƬurlת��Ϊassets�ļ�����ͼƬ
 * @author sunqing
 *���ڣ�2015.11.22
 */
public class PictureAdaptor {

	Context context;
	public PictureAdaptor(Context context) {
		super();
		this.context=context;
	}
	
	/**
	 * ӳ�䱾��ͼƬ
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
	 * ��̬���ñ�������
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
