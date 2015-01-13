package net.lxzhu.todaily;

import net.lxzhu.todaily.dao.Issue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class IssueListViewItemTag {

	public ImageView importantIcon;
	public ImageView urgencyIcon;
	public ImageView moreDetails;
	public TextView issueTitle;
	public TextView issueDescription;
	public View itemView;
	public int position;
	public IssueListViewAdapter adapter;

	public void attachToItemView(View itemView) {
		this.itemView = itemView;
		this.importantIcon = (ImageView) itemView.findViewById(R.id.issue_listview_item_important_icon);
		this.urgencyIcon = (ImageView) itemView.findViewById(R.id.issue_listview_item_urgency_icon);
		this.issueTitle = (TextView) itemView.findViewById(R.id.issue_listview_item_title);
		this.issueDescription = (TextView) itemView.findViewById(R.id.issue_listview_item_description);
		this.moreDetails = (ImageView) itemView.findViewById(R.id.issue_listview_item_detail_button);
		itemView.setTag(this);
	}

	public void updateItemView(IssueListViewAdapter adapter, int position, Issue issue) {
		this.adapter = adapter;
		this.position = position;
		this.issueTitle.setText(issue.getTitle());
		this.issueDescription.setText(issue.getDescription());
	}
}
