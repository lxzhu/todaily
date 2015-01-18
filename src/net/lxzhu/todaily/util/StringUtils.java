package net.lxzhu.todaily.util;

public class StringUtils {
	public static boolean isNullOrEmpty(String text) {
		String regex = "^(\\s)*$";
		return text == null || text.matches(regex);
	}

	public static String trimEnd(String text) {
		return text;
	}

	public static String trimStart(String text) {
		return text;
	}

	public static String trim(String text) {
		return trimEnd(trimStart(text));
	}
}
