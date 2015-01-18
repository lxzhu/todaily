package net.lxzhu.todaily.adapter;

import android.app.Activity;
import android.view.*;

public class IssueListViewItemDeleteButtonClickEventHandler implements View.OnClickListener {
	protected IssueListViewItemTag itemViewTag;

	protected Activity activity;

	public IssueListViewItemDeleteButtonClickEventHandler(Activity activity, IssueListViewItemTag itemViewTag) {
		this.activity = activity;
		this.itemViewTag = itemViewTag;
	}

	@Override
	public void onClick(View v) {
		this.itemViewTag.delete();
	}

}
