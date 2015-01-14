package net.lxzhu.todaily.util;

public class Padding {
	public int left;
	public int right;
	public int top;
	public int bottom;

	public Padding() {
		this(0, 0, 0, 0);
	}

	public Padding(int left, int top, int right, int bottom) {
		this.left = left;
		this.right = right;
		this.top = top;
		this.bottom = bottom;
	}
}
