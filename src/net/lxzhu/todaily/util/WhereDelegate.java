package net.lxzhu.todaily.util;

public interface WhereDelegate<T> {
	public boolean exec(T item);
}
