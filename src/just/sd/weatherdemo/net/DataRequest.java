package just.sd.weatherdemo.net;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * 网络获取数据
 * @author sunqing
 *日期：2015.11.21
 */
public class DataRequest {

	/**
	 * 静态方法，从网络获取数据
	 * @param method 获取方式(get/post)
	 * @param url 数据接口地址
	 * @return <String>类型的数据
	 */
	public static String request(String method, String url) {

		HttpResponse httpResponse = null;

		StringBuffer result = new StringBuffer();

		try {

			if (method.equals("GET")) {

				HttpGet httpGet = new HttpGet(url);

				HttpClient httpClient = new DefaultHttpClient();

				httpResponse = httpClient.execute(httpGet);

				HttpEntity httpEntity = httpResponse.getEntity();

				result.append(EntityUtils.toString(httpEntity, "utf-8"));

				StatusLine statusLine = httpResponse.getStatusLine();

				result.append("协议:" + statusLine.getProtocolVersion() + "\r\n");

				int statusCode = statusLine.getStatusCode();

				result.append("状态码:" + statusCode + "\r\n");

			} else if (method.equals("POST")) {

				HttpPost httpPost = new HttpPost(url);

				HttpClient httpClient = new DefaultHttpClient();

				httpResponse = httpClient.execute(httpPost);

				HttpEntity httpEntity = httpResponse.getEntity();

				result.append(EntityUtils.toString(httpEntity, "utf-8"));
			}

		} catch (Exception e) {
			//e.printStackTrace();
			return null;
		}
		return result.toString();
	}

}
