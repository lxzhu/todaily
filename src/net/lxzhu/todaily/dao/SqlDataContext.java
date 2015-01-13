package net.lxzhu.todaily.dao;

import java.util.ArrayList;
import java.util.List;

import net.lxzhu.todaily.util.Linq;
import net.lxzhu.todaily.util.SelectDelegate;
import net.lxzhu.todaily.util.ToastUtil;
import android.content.Context;
import android.database.Cursor;

public class SqlDataContext {
	protected Context context;
	protected SqliteDB sqlite;

	public SqlDataContext(Context context) {
		this.context = context;
		this.sqlite = new SqliteDB(this.context);
	}

	protected <T> T fetchFirst(String sql, Object[] args, DataBinder<T> binder) {

		String[] texts = new Linq<Object>(args).select(new SelectDelegate<Object, String>() {
			@Override
			public String exec(Object data) {
				return data.toString();
			}
		}).toArray();
		return fetchFirst(sql, texts, binder);
	}

	protected <T> T fetchFirst(String sql, String[] args, DataBinder<T> binder) {
		T retObject = null;
		Cursor cursor = this.sqlite.getReadableDatabase().rawQuery(sql, args);
		cursor.moveToFirst();
		if (!cursor.isAfterLast()) {
			try {
				retObject = binder.getDataObject(cursor);
			} catch (Exception e) {
				e.printStackTrace();
				ToastUtil.showText(this.context, e.getLocalizedMessage());
			}
			cursor.close();
		}
		return retObject;
	}

	protected <T> List<T> fetchList(String sql, String[] args, DataBinder<T> binder) {
		List<T> retList = new ArrayList<T>();
		Cursor cursor = this.sqlite.getReadableDatabase().rawQuery(sql, args);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			try {
				T dataObject = binder.getDataObject(cursor);
				if (dataObject != null) {
					retList.add(dataObject);
				}
			} catch (Exception e) {
				e.printStackTrace();
				ToastUtil.showText(this.context, e.getLocalizedMessage());
			}
			cursor.moveToNext();
		}
		return retList;
	}

	

}
