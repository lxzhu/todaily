package net.lxzhu.todaily.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

@SuppressWarnings("rawtypes")
public class SqliteDB extends SQLiteOpenHelper {

	protected static final String DATABASE_NAME = "todaily";
	// the current database version
	public static final int[] VERSION_HISTORY = new int[] { 20141224 };

	public static int currentDatabaseVersion() {
		int lastObjectIndex = VERSION_HISTORY.length - 1;
		return VERSION_HISTORY[lastObjectIndex];
	}

	protected static Class[] ScriptObjects = new Class[] { IssueScriptObject.class };

	public SqliteDB(Context context) {
		super(context, DATABASE_NAME, null, currentDatabaseVersion());
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		for (Class scriptObjectClass : ScriptObjects) {
			try {
				ScriptObject script = (ScriptObject) scriptObjectClass
						.newInstance();
				script.onCreate(db);
			} catch (InstantiationException e) {
				Log.e(SqliteDB.class.getName(), e.getLocalizedMessage());
			} catch (IllegalAccessException e) {
				Log.e(SqliteDB.class.getName(), e.getLocalizedMessage());
			}
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		for (Class scriptObjectClass : ScriptObjects) {
			try {
				ScriptObject script = (ScriptObject) scriptObjectClass
						.newInstance();
				script.onUpgrade(db, oldVersion, newVersion);
			} catch (InstantiationException e) {
				Log.e(SqliteDB.class.getName(), e.getLocalizedMessage());
			} catch (IllegalAccessException e) {
				Log.e(SqliteDB.class.getName(), e.getLocalizedMessage());
			}
		}
	}

}
