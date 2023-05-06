package com.ikikyou.practice.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @author hongx
 * @date 2023/04/20 10:28
 */
public class DateUtil {

    /**
     * 获取指定时间到零点的秒数
     * @param currentDate 指定时间
     */
    public static long getRemainSecondsOneDay(Date currentDate) {
        //使用plusDays加传入的时间加1天，将时分秒设置成0
        LocalDateTime midnight = LocalDateTime.ofInstant(currentDate.toInstant(), ZoneId.systemDefault())
                .plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime currentDateTime = LocalDateTime.ofInstant(currentDate.toInstant(), ZoneId.systemDefault());
        return ChronoUnit.SECONDS.between(currentDateTime, midnight);
    }

    /**
     * 获取今天到零点的秒数
     */
    public static long getRemainSecondsOneDay() {
        return getRemainSecondsOneDay(new Date());
    }
}
