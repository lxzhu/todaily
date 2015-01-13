/**
 * 
 */
package net.lxzhu.todaily.dao;

import android.database.sqlite.SQLiteDatabase;

/**
 * @author RFIAS
 *
 */
public class IssueSlimScriptObject implements ScriptObject {

	@Override
	public void onCreate(SQLiteDatabase db) {
		StringBuffer sql = new StringBuffer();
		sql = new StringBuffer();
		sql.append("create table issue_slim( ")
				.append("_ID bigint primary key autoincrement, ")
				.append("issue_id bigint not null, ")
				.append("create_date_time, date not null, ")
				.append("update_date_time, date ").append(")");
		db.execSQL(sql.toString());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
