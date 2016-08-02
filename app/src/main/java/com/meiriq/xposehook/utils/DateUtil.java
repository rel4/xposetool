package com.meiriq.xposehook.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by tian on 15-12-11.
 */
public class DateUtil {

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat format_date = new SimpleDateFormat("yyyy-MM-dd");
    public static int DAYTIMR_NO_TIME = 0x1<<1;
    public static int DAYTIMR_START_IN_DAY = 0x1<<2;
    public static int DAYTIMR_END_IN_DAY = 0x1<<3;


    public static String getCurDate(){
        return format_date.format(new Date());
    }


    public static String getCurDate(int offsetDay){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - offsetDay);
        return format_date.format(calendar.getTime());
    }

    public static String getCurTime(){
        return format_date.format(new Date());
    }

    public static String getCurTime(long time){
        return format.format(new Date(time));
    }

    /**
     * 和getCurTime匹配相互转换
     * @param time
     * @return
     */
    public static long getCurrLongTime(String time){
        try {
            Date date = format.parse(time);
            return date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取制定天数前的时间　
     * @param offsetDay　n天前
     * @return
     */
    public static long getTimeMillis(int offsetDay){

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - offsetDay);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取日期
     * @param diff 0表示今天，1表示昨天
     *             tag 标示是开始时间还是结束时间
     *@deprecated getDateTime(int diff,int hour,int minute,int second)
     * @return
     */
    @Deprecated
    public static String getDateTime(int diff,int tag){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - diff);
        if(tag == DAYTIMR_START_IN_DAY){
            calendar.set(Calendar.HOUR_OF_DAY,0);
        }else if(tag == DAYTIMR_END_IN_DAY){
            calendar.set(Calendar.HOUR_OF_DAY,23);
        }
        return format.format(calendar.getTime());
    }

    @Deprecated
    public static String getDateTime(int diff,int hour,int minute,int second){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - diff);
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,second);
        return format.format(calendar.getTime());
    }

    public static String getDateTime(int diff,int hour,int minute){
        long dateTimeMills = getDateTimeMills(diff, hour, minute, 0);
        return format.format(new Date(dateTimeMills));
    }

    public static long getDateTimeMills(int diff,int hour,int minute,int second){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - diff);
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,second);
        return calendar.getTimeInMillis();
    }


}
