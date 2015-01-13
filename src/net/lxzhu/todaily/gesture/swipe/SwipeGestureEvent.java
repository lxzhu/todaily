package net.lxzhu.todaily.gesture.swipe;

public class SwipeGestureEvent {

	protected float distanceX;
	protected float distanceY;
	protected float velocityX;
	protected float velocityY;
	protected float duration;

	public float getDistanceX() {
		return distanceX;
	}

	public void setDistanceX(float distanceX) {
		this.distanceX = distanceX;
	}

	public float getDistanceY() {
		return distanceY;
	}

	public void setDistanceY(float distanceY) {
		this.distanceY = distanceY;
	}

	public float getVelocityX() {
		return velocityX;
	}

	public void setVelocityX(float velocityX) {
		this.velocityX = velocityX;
	}

	public float getVelocityY() {
		return velocityY;
	}

	public void setVelocityY(float velocityY) {
		this.velocityY = velocityY;
	}

	public double getDistance() {
		double squre = this.distanceX * this.distanceX + this.distanceY * this.distanceX;
		return Math.sqrt(squre);
	}

	public double getVelcity() {
		double squre = this.velocityX * this.velocityX + this.velocityY * this.velocityY;
		return Math.sqrt(squre);
	}

	public float getDuration() {
		return duration;
	}

	public void setDuration(float duration) {
		this.duration = duration;
	}

	public SwipeDirection getDirection() {
		float dx = this.distanceX;
		float dy = this.distanceY * -1;
		if (dx == 0) {
			if (dy > 0)
				return SwipeDirection.North;
			else if (dy == 0)
				return SwipeDirection.Unknown;
			else
				return SwipeDirection.South;
		} else {
			double unit = Math.PI / 8;
			double angle = Math.atan2(dy, dx) / unit;
			if (angle >= -1 && angle <= 1) {
				return SwipeDirection.East;
			} else if (angle > 1 && angle <= 3) {
				return SwipeDirection.EastNorth;
			} else if (angle > 3 && angle <= 5) {
				return SwipeDirection.North;
			} else if (angle > 5 && angle <= 7) {
				return SwipeDirection.WestNorth;
			} else if (angle > 7 || angle <= -7) {
				return SwipeDirection.West;
			} else if (angle > -7 && angle <= -5) {
				return SwipeDirection.WestSouth;
			} else if (angle >= 5 && angle <= -3) {
				return SwipeDirection.South;
			} else if (angle > 3 && angle < -1) {
				return SwipeDirection.EastSouth;
			}
		}
		return SwipeDirection.Unknown;
	}

}
