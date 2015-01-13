package net.lxzhu.todaily.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Linq<TElement> {
	protected Iterator<TElement> iterator;

	public Linq(TElement[] collection) {
		List<TElement> list = new ArrayList<TElement>();
		for (TElement item : collection) {
			list.add(item);
		}
		this.iterator = list.iterator();
	}

	public Linq(Iterator<TElement> iterator) {
		this.iterator = iterator;
	}

	public void each(ActionDelegate<TElement> action) {
		while (iterator.hasNext()) {
			TElement item = iterator.next();
			try {
				action.exec(item);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public <T> Linq<T> select(final SelectDelegate<TElement, T> select) {
		Iterator<T> retIterator = new Iterator<T>() {
			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}

			@Override
			public T next() {
				TElement src = iterator.next();
				T dest = select.exec(src);
				return dest;
			}

			@Override
			public void remove() {
				iterator.remove();
			}
		};
		Linq<T> retObjs = new Linq<T>(retIterator);
		return retObjs;
	}

	public Linq<TElement> where(final WhereDelegate<TElement> where) {
		Iterator<TElement> retIterator = new Iterator<TElement>() {
			TElement nextItem;

			@Override
			public boolean hasNext() {
				return getNextItem();
			}

			@Override
			public TElement next() {
				TElement retNextItem = null;
				// if nextItem is not null, it means, getNextItem invoked and
				// waiting invocation to next()
				// if nextItem is null, it means, it reach the end or next was
				// called, so we invoke getNextItem() for it.
				if (nextItem != null || getNextItem()) {
					retNextItem = nextItem;
				}
				nextItem = null;
				return retNextItem;
			}

			@Override
			public void remove() {
				iterator.remove();
			}

			boolean getNextItem() {
				while (iterator.hasNext()) {
					nextItem = iterator.next();
					if (where.exec(nextItem)) {
						return true;
					}
				}
				return false;
			}
		};
		return new Linq<TElement>(retIterator);
	}

	public boolean any(final WhereDelegate<TElement> where) {
		return false;
	}

	public TElement[] toArray(TElement[] items) {
		return this.toList().toArray(items);
	}

	public List<TElement> toList() {
		List<TElement> retList = new ArrayList<TElement>();
		while (this.iterator.hasNext()) {
			retList.add(this.iterator.next());
		}
		return retList;
	}
}
