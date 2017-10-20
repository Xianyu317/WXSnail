package com.snail.util;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;

public class DateUtil {
	private static Logger logger = Logger.getLogger(DateUtil.class);
	
	private static final long ONE_DAY_INTERVAL = 1000 * 3600 * 24;
	private static final long ONE_SECEND_INTERVAL = 1000;
	public static final String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
	public static final String yyyyMMddHHmm = "yyyy-MM-dd HH:mm";
	public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
	public static final String YYYYMMDD = "yyyy-MM-dd";
	public static final String YYYYMM = "yyyy-MM";
	public static final String HHMMSS = "HH:mm:ss";
	public static final String HHMMSSSSS = "HH:mm:ss:SSS";
	private static SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
	public static final String yyyyMMdd = "yyyyMMdd";
	public static final String TIME_FORMAT = " dd MM ? yyyy";
	public static final String YYYYMMDDHHMM = "yyyy/MM/dd HH:MM";
	public static final String YYYY_MM_DD_HHMMSS = "yyyy/MM/dd HH:mm:ss";
	public static final String YYYY_MM_DD_HHMM = "yyyy年MM月dd日 HH时mm分";
	
	/**
	 * 当前日期
	 */
	public static String getCurrentDMY(Date date)
	{
		return date2String(date,TIME_FORMAT);
	}
	
