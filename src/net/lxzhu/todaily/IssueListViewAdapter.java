package net.lxzhu.todaily;

import net.lxzhu.todaily.dao.Issue;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class IssueListViewAdapter extends ArrayAdapter<Issue> {

	public IssueListViewAdapter(Context context) {
		super(context, android.R.layout.simple_list_item_1);
	}

	public View getView(int position, View itemView, ViewGroup root) {
		IssueListViewItemTag tag = null;
		if (itemView == null) {
			LayoutInflater inflater = LayoutInflater.from(this.getContext());
			itemView = inflater.inflate(R.layout.issue_listview_item, null);
			tag = new IssueListViewItemTag();
			tag.attachToItemView(itemView);
			Activity activity = (Activity) this.getContext();

			tag.onClickMoreDetails(new IssueListViewItemMoreDetailsClickEventHandler(activity, tag));
			tag.onClickDeleteButton(new IssueListViewItemDeleteButtonClickEventHandler(activity, tag));
			itemView.setOnTouchListener(new IssueListViewItemTouchEvents(tag));
		} else {
			tag = (IssueListViewItemTag) itemView.getTag();
		}

		Issue issue = this.getItem(position);
		tag.updateItemView(this, position, issue);
		return itemView;
	}
}
