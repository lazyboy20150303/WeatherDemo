package com.example.weatherdemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter {
	
	
	private static final String DB_NAME="weather_app.db";
	private static final String TABLE_NAME="citycode";
	private static final int DB_VERSION=1;
	
	public static final String KEY_ID="_id";
	public static final String WEAID="weaid";
	public static final String CITYNM="citynm";
	public static final String CITYNO="cityno";
	public static final String CITYID="cityid";
	
	private static SQLiteDatabase db;
	private DBOpenHelper dbOpenHelper;
	private Context context;
	

	public DBAdapter(Context context){
		this.context = context;
	}
	
	public void open() throws SQLiteException{
		dbOpenHelper = new DBOpenHelper(context, DB_NAME, null, DB_VERSION);
		try{
			db = dbOpenHelper.getWritableDatabase();
		}catch(SQLiteException e){
			db = dbOpenHelper.getReadableDatabase();
		}
	}
	
	public void close() {
		 if (db != null){
		  //¹Ø±ÕÊý¾Ý¿â
		   db.close();
		   db = null;
		 }
	}
	
	private static class DBOpenHelper extends SQLiteOpenHelper{
		public DBOpenHelper(Context context,String name,CursorFactory factory,int version){
			super(context,name,factory,version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			String sql="CREATE TALBE"+TABLE_NAME+"("+
					KEY_ID+"INTEGER PRIMARY KEY,"+
					WEAID +"INTEGER NOT NULL,"+
					CITYNM+"VARCHAR(20) NOT NULL,"+
					CITYNO+"VARCHAR(20),"+
					CITYID+"VARCHAR(10) NOT NULL)";
			db.execSQL(sql);
		}

		@Override
		public void onUpgrade(SQLiteDatabase _db, int arg1, int arg2) {
			_db.execSQL("Drop table if exists "+DB_NAME);
			onCreate(_db);
		}
	}

	
}
