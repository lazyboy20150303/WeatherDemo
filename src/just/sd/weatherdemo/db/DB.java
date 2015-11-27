package just.sd.weatherdemo.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库适配器
 * 存储城市信息
 * @author sunqing
 *日期：2015.11.21
 */
public class DB {

	private static final String DB_NAME="weather_app.db";
	private static final String TABLE_NAME="citycode";
	private static final int DB_VERSION=1;

	public static final String WEAID="weaid";
	public static final String CITYNM="citynm";
	public static final String CITYNO="cityno";
	public static final String CITYID="cityid";

	private SQLiteDatabase db;
	private DBOpenHelper dbOpenHelper;
	private Context context;


	public DB(Context context){
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
			//关闭数据库
			db.close();
			db = null;
		}
	}

	/**
	 * 根据城市名查询
	 * @param citynm 城市名
	 * @return 城市信息
	 */
	public int findid(String citynm){
		String sql="SELECT "+WEAID+","+CITYNM+" FROM "+TABLE_NAME+" WHERE "+CITYNM+"=?";
		String args[]=new String[]{citynm};
		Cursor result=this.db.rawQuery(sql, args);
		int weatid = 0;//不存在的城市
		for(result.moveToFirst();!result.isAfterLast();result.moveToNext()){
			weatid=result.getInt(0);
		}
		return weatid;
	}
	
	public int getCount(){
		int count=0;
		String sql="SELECT COUNT("+WEAID+") FROM "+TABLE_NAME;
		Cursor result=db.rawQuery(sql, null);
		for(result.moveToFirst();!result.isAfterLast();result.moveToNext()){
			count=result.getInt(0);
		}
		return count;
	}
	
	public List<Map<String, String>> findPage(int currentPage,int lineSize){
		List<Map<String, String>> cities=new ArrayList<>();
		String sql="SELECT "+WEAID+","+CITYNM+","+
		CITYNO+","+CITYID+" FROM "+TABLE_NAME+" LIMIT ?,?";
		String args[]=new String[]{
				String.valueOf((currentPage-1)*lineSize),
				String.valueOf(lineSize)
		};
		Cursor result=this.db.rawQuery(sql, args);
		for(result.moveToFirst();!result.isAfterLast();result.moveToNext()){
			Map<String, String> city=new HashMap<>();
			city.put("db_cityname", result.getString(1));
			cities.add(city);
		}
		return cities;
	}
	
	public void insert(int weaid, String citynm, String cityno, String cityid) {
		if(findid(citynm)==0){//如果表中不存在该城市
			ContentValues cv=new ContentValues();
			cv.put("weaid", weaid);
			cv.put("citynm", citynm);
			cv.put("cityno", cityno);
			cv.put("cityid", cityid);
			this.db.insert(TABLE_NAME, null, cv);
		}else {
			this.update(weaid, citynm, cityno, cityid);
		}
	}
	
	public void update(int weaid, String citynm, String cityno, String cityid){
		String whereClause=WEAID+"=?";
		String whereArgs[]=new String[]{""+weaid};
		ContentValues cv=new ContentValues();
		cv.put("citynm", citynm);
		cv.put("cityid", cityid);
		cv.put("cityno", cityno);
		this.db.update(TABLE_NAME, cv, whereClause, whereArgs);
	}
	
	public void delete(int weaid){
		String whereClause=WEAID+"=?";
		String whereArgs[]=new String[]{""+weaid};
		this.db.delete(TABLE_NAME, whereClause, whereArgs);
	}
	
	private static class DBOpenHelper extends SQLiteOpenHelper{
		public DBOpenHelper(Context context,String name,CursorFactory factory,int version){
			super(context,name,factory,version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			String sql="CREATE TABLE "+TABLE_NAME+"("+
					WEAID +" INTEGER PRIMARY KEY,"+
					CITYNM+" VARCHAR(20) NOT NULL,"+
					CITYNO+" VARCHAR(20),"+
					CITYID+" VARCHAR(10) NOT NULL)";
			db.execSQL(sql);
		}

		@Override
		public void onUpgrade(SQLiteDatabase _db, int arg1, int arg2) {
			_db.execSQL("Drop table if exists "+DB_NAME);
			onCreate(_db);
		}
	}
}
