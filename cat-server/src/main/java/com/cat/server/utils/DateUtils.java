package com.cat.server.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static java.util.Calendar.*;

/**
 * 日期工具类
 * 优先使用org.apache.commons.lang3.time#DateFormatUtils
 */
public class DateUtils {

	/** 
	 * HH:mm 格式
	 */
	public static final String PATTERN_HH_MM = "HH:mm";

	/** 
	 * yyyyMMdd 格式
	 */
	public static final  String PATTERN_YYYYMMDD = "yyyyMMdd";

	/** 
	 * yyyy-MM-dd 格式
	 */
	public static final  String PATTERN_YYYY_MM_DD = "yyyy-MM-dd";

	/** 
	 * yyyyMMddHHmm 格式
	 */
	public static final  String PATTERN_YYYYMMDDHHMM = "yyyyMMddHHmm";

	/** 
	 * yyyy-MM-dd HH:mm:ss 格式
	 */
	public static final  String PATTERN_NORMAL = "yyyy-MM-dd HH:mm:ss";

	/** 
	 * yyyyMMddHHmmss 格式
	 */
	public static final  String PATTERN_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	
	/**
	 * EEE MMM dd HH:mm:ss zzz yyyy 系统默认格式
	 */
	public static final String SYSTEM_DEFAULT = "EEE MMM dd HH:mm:ss zzz yyyy";
	
	

	/**
	 * 比较两个时间是不是同一周
	 * 
	 * @param time1
	 * @param time2
	 * @param firstDayOfWeek
	 *            周的第一天设置值，{@see Calendar#DAY_OF_WEEK}
	 * @return
	 */
	public static boolean isSameWeek(Date time1, Date time2, final int firstDayOfWeek) {
		return calcWeekIntervals(time1, time2, firstDayOfWeek) == 0;
	}

	private static final List<Integer> DAY_IN_WEEK_LIST = new ArrayList<Integer>(Arrays.asList(Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY, Calendar.SATURDAY, Calendar.SUNDAY));

	/**
	 * 计算两个时间相隔的周数
	 * 
	 * @param time1
	 * @param time2
	 * @param firstDayOfWeek
	 *            firstDayOfWeek 周的第一天设置值，{@see Calendar#DAY_OF_WEEK}
	 * @return
	 */
	public static int calcWeekIntervals(final Date time1, final Date time2, final int firstDayOfWeek) {
		int firstDayIndex = DAY_IN_WEEK_LIST.indexOf(firstDayOfWeek);

		Calendar compareCalendar = Calendar.getInstance();

		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(time1);

		compareCalendar.setTime(time1);

		int index1 = DAY_IN_WEEK_LIST.indexOf(calendar1.get(Calendar.DAY_OF_WEEK));

		int diff1 = index1 - firstDayIndex;
		if (diff1 < 0) {
			compareCalendar.add(Calendar.DAY_OF_YEAR, DAY_IN_WEEK_LIST.size() - Math.abs(diff1));
		} else {
			compareCalendar.add(Calendar.DAY_OF_YEAR, -Math.abs(diff1));
		}
		compareCalendar.set(Calendar.HOUR_OF_DAY, 0);
		compareCalendar.set(Calendar.MINUTE, 0);
		compareCalendar.set(Calendar.SECOND, 0);
		compareCalendar.set(Calendar.MILLISECOND, 0);

		calendar1.setTime(compareCalendar.getTime());
		if (calendar1.before(compareCalendar)) {
			calendar1.add(Calendar.DAY_OF_YEAR, -DAY_IN_WEEK_LIST.size());
		}

		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(time2);

		compareCalendar.setTime(time2);

		int index2 = DAY_IN_WEEK_LIST.indexOf(calendar2.get(Calendar.DAY_OF_WEEK));

		int diff2 = index2 - firstDayIndex;
		if (diff2 < 0) {
			compareCalendar.add(Calendar.DAY_OF_YEAR, DAY_IN_WEEK_LIST.size() - Math.abs(diff2));
		} else {
			compareCalendar.add(Calendar.DAY_OF_YEAR, -Math.abs(diff2));
		}
		compareCalendar.set(Calendar.HOUR_OF_DAY, 0);
		compareCalendar.set(Calendar.MINUTE, 0);
		compareCalendar.set(Calendar.SECOND, 0);
		compareCalendar.set(Calendar.MILLISECOND, 0);

		calendar2.setTime(compareCalendar.getTime());
		if (calendar2.before(compareCalendar)) {
			calendar2.add(Calendar.DAY_OF_YEAR, -DAY_IN_WEEK_LIST.size());
		}

		return (int) Math.abs((calendar2.getTime().getTime() - calendar1.getTime().getTime()) / (DAY_IN_WEEK_LIST.size() * 24 * 3600 * 1000));
	}

