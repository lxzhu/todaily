package net.lxzhu.todaily.dao;

import android.database.Cursor;

public abstract class DataBinderBase<T> implements DataBinder<T> {
	protected Cursor cursor;
	protected String tableAliasName;

	public DataBinderBase(String tableAliasName) {
		if (tableAliasName != null && tableAliasName != "" && !tableAliasName.endsWith("_")) {
			tableAliasName += "_";
		}
		this.tableAliasName = tableAliasName;
	}

	public void init(Cursor cursor) {
		this.cursor = cursor;
	}

	protected int getColumnIndex(String columnName) {
		return this.cursor.getColumnIndex(this.tableAliasName + columnName);
	}

	protected long get(int columnIndex, long alt) {
		if (columnIndex < 0)
			return alt;
		if (cursor.isNull(columnIndex))
			return alt;
		return cursor.getLong(columnIndex);
	}

	protected int get(int columnIndex, int alt) {
		if (columnIndex < 0)
			return alt;
		if (cursor.isNull(columnIndex))
			return alt;
		return cursor.getInt(columnIndex);
	}

	protected double get(int columnIndex, double alt) {
		if (columnIndex < 0)
			return alt;
		if (cursor.isNull(columnIndex))
			return alt;
		return cursor.getDouble(columnIndex);

	}

	protected byte[] get(int columnIndex, byte[] alt) {
		if (columnIndex < 0)
			return alt;
		if (cursor.isNull(columnIndex))
			return alt;
		return cursor.getBlob(columnIndex);
	}

	protected String get(int columnIndex, String alt) {
		if (columnIndex < 0)
			return alt;
		if (cursor.isNull(columnIndex))
			return alt;
		return cursor.getString(columnIndex);

	}

}
