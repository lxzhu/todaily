package net.lxzhu.todaily.util;

public class ArrayUtil {
	public static <T> int indexOf(T[] items, T item) {
		int retIndex = -1;
		for (int index = 0; index < items.length; index++) {
			T curItem = items[index];
			if (curItem == item) {
				retIndex = index;
				break;
			}
		}
		return retIndex;
	}

	public static int indexOf(int[] items, int item) {
		int retIndex = -1;
		for (int index = 0; index < items.length; index++) {
			int curItem = items[index];
			if (curItem == item) {
				retIndex = index;
				break;
			}
		}
		return retIndex;
	}

	public static <T> boolean contains(T[] items, T item) {
		return indexOf(items, item) >= 0;
	}

	public static boolean contains(int[] items, int item) {
		return indexOf(items, item) >= 0;
	}
}
