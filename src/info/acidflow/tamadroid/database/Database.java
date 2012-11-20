package info.acidflow.tamadroid.database;

import info.acidflow.tamadroid.utils.UtilsFile;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database implements DatabaseInterface {

	private final Context context; 
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;
	private static final String DATABASE_NAME = "tamadroid";
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_CREATE = "sql/create_db.sql";
	private static final String TAG = "DB";
	private static final String TABLE_EGG = "eggs";
	private static final String COLUMN_EGG_TIME = "time";
	private static final String COLUMN_EGG_RAD = "rad";
	private static final String COLUMN_EGG_OPEN = "open";


	public Database(Context ctx) 
	{
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}

	private static class DatabaseHelper extends SQLiteOpenHelper 
	{
		private final Context context; 

		DatabaseHelper(Context c) 
		{
			super(c, DATABASE_NAME, null, DATABASE_VERSION);
			context = c;
		}

		@Override
		public void onCreate(SQLiteDatabase db) 
		{	
			InputStream db_create = null;
			String create_script = null;
			try {
				db_create = context.getAssets().open(DATABASE_CREATE);
				create_script = UtilsFile.inputStreamtoString(db_create);
				db.execSQL(create_script);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				//TODO
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub

		}
	} 

	public Database open() throws SQLException 
	{
		db = DBHelper.getWritableDatabase();
		return this;
	}

	//---closes the database---    
	public void close() 
	{
		DBHelper.close();
	}

	@Override
	public double getEggTime() {
		// TODO Auto-generated method stub
		Cursor query = db.query(TABLE_EGG, 
								new String[] {COLUMN_EGG_TIME},
								null, null, null, null, null);
		int index = query.getColumnIndex(COLUMN_EGG_TIME);
		double time = query.getDouble(index);
		query.close();
		return time;
	}

	@Override
	public void updateEggTime(double time) {
		// TODO Auto-generated method stub
		ContentValues args = new ContentValues();
		args.put(COLUMN_EGG_TIME, time);
		int ret = db.update(TABLE_EGG, args, null, null);
		Log.i(TAG,"Up EggTime with value " + time + ", " + ret + " rows affected.");
	}

	@Override
	public boolean getRadiatorState() {
		// TODO Auto-generated method stub
		Cursor query = db.query(TABLE_EGG, 
								new String[] {COLUMN_EGG_RAD},
								null, null, null, null, null);
		int index = query.getColumnIndex(COLUMN_EGG_RAD);
		int state = query.getInt(index);
		query.close();
		Log.i(TAG,"Get Radiator State " + state);
		return (state == 0) ? false : true;
	}

	@Override
	public void setRadiatorState(boolean state) {
		// TODO Auto-generated method stub
		ContentValues args = new ContentValues();
		args.put(COLUMN_EGG_RAD, state ? 0 : 1);
		int ret = db.update(TABLE_EGG, args, null, null);
		Log.i(TAG,"Up Radiator with value " + state + ", " + ret + " rows affected.");
	}

	@Override
	public boolean isEggOpen() {
		// TODO Auto-generated method stub
		Cursor query = db.query(TABLE_EGG, 
								new String[] {COLUMN_EGG_OPEN},
								null, null, null, null, null);
		int index = query.getColumnIndex(COLUMN_EGG_OPEN);
		int state = query.getInt(index);
		query.close();
		Log.i(TAG, "Is Egg Open " + state);
		return (state == 0) ? false : true;
	}

	@Override
	public void setEggOpen() {
		// TODO Auto-generated method stub
		ContentValues args = new ContentValues();
		args.put(COLUMN_EGG_OPEN, 1);
		int ret = db.update(TABLE_EGG, args, null, null);
		Log.i(TAG,"Set Egg open, " + ret + " rows affected.");
	}
	
}
