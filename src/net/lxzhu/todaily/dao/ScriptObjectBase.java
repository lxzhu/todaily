package net.lxzhu.todaily.dao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.lxzhu.todaily.util.ArrayUtil;
import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public abstract class ScriptObjectBase implements ScriptObject {

	protected abstract int[] getSupportedVersions();

	public void onCreate(SQLiteDatabase db) {
		onUpgrade(db, 0, SqliteDB.currentDatabaseVersion());
	}

	@SuppressLint("DefaultLocale")
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		int[] supportedVersion = this.getSupportedVersions();
		// get index
		int fromIndex = ArrayUtil.indexOf(SqliteDB.VERSION_HISTORY, oldVersion);
		int toIndex = ArrayUtil.indexOf(SqliteDB.VERSION_HISTORY, newVersion);

		if (fromIndex < 0 || toIndex < 0 || toIndex <= fromIndex) {
			String message = String.format("no way to upgrade database from version %d to %d", oldVersion, newVersion);
			Log.e("ScriptObject", message);
		}

		for (int index = fromIndex; index < toIndex; index++) {
			int from = SqliteDB.VERSION_HISTORY[index];
			int to = SqliteDB.VERSION_HISTORY[index + 1];
			if (ArrayUtil.contains(supportedVersion, to)) {
				invokeUpgradeMethod(db,from, to);
			}
		}
	}

	@SuppressLint("DefaultLocale")
	private void invokeUpgradeMethod(SQLiteDatabase db,int from, int to) {
		String upgradeMethodName = String.format("from%08dTo%08d", from, to);
		try {			
			Method method = this.getClass().getDeclaredMethod(upgradeMethodName,SQLiteDatabase.class);
			method.invoke(this,db);
		} catch (NoSuchMethodException e) {

			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
