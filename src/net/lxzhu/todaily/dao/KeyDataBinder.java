package net.lxzhu.todaily.dao;

import android.database.Cursor;
import net.lxzhu.todaily.dao.DataBinder;

public class KeyDataBinder implements DataBinder<Long> {

	protected int keyColumnIndex;

	public KeyDataBinder() {
		this(0);
	}

	public KeyDataBinder(int keyColumnIndex) {
		this.keyColumnIndex = keyColumnIndex;
	}

	@Override
	public Long getDataObject(Cursor cursor) {
		return cursor.getLong(this.keyColumnIndex);
	}

}
