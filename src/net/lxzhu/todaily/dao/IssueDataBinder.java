package net.lxzhu.todaily.dao;

import java.util.Calendar;

import net.lxzhu.todaily.util.DateTimeUtil;
import android.database.Cursor;

class IssueDataBinder extends DataBinderBase<Issue> {

	public IssueDataBinder(String tableAliasName) {
		super(tableAliasName);
	}

	private int columnIndexId;
	private int columnIndexTitle;
	private int columnIndexDescription;
	private int columnIndexImportantLevel;
	private int columnIndexCreateDateTime;
	private int columnIndexUpdateDateTime;
	private int columnIndexLocationId;

	public void init(Cursor cursor) {
		super.init(cursor);
		this.columnIndexId = getColumnIndex("_id");
		this.columnIndexTitle = getColumnIndex("title");
		this.columnIndexDescription = getColumnIndex("description");
		this.columnIndexCreateDateTime = getColumnIndex("create_datetime");
		this.columnIndexImportantLevel = getColumnIndex("important_level");
		this.columnIndexUpdateDateTime = getColumnIndex("update_datetime");
		this.columnIndexLocationId = getColumnIndex("location_id");
	}

	@Override
	public Issue getDataObject(Cursor cursor) {
		Issue issue = null;
		try {
			issue = new Issue();
			issue.setId(get(columnIndexId, 0L));
			issue.setTitle(get(columnIndexTitle, ""));
			issue.setDescription(get(columnIndexDescription, ""));
			issue.setImportantLevel(get(columnIndexImportantLevel, 0));
			Calendar dateTime = DateTimeUtil.parse(get(columnIndexCreateDateTime, ""));
			if (dateTime != null) {
				issue.setCreateDateTime(dateTime);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			issue = null;
		}
		return issue;
	}

}
