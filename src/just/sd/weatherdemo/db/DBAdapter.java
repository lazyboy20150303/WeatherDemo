package just.sd.weatherdemo.db;

import android.content.ContentValues;
import android.content.Context;
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
public class DBAdapter {


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
			//关闭数据库
			db.close();
			db = null;
		}
	}

	public void insert(int weaid, String citynm, String cityno, String cityid) {
		ContentValues cv=new ContentValues();
		cv.put("weaid", weaid);
		cv.put("citynm", citynm);
		cv.put("cityno", cityno);
		cv.put("cityid", cityid);
		this.db.insert(TABLE_NAME, null, cv);
		close();
	}
	
	public void update(int weaid, String citynm, String cityno, String cityid){
		String whereClause=CITYID+"=?";
		String whereArgs[]=new String[]{cityid};
		ContentValues cv=new ContentValues();
		cv.put("weaid", weaid);
		cv.put("citynm", citynm);
		cv.put("cityno", cityno);
		this.db.update(TABLE_NAME, cv, whereClause, whereArgs);
		close();
	}
	
	public void delete(String cityid){
		String whereClause=CITYID+"=?";
		String whereArgs[]=new String[]{cityid};
		this.db.delete(TABLE_NAME, whereClause, whereArgs);
		close();
	}
	
	private static class DBOpenHelper extends SQLiteOpenHelper{
		public DBOpenHelper(Context context,String name,CursorFactory factory,int version){
			super(context,name,factory,version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			String sql="CREATE TALBE "+TABLE_NAME+"("+
					WEAID +" INTEGER NOT NULL,"+
					CITYNM+" VARCHAR(20) NOT NULL,"+
					CITYNO+" VARCHAR(20),"+
					CITYID+" VARCHAR(10) PRIMARY KEY)";
			db.execSQL(sql);
		}

		@Override
		public void onUpgrade(SQLiteDatabase _db, int arg1, int arg2) {
			_db.execSQL("Drop table if exists "+DB_NAME);
			onCreate(_db);
		}
	}
}
