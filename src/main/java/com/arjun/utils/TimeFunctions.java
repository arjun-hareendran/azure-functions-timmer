package com.arjun.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TimeFunctions {

	public String getTimepath() {
		LocalDateTime now = LocalDateTime.now();
		NumberFormat f = new DecimalFormat("00");

		List<String> dirname = new ArrayList<String>();
		List<String> filename = new ArrayList<String>();

		dirname.add(String.valueOf(now.getYear()));
		dirname.add(String.valueOf(f.format(now.getMonthValue())));
		dirname.add(String.valueOf(f.format(now.getDayOfMonth())));

		filename.add(String.valueOf(f.format(now.getHour())));
		filename.add(String.valueOf(f.format(now.getMinute())));
		filename.add(String.valueOf(f.format(now.getSecond())));

		return String.join("-", dirname) + "/" + String.join("-", filename);
	}

	public String getYear() {
		LocalDateTime now = LocalDateTime.now();
		NumberFormat f = new DecimalFormat("00");

		List<String> yearpart = new ArrayList<String>();

		yearpart.add(String.valueOf(now.getYear()));
		yearpart.add(String.valueOf(f.format(now.getMonthValue())));
		yearpart.add(String.valueOf(f.format(now.getDayOfMonth())));

		return String.join("-", yearpart);
	}

	public String getTime() {
		LocalDateTime now = LocalDateTime.now();
		NumberFormat f = new DecimalFormat("00");

		List<String> timepart = new ArrayList<String>();

		timepart.add(String.valueOf(f.format(now.getHour())));
		timepart.add(String.valueOf(f.format(now.getMinute())));
		timepart.add(String.valueOf(f.format(now.getSecond())));

		return String.join("-", timepart);
	}

	public String uniquePartition() {

		return getYear() + getTime();
	}

}