	/**
	 * startTime的毫秒数
	 * 
	 * @param timeContext
	 *            通过解析时间串 eg 18:00:00得到的时间上下文
	 * @return
	 */

	/**
	 * 当前时间 距离 当前日期的特殊时间点 的毫秒数
	 * 
	 * @param time
	 * @param hour
	 * @param minute
	 * @param second
	 * @return
	 */
	public static long getTodayOffsetMillis(Date time, int hour, int minute, int second) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);

		long miliis = cal.getTimeInMillis();

		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		cal.set(Calendar.MILLISECOND, 0);

		return Math.max(miliis - cal.getTimeInMillis(), 0);
	}

	/**
	 * 把 时间点字符串 转化成为 当前日期(年月日)的 时间
	 * 
	 * @param startTime
	 *            eg 18:00:00
	 * @return
	 */
	public static Date getTimeByString(String startTime) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());

		String[] timeArray = startTime.trim().split(":");
		if (timeArray.length >= 1) {
			int hour = Integer.valueOf(timeArray[0]);
			cal.set(Calendar.HOUR_OF_DAY, hour > 24 ? 24 : hour);
		}

		if (timeArray.length >= 2) {
			int min = Integer.valueOf(timeArray[1]);
			cal.set(Calendar.MINUTE, min > 60 ? 60 : min);
		}

		if (timeArray.length >= 3) {
			int sec = Integer.valueOf(timeArray[2]);
			cal.set(Calendar.SECOND, sec > 60 ? 60 : sec);
		}

		cal.set(Calendar.MILLISECOND, 0);

		return new Date(cal.getTimeInMillis());
	}

	/**
	 * 判断两个时间是不是同一个月
	 * 
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static boolean isSameMonth(Date time1, Date time2) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(time1);
		int year1 = cal.get(Calendar.YEAR);
		int month1 = cal.get(Calendar.MONTH);

		cal.setTime(time2);
		int year2 = cal.get(Calendar.YEAR);
		int month2 = cal.get(Calendar.MONTH);

		return year1 == year2 && month1 == month2;
	}

	/**
	 * 当前时间是否在起始时间和终止时间之间
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static boolean isBetweenThe(Date startDate, Date endDate) {
		Date now = new Date();
		return startDate.before(now) && now.before(endDate);
	}

//	/**
//	 * 当前日期是否在起始日期的周期日期内， 规则：（当前日期 - startDate）天数 % periodDays == 0 则在周期内
//	 * 
//	 * @param startDate
//	 * @param periodDays
//	 * @return
//	 */
//	public static boolean isInPeriodDays(Date startDate, int periodDays) {
//		Date now = new Date();
//		if (now.after(startDate)) {
//			return false;
//		}
//
//		int intervalDays = DateUtils.calc2DateTDOADays(startDate, now);
//		return intervalDays % periodDays == 0;
//	}

	/**
	 * 获取周的第一天
	 * 
	 * @param firstDayOfWeek
	 *            周的第一天设置值，{@link Calendar#DAY_OF_WEEK}
	 * @param time
	 *            指定时间，为 null 代表当前时间
	 * @return {@link Date} 周的第一天
	 */
	public static Date firstTimeOfWeek(int firstDayOfWeek, Date time) {
		Calendar cal = Calendar.getInstance();
		if (time != null) {
			cal.setTime(time);
		}
		cal.setFirstDayOfWeek(firstDayOfWeek);
		int day = cal.get(DAY_OF_WEEK);

		if (day == firstDayOfWeek) {
			day = 0;
		} else if (day < firstDayOfWeek) {
			day = day + (7 - firstDayOfWeek);
		} else if (day > firstDayOfWeek) {
			day = day - firstDayOfWeek;
		}

		cal.set(HOUR_OF_DAY, 0);
		cal.set(MINUTE, 0);
		cal.set(SECOND, 0);
		cal.set(MILLISECOND, 0);

		cal.add(DATE, -day);
		return cal.getTime();
	}

	/**
	 * 检查指定日期是否今天(使用系统时间)
	 * 
	 * @param date
	 *            被检查的日期
	 * @return
	 */
	public static boolean isToday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.add(DATE, 1);
		cal.set(HOUR_OF_DAY, 0);
		cal.set(MINUTE, 0);
		cal.set(SECOND, 0);
		cal.set(MILLISECOND, 0);
		Date end = cal.getTime(); // 明天的开始
		cal.add(MILLISECOND, -1);
		cal.add(DATE, -1);
		Date start = cal.getTime(); // 昨天的结束
		return date.after(start) && date.before(end);
	}

	/**
	 * 判断传进来的时间戳和系统当前时间是否同一天
	 *
	 * @param timestamp 时间戳（毫秒）
	 * @return
	 */
	public static boolean isSameDay(long timestamp) {
		Date date = new Date(timestamp);
		LocalDate now = LocalDate.now();
		LocalDate other = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return now.getDayOfYear() == other.getDayOfYear();
	}

	/**
	 * 两个时间戳（毫秒）是否同一天
	 *
	 * @param timestamp1
	 * @param timestamp2
	 * @return
	 */
	public static boolean isSameDay(long timestamp1, long timestamp2) {
		Date date = new Date(timestamp1);
		Date other = new Date(timestamp2);
		LocalDate ld1 = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate ld2 = other.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return ld1.getDayOfYear() == ld2.getDayOfYear();
	}

	/**
	 * 日期转换成字符串格式
	 * 
	 * @param theDate
	 *            待转换的日期
	 * @param datePattern
	 *            日期格式
	 * @return 日期字符串
	 */
	public static String date2String(Date theDate, String datePattern) {
		if (theDate == null) {
			return "";
		}

		DateFormat format = new SimpleDateFormat(datePattern);
		try {
			return format.format(theDate);
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 字符串转换成日期格式
	 * 
	 * @param dateString
	 *            待转换的日期字符串
	 * @param datePattern
	 *            日期格式
	 * @return {@link Date} 转换后的日期
	 */
	public static Date string2Date(String dateString, String datePattern) {
		if (dateString == null || dateString.trim().isEmpty()) {
			return null;
		}

		DateFormat format = new SimpleDateFormat(datePattern, Locale.UK);
		try {
			return format.parse(dateString);
		} catch (ParseException e) {
			throw new RuntimeException(String.format("[%s] -> [%s]", dateString, datePattern), e);
		}
	}
	
	/**
	 * 把秒数转换成把毫秒数
	 * 
	 * @param seconds
	 *            秒数的数组
	 * @return {@link Long} 毫秒数
	 */
	public static long toMillisSecond(long... seconds) {
		long millis = 0L;
		if (seconds != null && seconds.length > 0) {
			for (long time : seconds) {
				millis += (time * 1000);
			}
		}
		return millis;
	}
//
//	/**
//	 * 把毫秒数转换成把秒数
//	 * 
//	 * @param seconds
//	 *            毫秒数的数组
//	 * @return {@link Long} 毫秒数
//	 */
//	public static long toSecond(long... millis) {
//		long second = 0L;
//		if (millis != null && millis.length > 0) {
//			for (long time : millis) {
//				second += (time / ONE_SECOND_MILLISECOND);
//			}
//		}
//		return second;
//	}

	/**
	 * 修改日期
	 * 
	 * @param theDate
	 *            待修改的日期
	 * @param addDays
	 *            加减的天数
	 * @param hour
	 *            设置的小时
	 * @param minute
	 *            设置的分
	 * @param second
	 *            设置的秒
	 * @return 修改后的日期
	 */
	public static Date changeDateTime(Date theDate, int addDays, int hour, int minute, int second) {
		if (theDate == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(theDate);

		cal.add(DAY_OF_MONTH, addDays);
		cal.set(Calendar.MILLISECOND, 0);

		if (hour >= 0 && hour <= 24) {
			cal.set(HOUR_OF_DAY, hour);
		}
		if (minute >= 0 && minute <= 60) {
			cal.set(MINUTE, minute);
		}
		if (second >= 0 && second <= 60) {
			cal.set(SECOND, second);
		}

		return cal.getTime();
	}

	public static Date add(Date theDate, int addHours, int addMinutes, int addSecond) {
		if (theDate == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(theDate);

		cal.add(HOUR_OF_DAY, addHours);
		cal.add(MINUTE, addMinutes);
		cal.add(SECOND, addSecond);

		return cal.getTime();
	}

	public static Date add(Date theDate, int days, int addHours, int addMinutes, int addSecond) {
		if (theDate == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(theDate);

		cal.add(Calendar.DATE, days);
		cal.add(HOUR_OF_DAY, addHours);
		cal.add(MINUTE, addMinutes);
		cal.add(SECOND, addSecond);

		return cal.getTime();
	}

	/**
	 * 取得星期几
	 * 
	 * @param theDate
	 * @return
	 */
	public static int dayOfWeek(Date theDate) {
		if (theDate == null) {
			return -1;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(theDate);

		return cal.get(DAY_OF_WEEK);
	}

	/**
	 * 获得某一时间的0点
	 * 
	 * @param theDate
	 *            需要计算的时间
	 */
	public static Date getDate0AM(Date theDate) {
		if (theDate == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(theDate);
		return new GregorianCalendar(cal.get(YEAR), cal.get(MONTH), cal.get(DAY_OF_MONTH)).getTime();
	}
	
	/**
	 * 获取某一时间的整点或者半点
	 * @param date
	 * @return
	 */
	public static Date getTimePoint(Date date) {
		Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	cal.set(Calendar.MINUTE, 30);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		if (cal.getTimeInMillis() < date.getTime()) {
			cal.set(Calendar.HOUR, cal.get(Calendar.HOUR) + 1);
			cal.set(Calendar.MINUTE, 00);
		}
		return cal.getTime();
	}

	/**
	 * 获得某一时间的4点
	 * 
	 * @param theDate
	 *            需要计算的时间
	 */
	public static Date getDate4AM(Date theDate) {
		if (theDate == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(theDate);
		return new GregorianCalendar(cal.get(YEAR), cal.get(MONTH), cal.get(DAY_OF_MONTH), 4, 0).getTime();
	}

//	/**
//	 * 获得某一时间的下一个0点
//	 * 
//	 * @param theDate
//	 *            需要计算的时间
//	 */
//	public static Date getNextDay0AM(Date theDate) {
//		if (theDate == null) {
//			return null;
//		}
//		Calendar cal = Calendar.getInstance();
//		cal.setTimeInMillis(theDate.getTime() + ONE_DAY_MILLISECOND);
//		return new GregorianCalendar(cal.get(YEAR), cal.get(MONTH), cal.get(DAY_OF_MONTH)).getTime();
//	}

//	/**
//	 * 获取指定时间的下一天
//	 * 
//	 * @param time
//	 * @return
//	 */
//	public static Date getNextDaysTime(String time) {
//		Date targetTime = getTimeByString(time);
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTimeInMillis(targetTime.getTime() + ONE_DAY_MILLISECOND);
//		return new Date(calendar.getTimeInMillis());
//	}

//	/**
//	 * 获得指定日期的23点59分59秒的时间
//	 * 
//	 * @param theDate
//	 *            需要计算的时间
//	 */
//	public static Date getThisDay2359PM(Date theDate) {
//		if (theDate == null) {
//			return null;
//		}
//
//		Calendar cal = Calendar.getInstance();
//		long millis = theDate.getTime() + ONE_DAY_MILLISECOND - ONE_SECOND_MILLISECOND;
//		cal.setTimeInMillis(millis);
//		Date date = new GregorianCalendar(cal.get(YEAR), cal.get(MONTH), cal.get(DAY_OF_MONTH)).getTime();
//		return new Date(date.getTime() - ONE_SECOND_MILLISECOND);
//	}

//	/**
//	 * 计算2个时间相差的天数,这个方法算的是2个零点时间的绝对时间(天数)
//	 * 
//	 * @param startDate
//	 *            起始时间
//	 * @param endDate
//	 *            结束时间
//	 */
//	public static int calc2DateTDOADays(Date startDate, Date endDate) {
//		if (startDate == null || endDate == null) {
//			return 0;
//		}
//		Date startDate0AM = getDate0AM(startDate);
//		Date endDate0AM = getDate0AM(endDate);
//		long v1 = startDate0AM.getTime() - endDate0AM.getTime();
//
//		BigDecimal bd1 = new BigDecimal(Math.abs(v1));
//		BigDecimal bd2 = new BigDecimal(ONE_DAY_MILLISECOND);
//
//		int days = (int) bd1.divide(bd2, 0, BigDecimal.ROUND_UP).doubleValue();
//		return days;
//	}

//	/**
//	 * 获得指定时间的下个周一的00:00:00的时间
//	 * 
//	 * @param date
//	 *            指定的时间
//	 * @return {@link Date} 周一的00:00:00的时间
//	 */
//	public static Date getNextMonday(Date date) {
//		if (date == null) {
//			return null;
//		}
//
//		// 本周周一
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(DateUtils.getDate0AM(date));
//		cal.set(DAY_OF_WEEK, MONDAY);
//
//		Calendar nextMondayCal = Calendar.getInstance();
//		nextMondayCal.setTimeInMillis(cal.getTimeInMillis() + ONE_DAY_MILLISECOND * 7);
//		return nextMondayCal.getTime();
//	}

	/**
	 * 获得获得改变后的时间
	 * 
	 * @param addDay
	 *            增加的天数(减少天数, 则传负数)
	 * @param to0AM
	 *            是否取0点时间
	 * @return
	 */
	public static Date add(int addDay, boolean to0AM) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(DATE, addDay);
		Date time = calendar.getTime();
		return to0AM ? getDate0AM(time) : time;
	}

	/**
	 * 计算两个时间之间相差的天
	 * 
	 * @param time1
	 *            时间1
	 * @param time2
	 *            时间2
	 * @param borderHour
	 *            以这个小时数区分天
	 * @param borderMinute
	 *            以这个分钟数区分天
	 * @param borderSecond
	 *            以这个秒数区分天
	 * @return
	 */
	public static int calcDiffTimezone(Date time1, Date time2, int borderHour, int borderMinute, int borderSecond) {
		Date borderTime1 = DateUtils.changeDateTime(time1, 0, borderHour, borderMinute, borderSecond);
		if (time1.compareTo(borderTime1) < 0) {
			borderTime1 = DateUtils.changeDateTime(time1, -1, borderHour, borderMinute, borderSecond);
		}

		Date borderTime2 = DateUtils.changeDateTime(time2, 0, borderHour, borderMinute, borderSecond);
		if (time2.compareTo(borderTime2) < 0) {
			borderTime2 = DateUtils.changeDateTime(time2, -1, borderHour, borderMinute, borderSecond);
		}

		return (int) Math.abs((borderTime2.getTime() - borderTime1.getTime()) / (24 * 3600 * 1000L));
	}

	/**
	 * 获取指定时间是星期几(星期一为1,星期天为7)
	 * 
	 * @param time
	 * @return
	 */
	public static int getWeekDay(Date time) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		int weekDay = cal.get(Calendar.DAY_OF_WEEK);
		if (weekDay == Calendar.SUNDAY) {
			return 7;
		}
		return weekDay - 1;
	}

	/**
	 * 获取边界时间的0点
	 * 
	 * @param time
	 * @param borderHour
	 *            以这个小时数区分天
	 * @param borderMinute
	 *            以这个分钟数区分天
	 * @param borderSecond
	 *            以这个秒数区分天
	 * @return
	 */
	public static Date getBorderTime(Date time, int borderHour, int borderMinute, int borderSecond) {
		Date borderTime = DateUtils.changeDateTime(time, 0, borderHour, borderMinute, borderSecond);
		if (time.compareTo(borderTime) < 0) {
			borderTime = DateUtils.changeDateTime(time, -1, borderHour, borderMinute, borderSecond);
		}
		return borderTime;
	}

	/**
	 * 获取次月第一天的0点
	 * 
	 * @param time
	 * @return
	 */
	public static Date getAMTimeOfNextMonth(Date time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(HOUR_OF_DAY, 0);
		calendar.set(MINUTE, 0);
		calendar.set(SECOND, 0);
		calendar.add(Calendar.MONTH, 1);
		return calendar.getTime();
	}

	/**
	 * 计算两个日期相差的月数 (从 time1 所在的月 开始计数 1, 每后移一月+1, 直到与time2 是同一个月, 这里假设time1<time2,方法内部会自动取小的时间开始移动)
	 * 
	 * @param time1
	 * @param time2
	 * @return time1,time2 所跨越的月数
	 */
	public static int getDistanceOfMonth(Date time1, Date time2) {
		Date startTime = time1.compareTo(time2) <= 0 ? time1 : time2;
		Date endTime = startTime == time1 ? time2 : time1;

		Calendar moveCal = Calendar.getInstance();
		moveCal.setTime(startTime);

		moveCal.set(Calendar.MILLISECOND, 0);
		moveCal.set(Calendar.HOUR_OF_DAY, 0);
		moveCal.set(Calendar.MINUTE, 0);
		moveCal.set(Calendar.SECOND, 0);
		moveCal.set(Calendar.DAY_OF_MONTH, 1);// 设置到 当月的1号0点

		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endTime);

		endCal.set(Calendar.MILLISECOND, 0);
		endCal.set(Calendar.HOUR_OF_DAY, 0);
		endCal.set(Calendar.MINUTE, 0);
		endCal.set(Calendar.SECOND, 0);
		endCal.set(Calendar.DAY_OF_MONTH, 1);// 设置到 当月的1号0点

		int distances = 0;

		while (moveCal.compareTo(endCal) < 0) {
			moveCal.add(Calendar.MONTH, 1);
			distances++;
		}

		return distances;
	}

	/**
	 * 获取utc时间相对0时区(本初子午线)的偏移毫秒数 例: 北京时间时区为东8区(时区标识:UTC+8)相对0时区快8小时(28800000毫秒),有的地区实行夏令制度时区标识有 UTC+9:30的
	 * 
	 * @return
	 */
	public static int getUtcTimeoffsetMillis() {
		return TimeZone.getDefault().getOffset(0);
	}

	/**
	 * 格式化日期
	 * 
	 * @param dateTime
	 * @param pattern
	 * @return
	 */
	public static String formter(long dateTime, String pattern) {
		DateFormat sFormat = new SimpleDateFormat(pattern);
		return sFormat.format(new Date(dateTime));
	}

	/**
	 * 格式化日期
	 * 
	 * @param dateTime
	 * @return
	 */
	public static String formter(Date dateTime) {
		DateFormat sFormat = new SimpleDateFormat(PATTERN_NORMAL);
		return sFormat.format(dateTime);
	}

	/**
	 * 计算当前周期开始的时间
	 * 
	 * @param startingTime
	 * @param periodTime
	 * @return
	 */
	public static long calPeriod(long startingTime, long periodTime) {
		long nowTime = System.currentTimeMillis();
		if (startingTime == 0 || periodTime == 0) {
			return nowTime;
		}
		long startTime = nowTime - startingTime;
		long periodNum = startTime / periodTime;
		return startingTime + periodNum * periodTime;
	}

	/**
     * 获取两个日期的差  field参数为ChronoUnit.*
     * @param startTime
     * @param endTime
     * @param field  单位(年月日时分秒)
     * @return
     */
    public static long betweenTwoTime(LocalDateTime startTime, LocalDateTime endTime, ChronoUnit field) {
        Period period = Period.between(LocalDate.from(startTime), LocalDate.from(endTime));
        if (field == ChronoUnit.YEARS) return period.getYears();
        if (field == ChronoUnit.MONTHS) return period.getYears() * 12 + period.getMonths();
        return field.between(startTime, endTime);
    }

}
