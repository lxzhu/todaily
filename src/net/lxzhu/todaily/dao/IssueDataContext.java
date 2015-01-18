package net.lxzhu.todaily.dao;

import java.util.Calendar;
import java.util.List;

import net.lxzhu.todaily.util.DateTimeUtil;
import net.lxzhu.todaily.util.ToastUtil;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class IssueDataContext extends SqlDataContext {

	public IssueDataContext(Context context) {
		super(context);
	}

	public void saveIssue(Issue issue) {
		if (issue.getId() <= 0) {
			insertIssue(issue);
		} else {
			insertIssue(issue);
		}
	}

	public Issue findIssue(long id) {
		String sql = "select issue._id as issue__id, issue.title as issue_title, issue.description as issue_description,"
				+ " issue.important_level as issue_important_level, issue.create_datetime as issue_create_datetime ,"
				+ " location.latitude as location_latitude , location.longitude as location_longitude , location.altitude as location_altitude , location.speed as location_speed ,"
				+ " location.time as location_time , location.street as location_street   from issue  "
				+ " left join location on issue.location_id=location._id where issue._id=?";
		return this.fetchFirst(sql, new Object[] { id }, new IssueDataBinder2("issue","location"));
	}

	public List<Issue> getIssueList() {
		String sql = "select issue._id as issue__id, issue.title as issue_title, issue.description as issue_description, issue.important_level as issue_important_level, issue.create_datetime as issue_create_datetime,"
				+ " location.latitude as location_latitude, location.longitude as location_longitude, location.altitude as location_altitude, location.speed as location_speed,"
				+ " location.time as location_time, location.street as location_street from issue "
				+ " left join location  on issue.location_id=location._id ";
		List<Issue> retIssueList = this.fetchList(sql, null, new IssueDataBinder2("issue", "location"));
		return retIssueList;
	}

	public void deleteIssue(long issueId) {
		String sql = "select ifnull(location_id,0) from issue where _id=?";
		long locationId = this.fetchFirstLong(sql, new Object[] { issueId });
		if (locationId > 0) {
			this.deleteLocation(locationId);
		}
		this.sqlite.getWritableDatabase().delete("issue", "_id=?", new String[] { String.valueOf(issueId) });
	}

	protected void insertIssue(final Issue issue) {
		try {
			long locationId = 0;
			if (issue.hasLocation()) {
				this.saveLocation(issue.getLocation());
				locationId = issue.getLocation().getId();
			}
			issue.setCreateDateTime(Calendar.getInstance());
			ContentValues cv = new ContentValues();
			cv.put("title", issue.getTitle());
			cv.put("description", issue.getDescription());
			cv.put("important_level", issue.getImportantLevel());
			cv.put("create_datetime", DateTimeUtil.format(issue.getCreateDateTime()));
			cv.put("location_id", locationId);
			long issueId = this.sqlite.getWritableDatabase().insert("issue", null, cv);
			issue.setId(issueId);
		} catch (Exception ex) {
			ToastUtil.showText(this.context, ex.getLocalizedMessage());
			ex.printStackTrace();
		}
	}

	protected void updateIssue(Issue issue) {
		try {

			long locationId = 0;
			if (issue.hasLocation()) {
				this.saveLocation(issue.getLocation());
				locationId = issue.getLocation().getId();
			}

			issue.setUpdateDateTime(Calendar.getInstance());
			ContentValues cv = new ContentValues();
			cv.put("title", issue.getTitle());
			cv.put("description", issue.getDescription());
			cv.put("important_level", issue.getImportantLevel());
			cv.put("update_datetime", DateTimeUtil.format(issue.getUpdateDateTime()));
			cv.put("location_id", locationId);
			this.sqlite.getWritableDatabase().update("location", cv, "_id=?",
					new String[] { String.valueOf(issue.getId()) });

		} catch (Exception ex) {
			ToastUtil.showText(this.context, ex.getLocalizedMessage());
			ex.printStackTrace();
		}
	}

	public void saveLocation(Location location) {
		if (location.getId() > 0) {
			this.updateLocation(location);
		} else {
			this.insertLocation(location);
		}
	}

	protected void insertLocation(Location location) {
		ContentValues cv = new ContentValues();
		cv.put("latitude", location.getLatitude());
		cv.put("longitude", location.getLongitude());
		cv.put("altitude", location.getAltitude());
		cv.put("speed", location.getSpeed());
		cv.put("time", location.getTime());
		cv.put("street", location.getStreet());
		long locationId = this.sqlite.getWritableDatabase().insert("location", null, cv);
		location.setId(locationId);
	}

	protected void updateLocation(Location location) {
		ContentValues cv = new ContentValues();
		cv.put("latitude", location.getLatitude());
		cv.put("longitude", location.getLongitude());
		cv.put("altitude", location.getAltitude());
		cv.put("speed", location.getSpeed());
		cv.put("time", location.getTime());
		cv.put("street", location.getStreet());
		this.sqlite.getWritableDatabase().update("location", cv, "_id=?",
				new String[] { String.valueOf(location.getId()) });

	}

	public void deleteLocation(long id) {
		this.sqlite.getWritableDatabase().delete("location", "_id=?", new String[] { String.valueOf(id) });
	}

	public byte[] getIssueSlimBlob(long issueSlimId) {
		byte[] retBytes = this.fetchFirstBlob("select Issue_slim_blob from issue_slim where _id=?",
				new Object[] { issueSlimId });
		return retBytes;
	}

	public void saveIssueSlimBlob(long issueSlimId, byte[] blob) {
		ContentValues cv = new ContentValues();
		cv.put("issue_slim_blob", blob);
		this.sqlite.getWritableDatabase().update("issue_slim", cv, "_id=?",
				new String[] { String.valueOf(issueSlimId) });
	}

	public void deleteIssueSlim(long id) {
		long locationId = this.fetchFirstLong("select ifnull(location_id,0) from issue_slim where _id=?",
				new Object[] { id });
		if (locationId > 0) {
			this.deleteLocation(locationId);
		}
		this.sqlite.getWritableDatabase().delete("issue_slim", "_id=?", new String[] { String.valueOf(id) });
	}

	public void saveIssueSlim(IssueSlim slim) {
		if (slim.getId() > 0) {
			this.updateIssueSlim(slim);
		} else {
			this.insertIssueSlim(slim);
		}
	}

	public void insertIssueSlim(IssueSlim slim) {
		long locationId = 0;
		if (slim.hasLocation()) {
			this.saveLocation(slim.getLocation());
		}
		slim.setCreateDateTime(Calendar.getInstance());
		ContentValues cv = new ContentValues();
		cv.put("issue_id", slim.getIssueId());
		cv.put("location_id", locationId);
		cv.put("issue_slim_type_id", slim.getTypeId());
		cv.put("start_datetime", DateTimeUtil.format(slim.getStartDateTime()));
		cv.put("end_datetime", DateTimeUtil.format(slim.getEndDateTime()));
		cv.put("create_datetime", DateTimeUtil.format(slim.getCreateDateTime()));
		long issueSlimId = this.sqlite.getWritableDatabase().insert("issue_slim", null, cv);
		slim.setId(issueSlimId);

	}

	public void updateIssueSlim(IssueSlim slim) {
		long locationId = 0;
		if (slim.hasLocation()) {
			this.saveLocation(slim.getLocation());
		}
		slim.setUpdateDateTime(Calendar.getInstance());
		ContentValues cv = new ContentValues();
		cv.put("issue_id", slim.getIssueId());
		cv.put("location_id", locationId);
		cv.put("issue_slim_type_id", slim.getTypeId());
		cv.put("start_datetime", DateTimeUtil.format(slim.getStartDateTime()));
		cv.put("end_datetime", DateTimeUtil.format(slim.getEndDateTime()));
		cv.put("update_datetime", DateTimeUtil.format(slim.getUpdateDateTime()));
		this.sqlite.getWritableDatabase().update("issue_slim", cv, "_id=?",
				new String[] { String.valueOf(slim.getId()) });

	}
}
