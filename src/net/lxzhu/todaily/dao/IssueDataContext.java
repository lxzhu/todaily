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
		String sql = "select _id as c0, title as c1, description as c2, important_level as c3, create_date_time as c4,"
				+ " location_latitude as c5, location_longitude as c6, location_altitude as c7, location_speed as c8,"
				+ " location_time as c9, location_street as c10 from " + IssueScriptObject.TABLE_NAME + " where _id=?";
		return this.fetchFirst(sql, new Object[] { id }, new IssueDataBinder());
	}

	public List<Issue> getIssue() {
		String sql = "select _id as c0, title as c1, description as c2, important_level as c3, create_date_time as c4,"
				+ " location_latitude as c5, location_longitude as c6, location_altitude as c7, location_speed as c8,"
				+ " location_time as c9, location_street as c10 from "
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
				Location location = new Location();
				location.latitude = cursor.isNull(5) ? 0 : cursor.getDouble(5);
				location.longitude = cursor.isNull(6) ? 0 : cursor.getDouble(6);
				location.altitude = cursor.isNull(7) ? 0 : cursor.getDouble(7);
				location.speed = cursor.isNull(8) ? 0 : cursor.getDouble(8);
				location.time = cursor.isNull(9) ? 0 : cursor.getLong(9);
				location.street = cursor.isNull(10) ? "" : cursor.getString(10);
				issue.setLocation(location);
			} catch (Exception e) {
				e.printStackTrace();
				issue = null;
			}
			return issue;
		}

	}

	protected void insert(final Issue issue) {
		try {
			Location location = issue.getLocation();
			if (location == null) {
				location = new Location();
			}

			String sql = "insert into " + IssueScriptObject.TABLE_NAME
					+ "(title,description,important_level,create_date_time,location_latitude,location_longitude,"
					+ " location_altitude,location_speed,location_time,location_street)"
					+ "values (?,?,?,?,?,?,?,?,?,?)";
			this.sqlite.getWritableDatabase().execSQL(
					sql,
					new Object[] { issue.getTitle(), issue.getDescription(), issue.getImportantLevel(),
							DateTimeUtil.format(issue.getCreateDateTime()), location.latitude, location.longitude,
							location.altitude, location.speed, location.time, location.street });
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
			Location location = issue.getLocation();
			if (location == null) {
				location = new Location();
			}
			issue.setUpdateDateTime(Calendar.getInstance());
			String sql = "update " + IssueScriptObject.TABLE_NAME + " set title=?,description=?,"
					+ " important_level=?, update_date_time=?, location_latitude=?, location_longitude=?,"
					+ " location_altitude=?, location_speed=?, location_time=? ,location_street=?" + " where _id=?";

			this.sqlite.getWritableDatabase().execSQL(
					sql,
					new Object[] { issue.getTitle(), issue.getDescription(), issue.getImportantLevel(),
							DateTimeUtil.format(issue.getUpdateDateTime()), location.latitude, location.longitude,
							location.altitude, location.speed, location.time, location.street, issue.getId() });

		} catch (Exception ex) {
			ToastUtil.showText(this.context, ex.getLocalizedMessage());
			ex.printStackTrace();
		}
	}
}
