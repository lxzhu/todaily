package net.lxzhu.todaily.gesture.swipe;

public enum SwipeDirection {
	Unknown(0), North(1), EastNorth(2), East(3), EastSouth(4), South(5), WestSouth(6), West(7), WestNorth(8);
	private final int direction;

	SwipeDirection(int dir) {
		this.direction = dir;
	}
	public int getDirection(){
		return this.direction;
	}

}
