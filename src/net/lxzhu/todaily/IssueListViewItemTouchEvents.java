package net.lxzhu.todaily;

import net.lxzhu.todaily.util.ToastUtil;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

public class IssueListViewItemTouchEvents implements View.OnTouchListener {
	public static final int sMinimalMoveBuffer = 10;

	public IssueListViewItemTouchEvents(IssueListViewItemTag itemTag) {
		this.itemTag = itemTag;
		// this.detector = new GestureDetector(this.getActivity(), this);
	}

	private IssueListViewItemTag itemTag;
	private MotionEvent firstEvent;

	private Activity getActivity() {
		return (Activity) this.itemTag.itemView.getContext();
	}

	@SuppressLint("ClickableViewAccessibility")
	public boolean onTouch(View v, MotionEvent event) {

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			firstEvent = MotionEvent.obtain(event);
			break;
		case MotionEvent.ACTION_UP:
			onTap(event);
			firstEvent = null;
			break;
		case MotionEvent.ACTION_CANCEL:
			firstEvent = null;
			break;
		case MotionEvent.ACTION_MOVE:
			onMove(event);
			break;
		}
		return true;
	}

	public boolean onMove(MotionEvent e2) {
		boolean handled = false;
		if (this.firstEvent != null) {
			MotionEvent e1 = this.firstEvent;
			float dy = e2.getY() - e1.getY();
			float dx = e2.getX() - e1.getX();
			Log.d("IssueListViewTouchEvents", String.format("dy=%f;dx=%f", dy, dx));
			if (Math.abs(dx) < sMinimalMoveBuffer) {
				Log.d("IssueListViewTouchEvents", "dx is too small");
			} else if (DirectionUtils.isWest(dy, dx)) {
				Log.d("IssueListViewTouchEvents", "move to west");
				this.itemTag.swipeLeft();
				this.itemTag.notifyDataSetChanged();
				handled = true;

			} else if (DirectionUtils.isEast(dy, dx)) {
				Log.d("IssueListViewTouchEvents", "move to east");
				this.itemTag.swipeRight();
				this.itemTag.notifyDataSetChanged();
				handled = true;
			}
		}
		return handled;

	}

	public void onTap(MotionEvent event) {
		if (this.firstEvent != null) {
			float dy = event.getY() - firstEvent.getY();
			float dx = event.getX() - firstEvent.getX();
			double distance = Math.sqrt(dx * dx + dy * dy);
			if (distance < sMinimalMoveBuffer) {
				this.itemTag.itemView.performClick();
			}
		}
	}

	public boolean onDoubleTap(MotionEvent e) {
		ToastUtil.showText(this.getActivity(), "double tap");
		return true;
	}

}
