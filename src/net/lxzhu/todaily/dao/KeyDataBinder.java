package net.lxzhu.todaily.dao;

import android.database.Cursor;

public class KeyDataBinder extends DataBinderBase<Long> {

	protected String keyColumnName;
	protected int keyColumnIndex;

	public KeyDataBinder(String tableAlaisName, String keyColumnName) {
		super(tableAlaisName);
		this.keyColumnName = keyColumnName;
	}

	public KeyDataBinder(String keyColumnName) {
		this("", keyColumnName);
	}

	public KeyDataBinder() {
		this("", "_id");
	}

	public void init(Cursor cursor) {
		super.init(cursor);
		this.keyColumnIndex = this.getColumnIndex(this.keyColumnName);
	}

	@Override
	public Long getDataObject(Cursor cursor) {
		return cursor.getLong(this.keyColumnIndex);
	}

}
