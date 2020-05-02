package com.base.common.poi.util;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils {

    public final static String YYYY = "yyyy";

    public final static String MM = "MM";

    public final static String DD = "dd";

    public final static String YYYY_MM_DD = "yyyy/MM/dd";

    public final static String YYYY_MM = "yyyy/MM";

    public final static String HH_MM_SS = "HH:mm:ss";

    public final static String HH_MM = "HH:mm";

    public final static String YYYY_MM_DD_HH_MM = "yyyy/MM/dd HH:mm";

    public final static String YYYY_MM_DD_HH_MM_SS = "yyyy/MM/dd HH:mm:ss";

    /**
     * 构造函数
     */
    public DateUtils() {
    }

    /**
     * 日期格式化－将<code>Date</code>类型的日期格式化为<code>String</code>型
     * 
     * @param date
     *            待格式化的日期
     * @param pattern
     *            时间样式
     * @return 一个被格式化了的<code>String</code>日期
     */
    public static String format(Date date, String pattern) {
        if (date == null) {
            return null;
        } else {
            return getFormatter(pattern).format(date);
        }
    }

    /**
     * 默认为yyyy-MM-dd的格式化
     * 
     * @param date
     * @return
     * @Fixed by luyz
     */
    public static String format(Date date) {
        if (date == null) {
            return null;
        } else {
            return getFormatter("yyyy-MM-dd").format(date);
        }
    }

    /**
     * 日期解析－将<code>String</code>类型的日期解析为<code>Date</code>型
     * 
     * @param strDate
     *            待格式化的日期
     * @param pattern
     *            日期样式
     * @exception ParseException
     *                如果所给的字符串不能被解析成一个日期
     * @return 一个被格式化了的<code>Date</code>日期
     */
    public static Date parse(String strDate, String pattern)
            throws ParseException {
        try {
            if (strDate != null && !"".equals(strDate)) {
                return getFormatter(pattern).parse(strDate);
            } else {
                return null;
            }
        } catch (ParseException pe) {
            throw new ParseException(
                    "Method parse in Class DateUtils  err: parse strDate fail.",
                    pe.getErrorOffset());
        }
    }

    /**
     * 默认为yyyy-MM-dd格式的解析
     * 
     * @param strDate
     * @return
     * @throws ParseException
     * @Fixed by luyz
     */
    public static Date parse(String strDate) throws ParseException {
        try {
            if (strDate != null && !"".equals(strDate)) {
                return getFormatter("yyyy-MM-dd").parse(strDate);
            } else {
                return null;
            }
        } catch (ParseException pe) {
            throw new ParseException(
                    "Method parse in Class DateUtils  err: parse strDate fail.",
                    pe.getErrorOffset());
        }
    }

    /**
     * 获取当前日期
     * 
     * @return 一个包含年月日的<code>Date</code>型日期
     */
    public static synchronized Date getCurrDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    /**
     * 获取当前日期
     * 
     * @return 一个包含年月日的<code>String</code>型日期，但不包含时分秒。yyyy-mm-dd
     */
    public static String getCurrDateStr() {
        return format(getCurrDate(), YYYY_MM_DD);
    }
    public static String getCurrDateStr(String formatStr) {
        return format(getCurrDate(), formatStr);
    }

    /**
     * 获取当前时间
     * 
     * @return 一个包含年月日时分秒的<code>String</code>型日期。hh:mm:ss
     */
    public static String getCurrTimeStr() {
        return format(getCurrDate(), HH_MM_SS);
    }

    public static String getCurrHMStr() {
        return format(getCurrDate(), HH_MM);
    }

    /**
     * 获取当前完整时间,样式: yyyy－MM－dd hh:mm:ss
     * @return 一个包含年月日时分秒的<code>String</code>型日期。yyyy-MM-dd hh:mm:ss
     */
    public static String getCurrDateTimeStr() {
        return format(getCurrDate(), YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 根据传入的格式获取当前完整时间,样式: yyyy－MM－dd hh:mm:ss
     * @return 一个包含年月日时分秒的<code>String</code>型日期。yyyy-MM-dd hh:mm:ss
     */
    public static String getCurrDateTimeStr(String formatStr) {
        return format(getCurrDate(), formatStr);
    }
    /**
     * 获取当前时间,不要秒（此处依据安阳局变电运行管理需求）,样式: yyyy－MM－dd hh:mm
     * 
     * @return 一个包含年月日时分秒的<code>String</code>型日期。yyyy-MM-dd hh:mm
     */
    public static String getCurrDateTimeStr0() {
        return format(getCurrDate(), YYYY_MM_DD_HH_MM);
    }

    /**
     * 获取当前年分 样式：yyyy
     * 
     * @return 当前年分
     */
    public static String getYear() {
        return format(getCurrDate(), YYYY);
    }

    /**
     * 获取当前年分 样式：yyyy
     * 
     * @return 当前年分
     */
    public static String getYear(Date date) {
        return format(date, YYYY);
    }

    /**
     * 获取当前月分 样式：MM
     * 
     * @return 当前月分
     */
    public static String getMonth() {
        return format(getCurrDate(), MM);
    }

    /**
     * 获取当前月分 样式：MM
     * 
     * @return 当前月分
     */
    public static String getMonth(Date date) {
        return format(date, MM);
    }

    /**
     * 获得当前季度
     * 
     * @return 当前季度
     */
    public static String getQuarter(String month) {
        String quarter = "1";
        int m = Integer.parseInt(month);
        if (m >= 1 && m <= 3) {
            quarter = "1";
        } else if (m > 3 && m <= 6) {
            quarter = "2";
        } else if (m > 6 && m <= 9) {
            quarter = "3";
        } else {
            quarter = "4";
        }
        return quarter;
    }

    /**
     * 获取当前日期号 样式：dd
     * 
     * @return 当前日期号
     */
    public static String getDay() {
        return format(getCurrDate(), DD);
    }

    /**
     * 获取当前日期号 样式：dd
     * 
     * @return 当前日期号
     */
    public static String getDay(Date date) {
        return format(date, DD);
    }

    /**
     * 按给定日期样式判断给定字符串是否为合法日期数据
     * 
     * @param strDate
     *            要判断的日期
     * @param pattern
     *            日期样式
     * @return true 如果是，否则返回false
     */
    public static boolean isDate(String strDate, String pattern) {
        try {
            parse(strDate, pattern);
            return true;
        } catch (ParseException pe) {
            return false;
        }
    }

    /**
     * 判断给定字符串是否为特定格式日期（包括：年月日yyyy-MM-dd）数据
     * 
     * @param strDate
     *            要判断的日期
     * @return true 如果是，否则返回false
     */
    // public static boolean isDate(String strDate) {
    // try {
    // parse(strDate, YYYY_MM_DD);
    // return true;
    // }
    // catch (ParseException pe) {
    // return false;
    // }
    // }
    /**
     * 判断给定字符串是否为特定格式年份（格式：yyyy）数据
     * 
     * @param strDate
     *            要判断的日期
     * @return true 如果是，否则返回false
     */
    public static boolean isYYYY(String strDate) {
        try {
            parse(strDate, YYYY);
            return true;
        } catch (ParseException pe) {
            return false;
        }
    }

    public static boolean isYYYYMM(String strDate) {
        try {
            parse(strDate, YYYY_MM);
            return true;
        } catch (ParseException pe) {
            return false;
        }
    }

    /**
     * 判断给定字符串是否为特定格式的年月日（格式：yyyy-MM-dd）数据
     * 
     * @param strDate
     *            要判断的日期
     * @return true 如果是，否则返回false
     */
    public static boolean isYYYYMMDD(String strDate) {
        try {
            parse(strDate, YYYY_MM_DD);
            return true;
        } catch (ParseException pe) {
            return false;
        }
    }

    /**
     * 判断给定字符串是否为特定格式年月日时分秒（格式：yyyy-MM-dd HH:mm:ss）数据
     * 
     * @param strDate
     *            要判断的日期
     * @return true 如果是，否则返回false
     */
    public static boolean isYYYYMMDDHHMMSS(String strDate) {
        try {
            parse(strDate, YYYY_MM_DD_HH_MM_SS);
            return true;
        } catch (ParseException pe) {
            return false;
        }
    }

    public static boolean isYYYYMMDDHHMM(String strDate) {
        try {
            parse(strDate, YYYY_MM_DD_HH_MM);
            return true;
        } catch (ParseException pe) {
            return false;
        }
    }

    /**
     * 判断给定字符串是否为特定格式时分秒（格式：HH:mm:ss）数据
     * 
     * @param strDate
     *            要判断的日期
     * @return true 如果是，否则返回false
     */
    public static boolean isHHMMSS(String strDate) {
        try {
            parse(strDate, HH_MM_SS);
            return true;
        } catch (ParseException pe) {
            return false;
        }
    }

    /**
     * 判断给定字符串是否为特定格式时间（包括：时分秒hh:mm:ss）数据
     * 
     * @param strTime
     *            要判断的时间
     * @return true 如果是，否则返回false
     */
    // public static boolean isTime(String strTime) {
    // try {
    // parse(strTime, HH_MM_SS);
    // return true;
    // }
    // catch (ParseException pe) {
    // return false;
    // }
    // }
    /**
     * 判断给定字符串是否为特定格式日期时间（包括：年月日时分秒 yyyy-MM-dd hh:mm:ss）数据
     * 
     * @param strDateTime
     *            要判断的日期时间
     * @return true 如果是，否则返回false
     */
    // public static boolean isDateTime(String strDateTime) {
    // try {
    // parse(strDateTime, YYYY_MM_DD_HH_MM_SS);
    // return true;
    // }
    // catch (ParseException pe) {
    // return false;
    // }
    // }
    /**
     * 获取一个简单的日期格式化对象
     * 
     * @return 一个简单的日期格式化对象
     */
    private static SimpleDateFormat getFormatter(String parttern) {
        return new SimpleDateFormat(parttern);
    }

    /**
     * 获取给定日前的后intevalDay天的日期
     * 
     * @param refenceDate
     *            给定日期（格式为：yyyy-MM-dd）
     * @param intevalDays
     *            间隔天数
     * @return 计算后的日期
     */
    public static String getNextDate(String refenceDate, int intevalDays) {
        try {
            return getNextDate(parse(refenceDate, YYYY_MM_DD), intevalDays);
        } catch (Exception ee) {
            return "";
        }
    }

    /**
     * 获取给定日前的后intevalDay天的日期
     * 
     * @param refenceDate
     *            Date 给定日期
     * @param intevalDays
     *            int 间隔天数
     * @return String 计算后的日期
     */
    @SuppressWarnings("static-access")
    public static String getNextDate(Date refenceDate, int intevalDays) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(refenceDate);
            calendar.set(calendar.DATE, calendar.get(calendar.DATE)
                    + intevalDays);
            return format(calendar.getTime(), YYYY_MM_DD);
        } catch (Exception ee) {
            return "";
        }
    }

    @SuppressWarnings("static-access")
    public static Date getNextDated(Date refenceDate, int intevalDays) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(refenceDate);
            calendar.set(calendar.DATE, calendar.get(calendar.DATE)
                    + intevalDays);
            return calendar.getTime();
        } catch (Exception ee) {
            return null;
        }
    }

    /**
     * 获取指定日期前一天的日期
     * 
     * @param date
     * @return
     * @throws Exception
     */
    public static String getYesterday(Date date) throws Exception {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, -1);
            String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(cal
                    .getTime());
            return yesterday;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static long getIntevalDays(String startDate, String endDate) {
        try {
            return getIntevalDays(parse(startDate, YYYY_MM_DD), parse(endDate,
            		YYYY_MM_DD));
        } catch (Exception ee) {
            return 0l;
        }
    }
    public static long getIntevalDays(String startDate, String endDate, String formatStr) {
        try {
            return getIntevalDays(parse(startDate, formatStr), parse(endDate,
            		formatStr));
        } catch (Exception ee) {
            return 0l;
        }
    }

    public static long getIntevalDays(Date startDate, Date endDate) {
        try {
            Calendar startCalendar = Calendar.getInstance();
            Calendar endCalendar = Calendar.getInstance();

            startCalendar.setTime(startDate);
            endCalendar.setTime(endDate);
            long diff = endCalendar.getTimeInMillis()
                    - startCalendar.getTimeInMillis();

            return (diff / (1000 * 60 * 60 * 24));
        } catch (Exception ee) {
            return 0l;
        }
    }

    public static double getIntevalHours(Date startDate, Date endDate) {
        try {
            Calendar startCalendar = Calendar.getInstance();
            Calendar endCalendar = Calendar.getInstance();

            startCalendar.setTime(startDate);
            endCalendar.setTime(endDate);
            long diff = endCalendar.getTimeInMillis()
                    - startCalendar.getTimeInMillis();
            return ((double) diff / (1000 * 60 * 60));
        } catch (Exception ee) {
            return 0.0;
        }
    }

    public static long getIntevalminute(Date startDate, Date endDate) {
        try {
            Calendar startCalendar = Calendar.getInstance();
            Calendar endCalendar = Calendar.getInstance();

            startCalendar.setTime(startDate);
            endCalendar.setTime(endDate);
            long diff = endCalendar.getTimeInMillis()
                    - startCalendar.getTimeInMillis();
            return (diff / (1000 * 60));
        } catch (Exception ee) {
            return 0;
        }
    }

    /**
     * 获取当前日期所在月的第一天的日期
     * 
     * @param current
     * @return
     */
    @SuppressWarnings("deprecation")
    public static Date getFirstDate(Date current) throws Exception {
        try {
            return new Date(current.getYear(), current.getMonth(), 1);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 获取当前日期所在月的最后一天的日期
     * 
     * @param current
     * @return
     * @throws Exception
     */
    @SuppressWarnings("deprecation")
    public static Date getLastDate(Date current) throws Exception {
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(new Date(current.getYear(), current.getMonth(), 1));
            c.roll(Calendar.MONTH, true);
            if (current.getMonth() == 11) {
                c.roll(Calendar.YEAR, true);
            }
            c.add(Calendar.DATE, -1);
            return c.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 获取当前日期所在月的天数 zhouyong add
     *
     * @return
     * @throws Exception
     */
    public static int getMonthDays() throws Exception {
        try {
            int days = 0;
            Date firstDay = getFirstDate(new Date());
            Date lastDay = getLastDate(new Date());
            long b = getIntevalDays(firstDay, lastDay);
            days = (int) b + 1;
            return days;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 获取日期上个月的最后一天的日期
     * @return
     * @throws Exception
     */
    public static Date getUpLastDate(Date date) throws Exception {
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.set(Calendar.DATE, 1);// 设为当前月的1号
            c.add(Calendar.DATE, -1);// 减一天，变为上月最后一天
            return c.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 获取日期下个月的最后一天的日期
     * @return
     * @throws Exception
     */
    public static Date getDownLastDate(Date date) throws Exception {
        try {
            Date c = getLastDate(date);
            c = getNextDated(c, 1);
            return c;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 获取s日期上个月的最后一天的日期
     * 
     * @param date
     * @return
     * @throws Exception
     */
    public static Date getLastMonthDate(Date date) throws Exception {
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            // c.set(Calendar.MONTH,1);//设为当前月的1号
            c.add(Calendar.MONTH, -1);// 减一天，变为上月最后一天
            return c.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 获取本周一日期
     * 
     * @param current
     *            当前时间
     * @return
     * @throws Exception
     */
    public static Date getThisWeekMonday(Date current) throws Exception {
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(current);
            c.setFirstDayOfWeek(Calendar.MONDAY);
            c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);// // 本周1
            return c.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 获得上周日时间
     * 
     * @param current
     * @return
     * @throws Exception
     */
    public static Date getLastWeekSunday(Date current) throws Exception {
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(getThisWeekMonday(current));
            c.setFirstDayOfWeek(Calendar.MONDAY);
            c.add(Calendar.DATE, -1);
            return c.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 获得本周日时间
     * 
     * @param current
     * @return
     * @throws Exception
     */
    public static Date getThisWeekSunday(Date current) throws Exception {
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(current);
            c.setFirstDayOfWeek(Calendar.MONDAY);
            c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            return c.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 获得上周日时间
     * 
     * @param current
     * @return
     * @throws Exception
     */
    public static Date getNextWeekMonday(Date current) throws Exception {
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(getThisWeekSunday(current));
            c.setFirstDayOfWeek(Calendar.MONDAY);
            c.add(Calendar.DATE, +1);
            return c.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 获得星期几
     * 
     * @param current
     * @return
     * @throws Exception
     */
    public static String getWeekDay(Date current) throws Exception {
        try {
            SimpleDateFormat sd = new SimpleDateFormat("EEE");
            return sd.format(current);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 得到某一天是这一年的第几周
     * 
     * @param utilDate
     * @return
     */
    public static String getWeekNum(Date utilDate) {
        Calendar cal = Calendar.getInstance(TimeZone.getDefault(), new Locale(
                "zh", "CN"));
        cal.setTime(utilDate);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        int week = cal.get(Calendar.WEEK_OF_YEAR);
        String ret = String.valueOf(week);
        return ret.length() == 1 ? "0" + ret : ret;
    }

    /**
     * 找出某月属于上半年还是下半年
     * 
     * @param month
     * @return
     */
    public static String getHalfYearByMonth(int month) {
        if (month < 7) {
            return "上半年  ";
        } else {
            return "下半年  ";
        }
    }

    /**
     * 找出某天是某月的哪个旬
     * 
     * @param day
     * @return
     */
    public static String getTendays(int day) {
        if (day <= 10) {
            return "上旬";
        } else if (10 < day && day <= 20) {
            return "中旬";
        } else {
            return "下旬";
        }
    }

    /**
     * 输入日期，得到下个月的日期格式是yyyy-mm
     * 
     * @param date
     * @return
     */
    public static String getNextMonth(Date date) {
        String month = format(date, MM);
        String ret = "";
        if ("01".equals(month)) {
            ret = getYear(date) + "-02";
        } else if ("02".equals(month)) {
            ret = getYear(date) + "-03";
        } else if ("03".equals(month)) {
            ret = getYear(date) + "-04";
        } else if ("04".equals(month)) {
            ret = getYear(date) + "-05";
        } else if ("05".equals(month)) {
            ret = getYear(date) + "-06";
        } else if ("06".equals(month)) {
            ret = getYear(date) + "-07";
        } else if ("07".equals(month)) {
            ret = getYear(date) + "-08";
        } else if ("08".equals(month)) {
            ret = getYear(date) + "-09";
        } else if ("09".equals(month)) {
            ret = getYear(date) + "-10";
        } else if ("10".equals(month)) {
            ret = getYear(date) + "-11";
        } else if ("11".equals(month)) {
            ret = getYear(date) + "-12";
        } else if ("12".equals(month)) {
            ret = String.valueOf(Integer.parseInt(getYear(date)) + 1) + "-01";
        }
        return ret;
    }

    /***************************************************************************
     * @功能 计算某年某周的开始日期
     * @return interger
     * @throws ParseException
     **************************************************************************/
    public static String getYearWeekFirstDay(String yearNum, String weekNum,
            int type) throws ParseException {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.parseInt(yearNum));
        cal.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(weekNum));
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        // 分别取得当前日期的年、月、日
        String tempYear = yearNum;
        String tempMonth = Integer.toString(cal.get(Calendar.MONTH) + 1);
        String tempDay = Integer.toString(cal.get(Calendar.DATE));
        String tempDate = "";
        if (1 == type) {
            tempDate = tempMonth + "月  " + tempDay + "日  ";
        } else if (2 == type) {
            tempDate = tempYear + "-"
                    + (tempMonth.length() == 1 ? "0" + tempMonth : tempMonth)
                    + "-" + tempDay;
        }
        return tempDate;
    }

    /***************************************************************************
     * @功能 计算某年某周的结束日期
     * @return interger
     * @throws ParseException
     **************************************************************************/
    public static String getYearWeekEndDay(String yearNum, String weekNum,
            int type) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.parseInt(yearNum));
        cal.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(weekNum) + 1);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        // 分别取得当前日期的年、月、日
        String tempYear = yearNum;
        String tempMonth = Integer.toString(cal.get(Calendar.MONTH) + 1);
        String tempDay = Integer.toString(cal.get(Calendar.DATE));
        String tempDate = "";
        if (1 == type) {
            tempDate = tempMonth + "月  " + tempDay + "日  ";
        } else if (2 == type) {
            tempDate = tempYear + "-"
                    + (tempMonth.length() == 1 ? "0" + tempMonth : tempMonth)
                    + "-" + tempDay;
        }
        return tempDate;
    }

    /**
     * 根据小时差值 计算时间
     * @param date
     * @param hourForCalculate
     * @return
     */
    public static Date hourCalculate(Date date, int hourForCalculate) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR, c.get(Calendar.HOUR) + hourForCalculate);
        return c.getTime();
    }

    /**
     * 根据分钟差值 计算时间
     * @param date
     * @param minuteForCalculate
     * @return
     */
    public static Date minuteCalculate(Date date, int minuteForCalculate) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) + minuteForCalculate);
        return c.getTime();
    }

    /**
     * 根据秒差值 计算时间
     * @param date
     * @param secondForCalculate
     * @return
     */
    public static Date secondCalculate(Date date, int secondForCalculate) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.SECOND, c.get(Calendar.SECOND) + secondForCalculate);
        return c.getTime();
    }

    /**
     * 将秒数转换为xx天xx小时xx分的格式
     * @param seconds
     * @return
     */
    public static String formatBySeconds(String seconds) {
        if (seconds == null || "".equals(seconds)) {
            return "";

        }
        long second = Long.parseLong(seconds);
        int day = (int) Math.floor(second / (3600 * 24));
        int hours = (int) Math.floor(second % (3600 * 24) / 3600);
        int minite = (int) Math.floor((second % (3600 * 24) % 3600) / 60);
        String runtime = day + "天  " + hours + "小时  " + minite + "分  ";
        return runtime;
    }

    public static Double getIntevalMonths(String startDate, String endDate) {
        try {
            return getIntevalMonths(parse(startDate, YYYY_MM_DD), parse(
                    endDate, YYYY_MM_DD));
        } catch (Exception ee) {
            return 0d;
        }
    }

    public static Double getIntevalMonths(Date startDate, Date endDate) {
        try {
            Calendar startCalendar = Calendar.getInstance();
            Calendar endCalendar = Calendar.getInstance();

            startCalendar.setTime(startDate);
            endCalendar.setTime(endDate);

            int startYear = startCalendar.get(Calendar.YEAR);
            int startMonth = startCalendar.get(Calendar.MONTH) + 1;
            int startDay = startCalendar.get(Calendar.DAY_OF_MONTH);

            int endYear = endCalendar.get(Calendar.YEAR);
            int endMonth = endCalendar.get(Calendar.MONTH) + 1;
            int endDay = endCalendar.get(Calendar.DAY_OF_MONTH);
            Double value = 0d;

            Double monthday = 30d;
            try {
                if (endDay > startYear) {
                    monthday = Double.valueOf(DateUtils.getMonthDays());
                } else {
                    monthday = Double.valueOf(DateUtils.getDay(DateUtils
                            .getUpLastDate(DateUtils.getCurrDate())));
                }
            } catch (Exception e) {
            }
            value = (endYear - startYear) * 12.0 + (endMonth - startMonth)
                    + (endDay - startDay) / monthday;
            if (value < 0) {
                value = 0d;
            }
            return Double.valueOf(Math.round(value * 100) / 100.0);
        } catch (Exception ee) {
            return 0d;
        }
    }
}
