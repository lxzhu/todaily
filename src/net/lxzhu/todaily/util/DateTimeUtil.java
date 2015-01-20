package net.lxzhu.todaily.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtil {
	/**
	 * this method create Calendar from text with format "yyyy-MM-dd HH:mm:ss"
	 * 
	 * @param text
	 * @return
	 * @throws ParseException
	 */
	public static Calendar parse(String text) {
		Calendar calendar = null;
		if (null == text || text.trim() != "") {
			return calendar;
		}
		try {

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss", Locale.US);
			Date date = format.parse(text);
			calendar = Calendar.getInstance();
			calendar.setTime(date);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return calendar;
	}

	public static String format(Calendar calendar) {
		if (calendar == null) {
			return "";
		}
		Date date = calendar.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss", Locale.US);
		return format.format(date);
	}

	public static String formatDate(Calendar calendar) {
		if (calendar == null) {
			return "";
		}
		Date date = calendar.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		return format.format(date);
	}
	
}
