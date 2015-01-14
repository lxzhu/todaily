package net.lxzhu.todaily;

import net.lxzhu.todaily.dao.Issue;
import net.lxzhu.todaily.util.RequestCodes;
import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class IssueListViewItemMoreDetailsClickEventHandler implements View.OnClickListener {

	protected IssueListViewItemTag itemViewTag;

	protected Activity activity;

	public IssueListViewItemMoreDetailsClickEventHandler(Activity activity, IssueListViewItemTag itemViewTag) {
		this.activity = activity;
		this.itemViewTag = itemViewTag;
	}

	@Override
	public void onClick(View v) {
		Object item = this.itemViewTag.adapter.getItem(this.itemViewTag.position);
		Issue issue = (Issue) item;
		Intent intent = new Intent(this.activity, IssueDetailsActivity.class);
		intent.putExtra(IssueDetailsActivity.sExtraDataKeyIssueId, issue.getId());
		
		this.activity.startActivityForResult(intent, RequestCodes.RequestEditIssue);
	}

}
