package com.pfm.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class Utils {

	private Utils() {

	}

	public static Date getPreviousDays(int previousDays) {

		Date date = null;
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -previousDays);

		String pattern = "dd-MM-yyyy HH:mm:ss";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		try {
			date = simpleDateFormat.parse(simpleDateFormat.format(cal.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

}
