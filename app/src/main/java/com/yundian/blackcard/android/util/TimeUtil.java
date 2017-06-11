package com.yundian.blackcard.android.util;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : Created by 180
 * @version : 0.01
 * @email : yaobanglin@163.com
 * @created time : 2015-06-29 12:05
 * @describe : TimeUtil
 * @for your attention : none
 * @revise : none
 */
@SuppressLint("SimpleDateFormat")
public class TimeUtil {

    public static String getTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm");
        return format.format(new Date(time));
    }

    public static String getNewFormatTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日 HH:mm");
        return format.format(new Date(time));
    }

    public static String getHourAndMin(long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(new Date(time));
    }

    public static String getDateAndTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date(time));
    }

    public static String formatDate(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(new Date(time));
    }

    public static String formatDate2(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月");
        return format.format(new Date(time));
    }

    public static String getFormatTime(long timesamp, boolean isDynamic) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        Date today = new Date(System.currentTimeMillis());
        Date otherDay = new Date(timesamp);
        int temp = Integer.parseInt(sdf.format(today))
                - Integer.parseInt(sdf.format(otherDay));
        return getTime(temp, timesamp, isDynamic);
    }

    private static String getTime(int temp, long timesamp, boolean isDynamic) {
        String result = "";
        switch (temp) {
            case 0:
                result = "今天 " + getHourAndMin(timesamp);
                break;
            case 1:
                result = "昨天 " + getHourAndMin(timesamp);
                break;
            case 2:
                result = "前天 " + getHourAndMin(timesamp);
                break;
            default:
                if (isDynamic) {
                    result = temp + "天前 ";
                } else {
                    result = getNewFormatTime(timesamp);
                }
                break;
        }
        return result;
    }
}
