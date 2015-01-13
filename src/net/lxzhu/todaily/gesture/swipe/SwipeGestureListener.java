package net.lxzhu.todaily.gesture.swipe;

public interface SwipeGestureListener {
	void onSwipeStart(SwipeGestureEvent evt);

	void onSwipe(SwipeGestureEvent evt);

	void onSwipeStop(SwipeGestureEvent evt);
}
