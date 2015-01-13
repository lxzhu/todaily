package net.lxzhu.todaily.dao;

import android.database.Cursor;

public interface DataBinder<T> {
	public T getDataObject(Cursor cursor);
}
