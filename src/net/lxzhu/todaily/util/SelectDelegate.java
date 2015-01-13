package net.lxzhu.todaily.util;

public interface SelectDelegate<TElement, TOut> {
	public TOut exec(TElement data);
}
