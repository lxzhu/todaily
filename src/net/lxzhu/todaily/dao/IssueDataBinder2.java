package net.lxzhu.todaily.dao;

import android.database.Cursor;

public class IssueDataBinder2 implements DataBinder<Issue> {
	protected IssueDataBinder issueDataBinder;
	protected LocationDataBinder locationDataBinder;

	public IssueDataBinder2() {
		this("issue", "location");
	}

	public IssueDataBinder2(String issueTableAliasName, String locationTableAliasName) {
		this.issueDataBinder = new IssueDataBinder(issueTableAliasName);
		this.locationDataBinder = new LocationDataBinder(locationTableAliasName);
	}

	public void init(Cursor cursor) {
		this.issueDataBinder.init(cursor);
		this.locationDataBinder.init(cursor);
	}

	public Issue getDataObject(Cursor cursor) {
		Issue issue = null;
		try {
			issue = this.issueDataBinder.getDataObject(cursor);
			Location location = this.locationDataBinder.getDataObject(cursor);
			issue.setLocation(location);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return issue;
	}
}
