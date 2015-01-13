package net.lxzhu.todaily;

import java.util.List;

import net.lxzhu.todaily.dao.Issue;
import net.lxzhu.todaily.dao.IssueDataContext;
import net.lxzhu.todaily.util.RequestCodes;
import net.lxzhu.todaily.util.ToastUtil;
import android.content.Context;
import android.content.Intent;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGestureListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentToday extends Fragment {

	private View mFragmentView;
	private ListView mIssueListView;
	private ListViewAdapter mListViewAdapter;
	private GestureOverlayView mGestureView;
	private ProgressBar mProgressBar;
	private GestureDetector mGestureDetector;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.mFragmentView = inflater.inflate(R.layout.fragment_today, container, false);
		this.mFragmentView.setTag(this);
		this.mIssueListView = (ListView) this.mFragmentView.findViewById(R.id.fragment_today_issue_list_view);
		// this.mIssueListView.setOnTouchListener(this);

		// this.mGestureView = (GestureOverlayView)
		// this.mGestureView.findViewById(R.id.fragment_today_gestureview);
		this.mProgressBar = (ProgressBar) this.mFragmentView.findViewById(R.id.fragment_today_progressbar);
		this.mGestureDetector = new GestureDetector(this.getActivity(), new GestureListener(this.mProgressBar));

		this.mListViewAdapter = new ListViewAdapter(this.getActivity());
		this.reloadIssues();

		this.mIssueListView.setAdapter(this.mListViewAdapter);
		this.mIssueListView.setClickable(true);
		this.mIssueListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> listview, View itemView, int position, long id) {
				itemView.setSelected(true);
			}
		});
		return this.mFragmentView;

	}

	protected void reloadIssues() {
		IssueDataContext dc = new IssueDataContext(getActivity());
		List<Issue> issueList = dc.getIssue();
		this.mListViewAdapter.clear();
		this.mListViewAdapter.addAll(issueList);
		this.mListViewAdapter.notifyDataSetChanged();
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		switch (requestCode) {
		case RequestCodes.RequestEditIssue:
			this.reloadIssues();
			break;
		case RequestCodes.RequestCreateIssue:
			this.reloadIssues();
			break;
		}
	}

	class ListViewAdapter extends ArrayAdapter<Issue> {

		public ListViewAdapter(Context context) {
			super(context, android.R.layout.simple_list_item_1);
		}

		public View getView(int position, View itemView, ViewGroup root) {
			ListViewItemTag tag = null;
			if (itemView == null) {
				itemView = getActivity().getLayoutInflater().inflate(R.layout.issue_listview_item, null);
				tag = new ListViewItemTag();
				tag.attachToItemView(itemView);
				tag.moreDetails.setOnClickListener(new MoreDetailsClickEventHandler(tag));
			} else {
				tag = (ListViewItemTag) itemView.getTag();
			}

			Issue issue = this.getItem(position);
			tag.updateItemView(position, issue);
			return itemView;
		}
	}

	/**
	 * 
	 * @author liangxiong
	 *
	 */
	class ListViewItemTag {
		public ImageView importantIcon;
		public ImageView urgencyIcon;
		public ImageView moreDetails;
		public TextView issueTitle;
		public TextView issueDescription;
		public View itemView;
		public int position;

		public void attachToItemView(View itemView) {
			this.itemView = itemView;
			this.importantIcon = (ImageView) itemView.findViewById(R.id.issue_listview_item_important_icon);
			this.urgencyIcon = (ImageView) itemView.findViewById(R.id.issue_listview_item_urgency_icon);
			this.issueTitle = (TextView) itemView.findViewById(R.id.issue_listview_item_title);
			this.issueDescription = (TextView) itemView.findViewById(R.id.issue_listview_item_description);
			this.moreDetails = (ImageView) itemView.findViewById(R.id.issue_listview_item_detail_button);
			itemView.setTag(this);
		}

		public void updateItemView(int position, Issue issue) {
			this.position = position;
			this.issueTitle.setText(issue.getTitle());
			this.issueDescription.setText(issue.getDescription());
		}
	}

	class MoreDetailsClickEventHandler implements View.OnClickListener {

		protected ListViewItemTag itemViewTag;

		public MoreDetailsClickEventHandler(ListViewItemTag itemViewTag) {
			this.itemViewTag = itemViewTag;
		}

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(getActivity().getBaseContext(), IssueDetailsActivity.class);
			getActivity().startActivityForResult(intent, RequestCodes.RequestEditIssue);
		}

	}

	public class GestureListener extends SimpleOnGestureListener {
		public GestureListener(ProgressBar progressBar) {
			this.progressBar = progressBar;
		}

		protected ProgressBar progressBar;
		protected float lastX = 0;
		protected float lastY = 0;
		protected float distanceX = 0;
		protected float distanceY = 0;

		// 用户轻触触摸屏，由1个MotionEvent ACTION_DOWN触发
		public boolean onDown(MotionEvent e) {

			return super.onDown(e);
		}

		/*
		 * 用户轻触触摸屏，尚未松开或拖动，由一个1个MotionEvent ACTION_DOWN触发
		 * 注意和onDown()的区别，强调的是没有松开或者拖动的状态
		 */
		public void onShowPress(MotionEvent e) {
			this.lastX = 0;
			this.lastY = 0;
			this.distanceX = 0;
			this.distanceY = 0;
			this.progressBar.setVisibility(View.VISIBLE);
			super.onShowPress(e);
		}

		// 用户（轻触触摸屏后）松开，由一个1个MotionEvent ACTION_UP触发
		public boolean onSingleTapUp(MotionEvent e) {
			this.progressBar.setVisibility(View.GONE);
			return super.onSingleTapUp(e);
		}

		// 用户按下触摸屏、快速移动后松开，由1个MotionEvent ACTION_DOWN, 多个ACTION_MOVE,
		// 1个ACTION_UP触发
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			return super.onFling(e1, e2, velocityX, velocityY);
		}

		// 用户按下触摸屏，并拖动，由1个MotionEvent ACTION_DOWN, 多个ACTION_MOVE触发
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			this.progressBar.getLayoutParams().height = (int) distanceY;
			// this.progressBar.setLayoutParams(new
			// LinearLayout.LayoutParams(distanceY,LinearLayout.LayoutParams.));
			return true;
		}

		// 用户长按触摸屏，由多个MotionEvent ACTION_DOWN触发
		public void onLongPress(MotionEvent e) {
			super.onLongPress(e);
		}
	}

}
