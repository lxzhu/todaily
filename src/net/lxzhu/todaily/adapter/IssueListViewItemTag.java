package net.lxzhu.todaily.adapter;

import net.lxzhu.todaily.R;
import net.lxzhu.todaily.R.id;
import net.lxzhu.todaily.dao.Issue;
import net.lxzhu.todaily.dao.IssueDataContext;
import net.lxzhu.todaily.util.Padding;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class IssueListViewItemTag {

	protected ImageView importantIcon;
	protected ImageView urgencyIcon;
	protected ImageView moreDetails;
	protected TextView issueTitle;
	protected TextView issueDescription;
	protected Button deleteButton;
	protected View itemView;
	protected int position;
	protected IssueListViewAdapter adapter;
	protected Padding ListViewItemPadding;
	protected static final int sSwipeLeft = -1;
	protected static final int sSwipeNO = 0;
	protected static final int sSwipeRight = 1;

	public void attachToItemView(View itemView) {
		try {
			this.itemView = itemView;
			this.importantIcon = (ImageView) itemView.findViewById(R.id.issue_listview_item_important_icon);
			this.urgencyIcon = (ImageView) itemView.findViewById(R.id.issue_listview_item_urgency_icon);
			this.issueTitle = (TextView) itemView.findViewById(R.id.issue_listview_item_title);
			this.issueDescription = (TextView) itemView.findViewById(R.id.issue_listview_item_description);
			this.moreDetails = (ImageView) itemView.findViewById(R.id.issue_listview_item_detail_button);
			this.deleteButton = (Button) itemView.findViewById(R.id.issue_listview_item_delete_button);
			itemView.setTag(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onClickMoreDetails(View.OnClickListener onClickEventHandler) {
		this.moreDetails.setOnClickListener(onClickEventHandler);
	}

	public void onClickDeleteButton(View.OnClickListener onClickEventHandler) {
		this.deleteButton.setOnClickListener(onClickEventHandler);
	}

	public void updateItemView(IssueListViewAdapter adapter, int position, Issue issue) {
		this.adapter = adapter;
		this.position = position;
		this.issueTitle.setText(issue.getTitle());
		this.issueDescription.setText(issue.getDescription());

		boolean hasSwipe = issue.getExtraTags().has("swipe");
		if (!hasSwipe) {
			this.getListViewItemPadding();
			this.deleteButton.setVisibility(View.GONE);
		} else {
			int swipeTag = issue.getExtraTags().getInt("swipe");
			if (sSwipeLeft == swipeTag) {
				this.deleteButton.setVisibility(View.VISIBLE);
				// this.itemView.setPadding(this.ListViewItemPadding.left - 100,
				// this.ListViewItemPadding.top,
				// this.ListViewItemPadding.right,
				// this.ListViewItemPadding.bottom);
			} else if (sSwipeRight == swipeTag) {
				this.deleteButton.setVisibility(View.GONE);

				// this.itemView.setPadding(this.ListViewItemPadding.left,
				// this.ListViewItemPadding.top,
				// this.ListViewItemPadding.right,
				// this.ListViewItemPadding.bottom);

				this.swipeNO();
			}
		}
	}

	private void getListViewItemPadding() {
		if (this.ListViewItemPadding == null) {
			int bottom = this.itemView.getPaddingBottom();
			int right = this.itemView.getPaddingRight();
			int top = this.itemView.getPaddingTop();
			int left = this.itemView.getPaddingLeft();
			this.ListViewItemPadding = new Padding(left, top, right, bottom);
		}
	}

	public Issue getItem() {
		return this.adapter.getItem(this.position);
	}

	public Context getContext() {
		return this.itemView.getContext();
	}

	public void notifyDataSetChanged() {
		this.adapter.notifyDataSetChanged();
	}

	public void swipeLeft() {
		this.getItem().getExtraTags().set("swipe", sSwipeLeft);
	}

	public void swipeRight() {
		this.getItem().getExtraTags().set("swipe", sSwipeRight);
	}

	protected void swipeNO() {
		this.getItem().getExtraTags().remove("swipe");
	}

	public void delete() {
		Issue item = this.getItem();
		IssueDataContext dc = new IssueDataContext(this.getContext());
		dc.deleteIssue(item.getId());
		this.adapter.remove(item);
	}
}
