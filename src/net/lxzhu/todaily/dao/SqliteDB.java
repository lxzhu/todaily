package net.lxzhu.todaily.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import net.lxzhu.todaily.util.ArrayUtil;
import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteDB extends SQLiteOpenHelper {
	protected static final String DATABASE_NAME = "todaily";
	// the current database version
	protected static String[] sScriptFileList;
	protected static int[] sVersionList;
	protected static Object sLock = new Object();
	protected Context context;

	public static int currentDatabaseVersion() {
		int size = sVersionList.length;
		return sVersionList[size - 1];
	}

	public SqliteDB(Context context) {
		super(context, DATABASE_NAME, null, currentDatabaseVersion());
		this.context = context;
	}

	public static void loadSqlScripts(Context context) {
		if (null == sVersionList) {
			synchronized (sLock) {
				if (null == sVersionList) {
					loadSqlScripts0(context);
				}
			}
		}
	}

	protected static void loadSqlScripts0(Context context) {
		try {
			sScriptFileList = context.getAssets().list("database");
			Arrays.sort(sScriptFileList);
			sVersionList = new int[sScriptFileList.length];
			for (int index = 0; index < sScriptFileList.length; index++) {
				String version = sScriptFileList[index].substring(2, 10);
				sVersionList[index] = Integer.valueOf(version);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		onUpgrade(db, 0, currentDatabaseVersion());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		int fromIndex = (oldVersion == 0) ? -1 : ArrayUtil.indexOf(sVersionList, oldVersion);
		int toIndex = ArrayUtil.indexOf(sVersionList, newVersion);
		for (int index = fromIndex + 1; index <= toIndex; index++) {
			String file = sScriptFileList[index];
			execScriptFile(db, file);
		}
	}

	protected void execScriptFile(SQLiteDatabase db, String fileName) {
		InputStream is = null;
		try {
			is = this.context.getAssets().open("database/" + fileName);
			SqlScriptReader reader = new SqlScriptReader(is);
			while (reader.next()) {
				String sql = reader.current();
				db.execSQL(sql);
			}
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
