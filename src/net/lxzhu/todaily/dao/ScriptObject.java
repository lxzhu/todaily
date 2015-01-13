package net.lxzhu.todaily.dao;

import android.database.sqlite.SQLiteDatabase;

public interface ScriptObject {
	public void onCreate(SQLiteDatabase db);

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);
}
