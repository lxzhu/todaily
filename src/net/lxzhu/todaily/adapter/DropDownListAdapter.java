package net.lxzhu.todaily.adapter;

import java.util.List;

import net.lxzhu.todaily.dto.DropDownItem;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class DropDownListAdapter<T extends DropDownItem> extends BaseAdapter implements SpinnerAdapter {

	protected int itemViewResourceId;
	protected Context context;
	protected List<T> items;

	public DropDownListAdapter(Context context, List<T> items) {
		this(context, items, 0);
	}

	public DropDownListAdapter(Context context, List<T> items, int itemViewResourceId) {
		this.context = context;
		this.items = items;
		this.itemViewResourceId = itemViewResourceId;
	}

	@Override
	public int getCount() {
		return this.items.size();
	}

	@Override
	public T getItem(int position) {
		return (T) this.items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView itemView = null;
		if (convertView != null && convertView instanceof TextView) {
			itemView = (TextView) convertView;
		} else {
			itemView = new TextView(this.context);
			
			T item = getItem(position);
			String text = "";
			if (item != null) {
				text = item.getText();
			}
			itemView.setText(text);
		}
		return itemView;
	}

}
