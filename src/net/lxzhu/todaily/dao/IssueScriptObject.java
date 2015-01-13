package net.lxzhu.todaily.dao;

import android.database.sqlite.SQLiteDatabase;

public class IssueScriptObject implements ScriptObject {

	public static final String TABLE_NAME = "issue";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_DESCRIPTION = "description";
	public static final String COLUMN_CREATE_DATE_TIME = "create_date_time";
	public static final String COLUMN_UPDATE_DATE_TIME = "update_date_time";
	public static final String COLUMN_PLAN_START_DATE_TIME = "plan_start_date_time";
	public static final String COLUMN_PLAN_END_DATE_TIME = "plan_end_date_time";
	public static final String COLUMN_ACTUAL_START_DATE_TIME = "actual_start_date_time";
	public static final String COLUMN_ACTUAL_END_DATE_TIME = "actual_end_date_time";
	public static final String COLUMN_IMPORTANT_LEVEL = "important_level";
	public static final String[] COLUMN_NAMES = new String[] { COLUMN_ID,
			COLUMN_TITLE, COLUMN_DESCRIPTION, COLUMN_CREATE_DATE_TIME,
			COLUMN_UPDATE_DATE_TIME, COLUMN_PLAN_START_DATE_TIME,
			COLUMN_PLAN_END_DATE_TIME, COLUMN_ACTUAL_START_DATE_TIME,
			COLUMN_ACTUAL_END_DATE_TIME, COLUMN_IMPORTANT_LEVEL};

	@Override
	public void onCreate(SQLiteDatabase db) {

		String sql = "create table issue(_ID integer primary key autoincrement,"
				+ " title nvarchar(50) not null,"
				+ " description nvarchar(2000),"
				+ " create_date_time datetime not null,"
				+ " update_date_time datetime,"
				+ " plan_start_date_time datetime  null,"
				+ " plan_end_date_time datetime,"
				+ " actual_start_date_time datetime, "
				+ " actual_end_date_time datetime,"
				+ " important_level int not null" + ")";
		db.execSQL(sql);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
