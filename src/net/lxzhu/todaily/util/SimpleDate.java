package net.lxzhu.todaily.util;

public class SimpleDate {
	protected long ticks;

	public static SimpleDate create(long ticks) {
		SimpleDate retObj = new SimpleDate();
		retObj.ticks = ticks;
		return retObj;
	}
	

}
