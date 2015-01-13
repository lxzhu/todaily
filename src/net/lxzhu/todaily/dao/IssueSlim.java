package net.lxzhu.todaily.dao;

import java.sql.Date;

public class IssueSlim {
	protected long id;
	protected long issueId;
	protected Date startDateTime;
	protected Date endDateTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getIssueId() {
		return issueId;
	}

	public void setIssueId(long issueId) {
		this.issueId = issueId;
	}

	public Date getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}

	public Date getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
	}

	public boolean isFinished() {
		return this.endDateTime != null;
	}

	public long duration() {
		return 0L;
	}

}
