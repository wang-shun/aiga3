package com.eastelsoft.etos2.em.base.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
/**
 * 
 */
public final class DateUtil {
	/**
	 * 4位年 2位月 2位日 24小时制 2位分 2位2秒 如：20130808182418的形式
	 */
	public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	/**
	 * 4位年-2位月-2位日  24小时制-2位分-2位-2秒 如：2013-08-08 18:24:18的形式
	 */
	public static String YMDHMS = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 4位年 2位月 2位日 如：20130808的形式
	 */
	public static String YYYYMMDD = "yyyyMMdd";
	/**
	 * 4位年-2位月-2位日  24小时制-2位分-2位-2秒 如：2013-08-08的形式
	 */
	public static String YYYY_MM_DD = "yyyy-MM-dd";
	/**
	 * 4位年-2位月-2位日  24小时制-2位分-2位-2秒 如：2013-08的形式
	 */
	public static String YYYY_MM = "yyyy-MM";
	/**
	 * 4位年-2位月-2位日  24小时制-2位分-2位-2秒 如：2013的形式
	 */
	public static String YYYY = "yyyy";
	
	
	/**
	 * 转换操作，将日期对象转换成指定的字符串，并处理对象中的特殊字符
	 * @param value Object
	 * @return String
	 */
	public static String doConvertToString(Object value) {
		String result = null;
		if (value instanceof Date) {
			SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDDHHMMSS);
			result = sdf.format(value);
			result = result.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\"", "&quot;");
		}
		return result;
	}
	
	/**
	 * 获得当前时间字符串形式
	 * @return String yyyyMMddHHmmss形式的字符串 如：20131118081808
	 */
	public static String getCurrTimeString() {
		return getCurrTimeString(YYYYMMDDHHMMSS);
	}
	
	/**
	 * 将当前日期对象转换成指定的日期格式化字符串
	 * @param format String 日期格式化字符串
	 * @return String 当前日期格式化后的字符串
	 */
	public static String getCurrTimeString(String formart) {
		SimpleDateFormat sdf = new SimpleDateFormat(formart);
		return sdf.format(new Date());
	}
	
	/**
	 * 将传入的Date对象，转换成yyyyMMddHHmmss形式的字符串
	 * @param date java.util.Date
	 * @return String yyyyMMddHHmmss形式的字符串 如：20131118081808
	 */
	public static String getDateStringByDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDDHHMMSS);
		return sdf.format(date);
	}
	
	/**
	 * 将传入的Date对象，转换成指定的formart形式的字符串
	 * @param date java.util.Date
	 * @param formart String 要将日期格式化成的字符串形式 如：yyyyMMddHHmmss
	 * @return String format形式的日期字符串
	 */
	public static String getDateStringByDate(Date date, String formart) {
		SimpleDateFormat sdf = new SimpleDateFormat(formart);
		return sdf.format(date);
	}
	
	/**
	 * 将传入的Timestamp对象，转换成指定的formart形式的字符串
	 * @param date java.util.Date
	 * @param formart String 要将日期格式化成的字符串形式 如：yyyyMMddHHmmss
	 * @return String format形式的日期字符串
	 */
	public static String getTimestamp(Timestamp date, String formart) {
		SimpleDateFormat sdf = new SimpleDateFormat(formart);
		return sdf.format(date);
	}
	
	/**
	 * 将14位的日期字符串，转换成Date对象
	 * @param date String 14位的日期字符串：如201312081508
	 * @return java.util.Date
	 */
	public static Date getDateByDateString(String date) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Integer.valueOf(date.substring(0, 4)), (Integer.valueOf(date.substring(4, 6)) - 1),
				Integer.valueOf(date.substring(6, 8)), Integer.valueOf(date.substring(8, 10)), 
				Integer.valueOf(date.substring(10, 12)), Integer.valueOf(date.substring(12, 14)));
		return calendar.getTime();
	}
	
	public static Date getDate(String date, String formart) {
		SimpleDateFormat sdf = new SimpleDateFormat(formart);
		
		try {
			Date d = sdf.parse(date);
			return d;
		} catch (ParseException e) {
			return null;
		}
	}
	
	/**
	 * 根据传入的时间，算出当天的时间范围的两个时间点
	 * @param date
	 * 支持格式：
	 * yyyy-MM-dd HH:mm:ss
	 * yyyy-MM-dd
	 * @return
	 */
	public static Date[] getDateScope(String date){
		if(StringUtils.isBlank(date)){
			return null;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat(YMDHMS);
		
		try {
			Date d = sdf.parse(date);
			return getDateScope(d);
		} catch (ParseException e) {
			sdf = new SimpleDateFormat(YYYY_MM_DD);
			try {
				Date d = sdf.parse(date);
				return getDateScope(d);
			} catch (ParseException e1) {
			}
		}
		return null;
	}
	
	/**
	 * 根据传入的时间，算出当天的时间范围的两个时间点
	 * @param date
	 * @return
	 * Date[0]:当天时间开始
	 * Date[1]:当天时间结束
	 */
	public static Date[] getDateScope(Date date){
		if(date == null){
			return null;
		}

		Calendar cal1=Calendar.getInstance();
		cal1.setTime(date);
		
		//获得开始时间
		cal1.set(Calendar.HOUR_OF_DAY, 0);
		cal1.set(Calendar.MINUTE, 0);
		cal1.set(Calendar.SECOND, 0);
		
		Date dateFirst = cal1.getTime();
		
		//获得开始时间
		cal1.set(Calendar.HOUR_OF_DAY, 23);
		cal1.set(Calendar.MINUTE, 59);
		cal1.set(Calendar.SECOND, 59);
		
		Date dateEnd = cal1.getTime();
		
		return new Date[]{dateFirst, dateEnd};
	}
	
}

