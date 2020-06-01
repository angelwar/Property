package com.huanzong.property.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

    public static long dateToTime(Date time) {
            long ts = time.getTime()/1000;
            return ts;
    }

    /**
     * 时间转换为时间戳
     * @param time
     * @return
     */
    public static long dateToTimestamp(String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = simpleDateFormat.parse(time);
            long ts = date.getTime()/1000;
            return ts;
        } catch (ParseException e) {
            return 0;
        }
    }


    /**
     * 时间戳转换为时间
     */

    public static String timestampToDate(long time) {
        if (time < 10000000000L) {
            time = time * 1000;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String sd = sdf.format(new Date(Long.parseLong(String.valueOf(time))));
        return sd;
    }


    /**
     * 获取当前时间（yyyy-MM-dd hh:mm:ss）
     */

    public static String nowTime() {
        Date currentTime = new Date();// 获取当前时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");// 格式化时间
        String dateString = formatter.format(currentTime);// 转换为字符串
        return dateString;
    }

    /**
     * 获取当前时间戳（13位）
     */

    public static long nowData() {
// 方法 一
// long data = System.currentTimeMillis();
// 方法 二
        long data = Calendar.getInstance().getTimeInMillis();
// 方法三
// long data = new Date().getTime();
        return data;

    }

    /**
     * 比较时间大小
     */

    public static int compare_date(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
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
     * 字符串转换为时间
     */

    public static Date strToData(String data,String format) {
        Date date = null;
        try {
            date = new SimpleDateFormat(format).parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    /**
     * 时间格式化
     */

    public static String parseTimestamp(String format) {
        Date now = new Date();
        SimpleDateFormat f = new SimpleDateFormat(format);
        return f.format(now);
    }
}
