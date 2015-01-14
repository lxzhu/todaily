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
	private IssueListViewAdapter mIssueListViewAdapter;
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
		// this.mGestureDetector = new GestureDetector(this.getActivity(), new
		// GestureListener(this.mProgressBar));

		this.mIssueListViewAdapter = new IssueListViewAdapter(this.getActivity());
		this.reloadIssues();

		this.mIssueListView.setAdapter(this.mIssueListViewAdapter);
		this.mIssueListView.setClickable(true);
		this.mIssueListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> listview, View itemView, int position, long id) {
				//itemView.setSelected(true);
			}
		});
		
		return this.mFragmentView;

	}

	protected void reloadIssues() {
		IssueDataContext dc = new IssueDataContext(getActivity());
		List<Issue> issueList = dc.getIssue();
		this.mIssueListViewAdapter.clear();
		this.mIssueListViewAdapter.addAll(issueList);
		this.mIssueListViewAdapter.notifyDataSetChanged();
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
}
