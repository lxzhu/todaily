package net.lxzhu.todaily.gesture.swipe;

import java.util.Calendar;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;

public class SwipeGestureDetector implements OnTouchListener {
	protected Context mContext;
	protected float distanceX = 0;
	protected float distanceY = 0;
	protected float lastX = 0;
	protected float lastY = 0;
	protected SwipeGestureListener mListener;
	protected ViewConfiguration viewConfiguration;
	protected Calendar startTime;
	protected Calendar stopTime;

	public SwipeGestureDetector(Context context, SwipeGestureListener l) {
		this.mContext = context;
		this.mListener = l;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			setup(event);
			break;
		case MotionEvent.ACTION_MOVE:
			accumulate(event);
			this.onSwipe(false);
			break;
		case MotionEvent.ACTION_UP:
			accumulate(event);
			this.onSwipe(true);
			break;
		case MotionEvent.ACTION_CANCEL:
			tearDown(event);
			break;
		}
		return true;
	}

	protected void setup(MotionEvent event) {
		this.lastX = event.getX();
		this.lastY = event.getY();
		this.distanceX = 0;
		this.distanceY = 0;
		this.startTime = Calendar.getInstance();
	}

	protected void accumulate(MotionEvent event) {
		float deltaX;
		float deltaY;
		deltaX = event.getX() - lastX;
		deltaY = event.getY() - lastY;
		distanceX += deltaX;
		distanceY += deltaY;
		this.lastX = event.getX();
		this.lastY = event.getY();
	}

	protected void onSwipe(boolean isActionUp) {
		if (this.mListener != null) {
			viewConfiguration = ViewConfiguration.get(this.mContext);
			if (this.distanceX > viewConfiguration.getScaledTouchSlop()
					|| this.distanceY > viewConfiguration.getScaledTouchSlop()) {
				long duration = this.getDuration();
				SwipeGestureEvent evt = new SwipeGestureEvent();
				evt.setDistanceX(this.distanceX);
				evt.setDistanceY(this.distanceY);
				evt.setDuration(duration);
				evt.setVelocityX(this.distanceX * 1000 / duration);
				evt.setVelocityY(this.distanceY * 1000 / duration);
				if (isActionUp) {
					this.mListener.onSwipe(evt);
				} else {
					this.mListener.onSwipeStop(evt);
				}
			}
		}
	}

	protected void tearDown(MotionEvent event) {
		this.distanceX = 0;
		this.distanceY = 0;
		this.lastX = 0;
		this.lastY = 0;
		this.stopTime = Calendar.getInstance();
	}

	protected long getDuration() {
		return this.stopTime.getTimeInMillis() - this.startTime.getTimeInMillis();
	}

}
