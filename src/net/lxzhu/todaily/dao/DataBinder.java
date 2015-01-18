package net.lxzhu.todaily.dao;

import android.database.Cursor;

public interface DataBinder<T> {
	public void init(Cursor cursor);

	public T getDataObject(Cursor cursor);
}
