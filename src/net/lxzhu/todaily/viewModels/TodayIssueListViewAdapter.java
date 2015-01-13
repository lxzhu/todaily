package net.lxzhu.todaily.viewModels;

import net.lxzhu.todaily.dao.Issue;
import android.content.Context;
import android.widget.ArrayAdapter;

public class TodayIssueListViewAdapter extends ArrayAdapter<Issue> {

	public TodayIssueListViewAdapter(Context context) {
		super(context, android.R.layout.simple_list_item_1);

	}
}
