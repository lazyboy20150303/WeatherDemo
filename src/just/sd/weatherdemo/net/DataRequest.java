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
 * �����ȡ����
 * @author sunqing
 *���ڣ�2015.11.21
 */
public class DataRequest {

	/**
	 * ��̬�������������ȡ����
	 * @param method ��ȡ��ʽ(get/post)
	 * @param url ���ݽӿڵ�ַ
	 * @return <String>���͵�����
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

				result.append("Э��:" + statusLine.getProtocolVersion() + "\r\n");

				int statusCode = statusLine.getStatusCode();

				result.append("״̬��:" + statusCode + "\r\n");

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
