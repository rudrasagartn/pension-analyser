package com.pfm.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Utils {

	private static final Logger log = LoggerFactory.getLogger(Utils.class);

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
			log.error("Exception occured parsing date ", e);
		}
		return date;
	}

}