	public static String getCurrentYMDHM(String dateStr) throws Exception {
		SimpleDateFormat sdf1 = new SimpleDateFormat(YYYY_MM_DD_HHMMSS) ;        
        SimpleDateFormat sdf2 = new SimpleDateFormat(YYYY_MM_DD_HHMM) ; 
		return sdf2.format(sdf1.parse(dateStr));
	}
	public static String getCurrentYMDHM() {
		SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HHMM) ; 
		return sdf.format(new Date());
	}
	public static String getCurrentYMDHMS(String dateStr) throws Exception {
		SimpleDateFormat sdf1 = new SimpleDateFormat(YYYYMMDDHHMMSS) ;        
		SimpleDateFormat sdf2 = new SimpleDateFormat(YYYY_MM_DD_HHMM) ; 
		return sdf2.format(sdf1.parse(dateStr));
	}
	
	/**
	 * 当前日期
	 */
	public static String getCurrentYMD()
	{
		return date2String(new Date(),yyyyMMdd);
	}
	
	/**
	 * 获得当然日期，返回Date类型
	 */
	public static Date getCurDate() {
		return new Date();
	}

	/**
	 * 当前日期
	 */
	public static String getCurrentDate() {
		return date2String(new Date(), YYYYMMDD);
	}
	
	public static String getCurrentDate(Date date) {
		return date2String(date, YYYYMMDD);
	}
	
	public static String getCurrentTime(Date date) {
		return date2String(date, HHMMSSSSS);
	}
	
	/**
	 * 当天开始时间
	 */
	public static String getCurrentDateStart() {
		return date2String(new Date(), YYYYMMDD) + " 00:00:00";
	}
	
	/**
	 * 当天结束时间
	 */
	public static String getCurrentDateEnd() {
		return date2String(new Date(), YYYYMMDD) + " 23:59:59";
	}

	/**
	 * 当前日期
	 */
	public static String getCurrentYM() {
		return date2String(new Date(), YYYYMM);
	}

	/**
	 * 当前时间yyyy-MM-dd HH:mm:ss
	 */
	public static String getCurrentDatetime() {
		return date2String(new Date(), YYYYMMDDHHMMSS);
	}
	
	/**
	 * 当前时间yyyy-MM-dd HH:mm
	 */
	public static String getCurrentDatetimeYmdhm() {
		return date2String(new Date(), yyyyMMddHHmm);
	}
	
	/**
	 * 当前时间yyyy/MM/dd HH:mm
	 */
	public static String getCurrentDatetimeTo() {
		return date2String(new Date(), YYYYMMDDHHMM);
	}
	
	/**
	 * 当前时间yyyy-MM-dd HH:mm:ss
	 */
	public static String getCurrentDatetime(Date date) {
		return date2String(date, YYYYMMDDHHMMSS);
	}

	/**
	 * 当前时间
	 */
	public static String getCurrentDateString() {
		return date2String(new Date(), yyyyMMddHHmmss);
	}

	/**
	 * 日期按照指定格式转换为字符串
	 */
	public static String date2String(Date date, String formatStr) {
		return date2String(date, formatStr, Locale.getDefault());
	}

	/**
	 * 使用参数Format将字符串转为Date
	 */
	public static Date string2Date(String strDate, String formatStr) {
		return string2Date(strDate, formatStr, Locale.getDefault());
	}

	/**
	 * 比较StartDate和EndDate, startDate和endDate必须是凌晨的0:00:00:000.
	 */
	public static int dateDiff(Date startDate, Date endDate) {
		assert (startDate.getTime() % ONE_DAY_INTERVAL == 0 && endDate
				.getTime() % ONE_DAY_INTERVAL == 0);
		long interval = endDate.getTime() - startDate.getTime();
		return (int) (interval / ONE_DAY_INTERVAL);
	}

	/**
	 * 比较StartDate和EndDate, 返回值秒钟
	 */
	public static long dateDiffMin(Date startDate, Date endDate) {
		assert (startDate.getTime() % ONE_DAY_INTERVAL == 0 && endDate
				.getTime() % ONE_DAY_INTERVAL == 0);
		long interval = endDate.getTime() - startDate.getTime();
		return interval / ONE_SECEND_INTERVAL;
	}

	/**
	 * 得到增加i天后的时间，如加（5）或减（-5）
	 */
	public static Date addDay(Date date, int i) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, i);
		return cal.getTime();
	}

	/**
	 * 得到之前的几天
	 */
	public static String getDateBefore(int before) {
		return date2String(new Date(System.currentTimeMillis()
				- ONE_DAY_INTERVAL * before), YYYYMMDD);
	}

	/**
	 * 得到之前的几个月
	 */
	public static String getMonthBefore(int before) {
		Calendar gr = Calendar.getInstance();

		int year = Integer.parseInt(DateUtil.getCurrentDate().substring(0, 4));

		int month = Integer.parseInt(DateUtil.getCurrentDate().substring(5, 7));

		int day = Integer.parseInt(DateUtil.getCurrentDate().substring(8, 10));

		SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDDHHMMSS);

		gr.set(year, month - before - 1, day);

		return sdf.format(gr.getTime());
	}
	/**
	 * 得到某一天前九个月
	 * @param date 某一天
	 * @param before 前几个月
	 * @param time 时间（例： 23:59:59）
	 * @return
	 */
	public static String getMonthBeforeOneDay(String date,int before,String time) {
		Calendar gr = Calendar.getInstance();

		int year = Integer.parseInt(date.substring(0, 4));

		int month = Integer.parseInt(date.substring(5, 7));

		int day = Integer.parseInt(date.substring(8, 10));

		SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDD);

		gr.set(year, month - before - 1, day);

		return sdf.format(gr.getTime())+time;
	}
	/**
	 * 得到指定日期的前几天
	 */
	public static Date getDateBefore(Date dt, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		cal.add(Calendar.DAY_OF_MONTH, days);
		return cal.getTime();
	}

	/**
	 * 得到之后几天
	 */
	public static String getDateAfter(String dt, int days) {
		Date dt_time;
		dt_time = string2Date(dt, YYYYMMDD);
		return date2String(new Date(dt_time.getTime() + 1000 * 60 * 60 * 24
				* days), YYYYMMDD);
	}

	/**
	 * 得到之前几天
	 */
	public static String getDateBefore(String dt, int days) {
		Date dt_time;
		dt_time = string2Date(dt, YYYYMMDD);
		return date2String(new Date(dt_time.getTime() - 1000 * 60 * 60 * 24
				* days), YYYYMMDD);
	}

	/**
	 * 得到某天的DayOfWeek: 星期一为1...星期天为7;
	 */
	public static int getDayOfWeek(Date theDay) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(theDay);
		return ((cal.get(Calendar.DAY_OF_WEEK) + 5) % 7) + 1;
	}

	/**
	 * 得到一个日期的年份
	 */
	public static int getYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}

	/**
	 * 得到一个日期的月份
	 */
	public static int getMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH) + 1;
	}

	/**
	 * 得到一个日期的Day
	 */
	public static int getDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 得到一个日期的小时
	 */
	public static int getHour(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 得到一个日期的分钟
	 */
	public static int getMinite(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MINUTE);
	}
	
	/**
	 * 得到一个日期的秒
	 */
	public static int getSecond(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.SECOND);
	}

	/**
	 * 得到输入日期是该年第几周
	 */
	public static int getWeekOfYear(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		return cld.get(Calendar.WEEK_OF_YEAR);
	}

	private static Date string2Date(String strDate, String formatStr,
			Locale locale) {
		Date date = null;
		try {
			date = new SimpleDateFormat(formatStr, locale).parse(strDate);
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		return date;
	}

	public static String date2Str(Date date, String format) {
		SimpleDateFormat tempFormat = new SimpleDateFormat(format);
		if (date == null) {
			return "";
		}
		return tempFormat.format(date);
	}

	public static String date2Str(Date date) {
		if (date == null) {
			return "";
		}
		return formater.format(date);
	}

	private static String date2String(Date date, String formatStr, Locale locale) {
		try {
			Format format = new SimpleDateFormat(formatStr, locale);
			return format.format(date);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "";
	}

	// 判断时间date1是否在时间date2之前
	// 时间格式 2005-4-21 16:16:34
	public static boolean isDateBefore(String date1, String date2) {
		try {
			DateFormat df = DateFormat.getDateTimeInstance();
			return df.parse(date1).before(df.parse(date2));
		} catch (ParseException e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	// 判断当前时间是否在时间date2之前
	// 时间格式 2005-4-21 16:16:34
	public static boolean isDateBefore(String date2) {
		try {
			Date date1 = new Date();
			DateFormat df = DateFormat.getDateTimeInstance();
			return date1.before(df.parse(date2));
		} catch (ParseException e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	// 时间date 和当前时间加上nDay天即date2(当前时间) date>date2返回false date<date2返回false
	public static boolean compareDate(Date date, int nDay) {
		Calendar curr = Calendar.getInstance();
		curr.setTime(getCurDate());
		curr.add(Calendar.DAY_OF_WEEK, nDay);
		Date currDate = curr.getTime();
		return date.before(currDate);
	}

	public static boolean compareDate2(Date date, int nHour) {
		Calendar curr = Calendar.getInstance();
		curr.setTime(getCurDate());
		curr.add(Calendar.HOUR_OF_DAY, nHour);
		Date currDate = curr.getTime();
		return date.before(currDate);
	}

	/**
	 * 获得指定日期的后n年的日期
	 * 
	 * @param currentDate
	 * @return
	 */
	public static Date getForOffsetYear(Date currentDate, int nYear) {
		if (currentDate == null) {
			return null;
		}
		Calendar curr = Calendar.getInstance();
		curr.setTime(currentDate);
		curr.add(Calendar.YEAR, nYear);
		return curr.getTime();
	}

	/**
	 * 获得指定日期的后n月的日期
	 * 
	 * @param currentDate
	 * @return
	 */
	public static Date getForOffsetMonth(Date currentDate, int nMonth) {
		if (currentDate == null) {
			return null;
		}
		Calendar curr = Calendar.getInstance();
		curr.setTime(currentDate);
		curr.add(Calendar.MONTH, nMonth);
		return curr.getTime();
	}

	/**
	 * 获取时间戳
	 * 
	 * @return
	 * @throws ParseException
	 * @author lyl
	 * @creation date Apr 20, 2011
	 */
	public static long getTimestamp() {
		long rand = System.currentTimeMillis();
		return rand;
	}

	/**
	 * 将时间戳转换为时间
	 * 
	 * @param time
	 * @return
	 * @throws ParseException
	 * @author lyl
	 * @creation date Apr 20, 2011
	 */
	public static Date getTimesDate(long time) {
		Date nowTime = new Date(time);
		return nowTime;
	}

	/**
	 * 比较两个时间
	 * 
	 * @param DATE1
	 * @param DATE2
	 * @return
	 */
	public static int compare_date(String DATE1, String DATE2) {

		DateFormat df = new SimpleDateFormat(YYYYMMDDHHMMSS);
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(DATE2);
			if (dt1.getTime() > dt2.getTime()) {
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	/**
	 * @param date1
	 *            需要比较的时间 不能为空(null),需要正确的日期格式
	 * @param date2
	 *            被比较的时间 为空(null)则为当前时间
	 * @param stype
	 *            返回值类型 0为多少天，1为多少个月，2为多少年
	 * @return
	 */
	public static int compareDate(String date1, String date2, int stype) {
		int n = 0;
		String formatStyle = stype == 1 ? "yyyy-MM" : "yyyy-MM-dd";

		date2 = date2 == null ? getCurrentDate() : date2;

		DateFormat df = new SimpleDateFormat(formatStyle);
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		try {
			c1.setTime(df.parse(date1));
			c2.setTime(df.parse(date2));
		} catch (Exception e3) {
			System.out.println("wrong occured");
		}
		// List list = new ArrayList();
		while (!c1.after(c2)) { // 循环对比，直到相等，n 就是所要的结果
			// list.add(df.format(c1.getTime())); // 这里可以把间隔的日期存到数组中 打印出来
			n++;
			if (stype == 1) {
				c1.add(Calendar.MONTH, 1); // 比较月份，月份+1
			} else {
				c1.add(Calendar.DATE, 1); // 比较天数，日期+1
			}
		}

		n = n - 1;

		if (stype == 2) {
			n = (int) n / 365;
		}

		// System.out.println(date1+" -- "+date2+" 相差多少"+u[stype]+":"+n);
		return n;
	}

	/**
	 * 返回当前月的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static String getMothByLastDay(String date) {
		// String date ="2011-11-13";
		DateFormat df = new SimpleDateFormat(YYYYMMDDHHMMSS);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(string2Date(date, YYYYMMDDHHMMSS));
		Calendar cpcalendar = (Calendar) calendar.clone();
		cpcalendar.set(Calendar.DAY_OF_MONTH, 1);
		// System.out.println("当前月第一天:"+df.format( new
		// Date(cpcalendar.getTimeInMillis())));
		cpcalendar.add(Calendar.MONTH, 1);
		cpcalendar.add(Calendar.DATE, -1);
		// System.out.println("当前月最后一天:"+df.format( new
		// Date(cpcalendar.getTimeInMillis())));
		return df.format(new Date(cpcalendar.getTimeInMillis()));
	}

	/**
	 * 返回当前月的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static String getMothByOneDay(String date) {
		// String date ="2011-11-13";
		DateFormat df = new SimpleDateFormat(YYYYMMDDHHMMSS);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(string2Date(date, YYYYMMDDHHMMSS));
		Calendar cpcalendar = (Calendar) calendar.clone();
		cpcalendar.set(Calendar.DAY_OF_MONTH, 1);
		return df.format(new Date(cpcalendar.getTimeInMillis()));
	}

	/**
	 * 根据年月获取最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static String getLastDayByYM(String date) {
		DateFormat df = new SimpleDateFormat(YYYYMMDDHHMMSS);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(string2Date(date, YYYYMMDD));
		Calendar cpcalendar = (Calendar) calendar.clone();
		cpcalendar.set(Calendar.DAY_OF_MONTH, 1);
		// System.out.println("当前月第一天:"+df.format( new
		// Date(cpcalendar.getTimeInMillis())));
		cpcalendar.add(Calendar.MONTH, 1);
		cpcalendar.add(Calendar.DATE, -1);
		// System.out.println("当前月最后一天:"+df.format( new
		// Date(cpcalendar.getTimeInMillis())));
		return df.format(new Date(cpcalendar.getTimeInMillis()));
	}

	/**
	 * 得到本周周一
	 * @return yyyy-MM-dd
	 */
	public static String getMondayOfThisWeek() {
		Calendar c = Calendar.getInstance();
		int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayofweek == 0)
			dayofweek = 7;
		c.add(Calendar.DATE, -dayofweek + 1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(c.getTime()) + " 00:00:00";
	}

	/**
	 * 得到本周周日
	 * @return yyyy-MM-dd
	 */
	public static String getSundayOfThisWeek() {
		Calendar c = Calendar.getInstance();
		int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayofweek == 0)
			dayofweek = 7;
		c.add(Calendar.DATE, -dayofweek + 7);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(c.getTime()) + " 23:59:59";
	}
	
	/**
	  * 得到本月最后一天
	  * @return yyyy-MM-dd
	  */
	 public static String getLastDateOfMonth()
	 {
	  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	  Date dt = new Date();
	  Calendar cal = Calendar.getInstance();
	  cal.setTime(dt);
	  int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	  cal.set(Calendar.DAY_OF_MONTH, days);
	  return format.format(cal.getTime()) + " 23:59:59";
	 }

	 /**
	  * 得到本月第一天
	  * @return yyyy-MM-dd
	  */
	 public static String getFristDateOfMonth()
	 {
	  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	  Date dt = new Date();
	  Calendar cal = Calendar.getInstance();
	  cal.setTime(dt);
	  int days = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
	  cal.set(Calendar.DAY_OF_MONTH, days);
		return format.format(cal.getTime()) + " 00:00:00";
	}

	/**
	 * 将java日期格式转换成wsdl可识别的格式
	 * 
	 * @param date
	 * @return
	 */
	public static XMLGregorianCalendar convertToXMLGregorianCalendar(Date date) {

		if (date == null) {
			return null;
		}
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		XMLGregorianCalendar gc = null;
		try {
			gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
		} catch (Exception e) {

		}
		return gc;
	}
	 
    /**
     * 字符串转化为格式化字符串格式
     * @param date 日期
     * @param formatString 格式化字符串
     * @return
     * @throws ParseException
     */
    public static String convertStringToString(String date, String formatString){
        return new SimpleDateFormat(formatString).format(date);
    }
    
    /**
     * 给指定日期添加月数
     * @param date	日期
     * @param monthNub	月数
     * @return 添加后的日期
     */
    public static Date addMonth(Date date, int monthNub){
    	Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.MONTH, monthNub);
        return rightNow.getTime();
    }
    
    /**
     * 给指定日期添加年数
     * @param date	日期
     * @param monthNub	年数
     * @return 添加后的日期
     */
    public static Date addYear(Date date, int monthNub){
    	Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.YEAR, monthNub);
        return rightNow.getTime();
    }
    
    /**
	 * 当前时间
	 * 是否在有效期内(>开始时间, <结束时间)
	 */
	public static boolean timeLegal(String beginTM, String endTM) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
			Date nowDate = new Date();
			Date endTime = sdf.parse(endTM);
			Date beginTime = sdf.parse(beginTM);
			if(nowDate.before(endTime) && beginTime.before(nowDate)) {
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 获取系统时间前一天的开始时间
	 * @return
	 */
	public static String getSysDayBegin() {
		Calendar gr = Calendar.getInstance();
		gr.add(Calendar.DATE, -1);    //得到前一天
		SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDD);
		return sdf.format(gr.getTime()) +" 00:00:00";
	}
	/**
	 * 获取系统时间前一天的结束时间
	 * @return
	 */
	public static String getSysDayEnd() {
		Calendar gr = Calendar.getInstance();
		gr.add(Calendar.DATE, -1);    //得到前一天
		SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDD);
		return sdf.format(gr.getTime()) +" 23:59:59";
	}
	
	/**
	 * 获取系统时间前一天
	 * @return
	 */
	public static String getSysBrofeDay() {
		Calendar gr = Calendar.getInstance();
		gr.add(Calendar.DATE, -1);    //得到前一天
		SimpleDateFormat sdf = new SimpleDateFormat(yyyyMMdd);
		return sdf.format(gr.getTime());
	}
	
	public static String getSysBrofeDay(int dateNum) {
		Calendar gr = Calendar.getInstance();
		gr.add(Calendar.DATE, dateNum);    //得到前一天
		SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDD);
		return sdf.format(gr.getTime());
	}
	
	public static String formatDate(Date date,String format){
		String result="";
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		if(date!=null){
			result=sdf.format(date);
		}
		return result;
	}
	
	public static Date formatString(String str,String format) throws Exception{
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		return sdf.parse(str);
	}
}