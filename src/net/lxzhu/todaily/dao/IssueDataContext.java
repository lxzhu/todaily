package net.lxzhu.todaily.dao;

import java.util.Calendar;
import java.util.List;

import net.lxzhu.todaily.util.DateTimeUtil;
import net.lxzhu.todaily.util.ToastUtil;
import android.content.Context;
import android.database.Cursor;

public class IssueDataContext extends SqlDataContext {

	public IssueDataContext(Context context) {
		super(context);
	}

	public void save(Issue issue) {
		if (issue.getId() <= 0) {
			insert(issue);
		} else {
			update(issue);
		}
	}

	public Issue find(long id) {
		String sql = "select _id, title,description,important_level,create_date_time from "
				+ IssueScriptObject.TABLE_NAME + " where _id=?";
		return this.fetchFirst(sql, new Object[] { id }, new IssueDataBinder());
	}

	public List<Issue> getIssue() {
		String sql = "select _id, title,description,important_level,create_date_time from "
				+ IssueScriptObject.TABLE_NAME;
		List<Issue> retIssueList = this.fetchList(sql, null, new IssueDataBinder());
		return retIssueList;
	}

	public void delete(long id) {

		this.sqlite.getWritableDatabase().delete(IssueScriptObject.TABLE_NAME, "_id=?",
				new String[] { String.format("%d", id) });
	}

	class IssueDataBinder implements DataBinder<Issue> {

		@Override
		public Issue getDataObject(Cursor cursor) {
			Issue issue = null;
			try {
				issue = new Issue();
				issue.setId(cursor.getLong(0));
				issue.setTitle(cursor.getString(1));
				issue.setDescription(cursor.getString(2));
				issue.setImportantLevel(cursor.getInt(3));
				Calendar dateTime = DateTimeUtil.parse(cursor.getString(4));
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

	protected void insert(final Issue issue) {
		try {
			String sql = "insert into " + IssueScriptObject.TABLE_NAME
					+ "(title,description,important_level,create_date_time)" + "values (?,?,?,?)";
			this.sqlite.getWritableDatabase().execSQL(
					sql,
					new Object[] { issue.getTitle(), issue.getDescription(), issue.getImportantLevel(),
							DateTimeUtil.format(issue.getCreateDateTime()) });
			sql = "select last_insert_rowid()";

			long issueId = this.fetchFirst(sql, null, new KeyDataBinder());
			issue.setId(issueId);
		} catch (Exception ex) {
			ToastUtil.showText(this.context, ex.getLocalizedMessage());
			ex.printStackTrace();
		}
	}

	protected void update(Issue issue) {
		try {
			issue.setUpdateDateTime(Calendar.getInstance());
			String sql = "update " + IssueScriptObject.TABLE_NAME + " set title=?," + " description=?,"
					+ " important_level=?," + " update_date_time=?" + "where _id=?";

			this.sqlite.getWritableDatabase().execSQL(
					sql,
					new Object[] { issue.getTitle(), issue.getDescription(), issue.getImportantLevel(),
							DateTimeUtil.format(issue.getUpdateDateTime()), issue.getId() });

		} catch (Exception ex) {
			ToastUtil.showText(this.context, ex.getLocalizedMessage());
			ex.printStackTrace();
		}
	}
}
