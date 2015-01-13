package net.lxzhu.todaily.util;

public interface ActionDelegate<T> {
	public void exec(T data);
}
