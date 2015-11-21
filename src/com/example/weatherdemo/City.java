package com.example.weatherdemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.R.string;

public class City {
	
	 public static boolean GetCity(){
		 String requestUrl = "http://api.k780.com:88/?app=weather.city&format=json";
		 String jsonString=request("POST", requestUrl);
		 if (jsonString==null) {
			return false;
		}
		 try {
			@SuppressWarnings("unused")
			List<Map<String, Object>> cityList=parseJson(jsonString);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		 return true;
	 }

	 private static List<Map<String, Object>> parseJson(String data) throws JSONException{
		 List<Map<String, Object>> all=new ArrayList<Map<String,Object>>();
		 JSONObject result=new JSONObject(data);
		 JSONObject o=result.getJSONObject("result");
		 Iterator<Integer> iterator=o.keys();
		 while (iterator.hasNext()) {
			JSONObject item=o.getJSONObject(""+iterator.next());
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("weaid", item.getInt("weaid"));
			map.put("citynm", item.getString("citynm"));
			map.put("cityno", item.getString("cityno"));
			map.put("cityid", item.getString("cityid"));
			all.add(map);
		}
		 return all;
	 }
	 private static String request(String method, String url) {

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

                result.append("Ð­Òé:" + statusLine.getProtocolVersion() + "\r\n");

                int statusCode = statusLine.getStatusCode();

                result.append("×´Ì¬Âë:" + statusCode + "\r\n");

            } else if (method.equals("POST")) {

                HttpPost httpPost = new HttpPost(url);

                HttpClient httpClient = new DefaultHttpClient();

                httpResponse = httpClient.execute(httpPost);

                HttpEntity httpEntity = httpResponse.getEntity();

                result.append(EntityUtils.toString(httpEntity, "US-ASCII"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
