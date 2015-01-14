package net.lxzhu.todaily;

public class DirectionUtils {
	protected static final double sPI_OVER8 = Math.PI / 8;

	public static boolean isWest(double dy, double dx) {
		double angle = Math.atan2(dy, dx);
		return isWest(angle);
	}

	public static boolean isWest(double angle) {
		return (angle >= (7 * sPI_OVER8) && angle <= Math.PI) || (angle >= -Math.PI && angle <= (-7 * sPI_OVER8));
	}

	public static boolean isEast(double dy, double dx) {
		double angle = Math.atan2(dy, dx);
		return isEast(angle);
	}

	public static boolean isEast(double angle) {
		return angle >= -sPI_OVER8 && angle <= sPI_OVER8;
	}
}
