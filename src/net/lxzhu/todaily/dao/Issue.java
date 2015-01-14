package net.lxzhu.todaily.dao;

import java.util.Calendar;

public class Issue implements SupportViewModelTags {
	protected long id;
	protected String title;
	protected String description;
	protected Calendar createDateTime;
	protected Calendar updateDateTime;
	protected Calendar planStartDateTime;
	protected Calendar planEndDateTime;
	protected Calendar actualStartDateTime;
	protected Calendar actualEndDateTime;
	protected int StatusCode;
	protected int ImportantLevel;

	public Issue() {
		this.createDateTime = Calendar.getInstance();
	}

	public Issue(long id, String title, String description) {
		this.setId(id);
		this.setTitle(title);
		this.setDescription(description);
		this.createDateTime = Calendar.getInstance();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

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

	public Calendar getPlanStartDateTime() {
		return planStartDateTime;
	}

	public void setPlanStartDateTime(Calendar planStartDateTime) {
		this.planStartDateTime = planStartDateTime;
	}

	public Calendar getPlanEndDateTime() {
		return planEndDateTime;
	}

	public void setPlanEndDateTime(Calendar planEndDateTime) {
		this.planEndDateTime = planEndDateTime;
	}

	public Calendar getActualStartDateTime() {
		return actualStartDateTime;
	}

	public void setActualStartDateTime(Calendar actualStartDateTime) {
		this.actualStartDateTime = actualStartDateTime;
	}

	public Calendar getActualEndDateTime() {
		return actualEndDateTime;
	}

	public void setActualEndDateTime(Calendar actualEndDateTime) {
		this.actualEndDateTime = actualEndDateTime;
	}

	public int getStatusCode() {
		return StatusCode;
	}

	public void setStatusCode(int statusCode) {
		StatusCode = statusCode;
	}

	public int getImportantLevel() {
		return ImportantLevel;
	}

	public void setImportantLevel(int importantLevel) {
		ImportantLevel = importantLevel;
	}

	public String toString() {
		return this.getTitle();
	}

	private ViewModelTags mViewModelTags;

	public ViewModelTags getExtraTags() {
		if (mViewModelTags == null) {
			mViewModelTags = new ViewModelTags();
		}
		return mViewModelTags;
	}

}
