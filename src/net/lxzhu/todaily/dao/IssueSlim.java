package net.lxzhu.todaily.dao;

import java.sql.Date;
import java.util.Calendar;

public class IssueSlim {
	protected long id;
	protected long issueId;
	protected Calendar startDateTime;
	protected Calendar endDateTime;
	protected Calendar createDateTime;
	protected Calendar updateDateTime;
	public Calendar getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Calendar createDateTime) {
		this.createDateTime = createDateTime;
	}

	public Calendar getUpdateDateTime() {
		return updateDateTime;
	}

	public void setUpdateDateTime(Calendar updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

	protected int typeId;
	protected Location location;

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

	public Calendar getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(Calendar startDateTime) {
		this.startDateTime = startDateTime;
	}

	public Calendar getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(Calendar endDateTime) {
		this.endDateTime = endDateTime;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public boolean hasLocation() {
		return this.location != null;
	}

	public boolean isFinished() {
		return this.endDateTime != null;
	}

	public long duration() {
		return 0L;
	}

}
