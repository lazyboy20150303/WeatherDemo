package com.example.weatherdemo;

import java.text.SimpleDateFormat;

public class SimpleSms {
	public String sender;
	public String body;
	public String receiveTime;
	public String returnResults;
	
	public SimpleSms(){
	}

	public SimpleSms(String sender,String body){
		this.sender = sender;
		 this.body = body;
		 SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd"
		 +" "+"hh:mm:ss");
		 this.receiveTime = tempDate.format(new java.util.Date());
		 this.returnResults = "";
	}
	
}
