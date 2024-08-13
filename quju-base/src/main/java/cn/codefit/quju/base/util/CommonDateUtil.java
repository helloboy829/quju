package cn.codefit.quju.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class CommonDateUtil {
    public static LocalDateTime date2LocalDateTime(Date date) {
        Instant instant = date.toInstant();
        return LocalDateTime.ofInstant(instant, ZoneId.of("Asia/Shanghai"));
    }

    public static LocalDate date2LocalDate(Date date) {
        Instant instant = date.toInstant();
        return LocalDateTime.ofInstant(instant, ZoneId.of("Asia/Shanghai")).toLocalDate();
    }

    public static LocalTime date2LocalTime(Date date) {
        Instant instant = date.toInstant();
        return LocalDateTime.ofInstant(instant, ZoneId.of("Asia/Shanghai")).toLocalTime();
    }

    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        Instant instant = localDateTime.atZone(ZoneId.of("Asia/Shanghai")).toInstant();
        return Date.from(instant);
    }

    public static Date localDate2Date(LocalDate localDate) {
        Instant instant = localDate.atStartOfDay().atZone(ZoneId.of("Asia/Shanghai")).toInstant();
        return Date.from(instant);
    }

    public static Date localTime2Date(LocalTime localTime) {
        Instant instant = LocalDateTime.of(LocalDate.now(), localTime).atZone(ZoneId.of("Asia/Shanghai")).toInstant();
        return Date.from(instant);
    }

    public static LocalDateTime parseString2LocalDateTime(String str, String pattern) {
        if (pattern == null) {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return LocalDateTime.parse(str, df);
        }
        return LocalDateTime.parse(str, DateTimeFormatter.ofPattern(pattern));
    }

    //解析带T的时间字符串
    public static Date parseString2Date(String str) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = sdf.parse(str);
        return date;
    }

    /**
     * 获取当天的00:00:00
     *
     * @return
     */
    public static LocalDateTime getDayStart(LocalDateTime time) {
        return time.with(LocalTime.MIN);
    }

    /**
     * 获取当天的23:59:59
     *
     * @return
     */
    public static LocalDateTime getDayEnd(LocalDateTime time) {
//        LocalDateTime localDateTime = LocalDateTime.of(time.getYear(),time.getMonth() , time.getDayOfMonth(), 23, 59, 59,0);
        //默认获取的一条的最后一刻是到微秒级别的，我们比较仅比较到秒，所以需要处理下
//        return time.with(LocalTime.MAX);
        return time.with(LocalTime.MAX).withNano(0);
    }

    /**
     * 获取当天的23:59:59
     *
     * @return
     */
    public static Date getDateDayEnd(Date time) {
        LocalDateTime localDateTime = date2LocalDateTime(time);//1、date 转 localDateTime
        LocalDateTime localDateTimeStart = getDayEnd(localDateTime);
        return CommonDateUtil.localDateTime2Date(localDateTimeStart);
    }

    public static void main(String[] args) {
        Date date = getDateDayEnd(new Date());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(simpleDateFormat.format(date));
    }

    /**
     * 获取某个时间所在月份的某一天的同样的日期
     *
     * @return
     */
    public static LocalDateTime getDayOfMonth(LocalDateTime time, int newDayOfMonth) {

        return LocalDateTime.of(time.getYear(), time.getMonth(), newDayOfMonth, time.getHour(), time.getMinute(), time.getSecond());
    }

    /**
     * 获取某个日期所在周的周一
     *
     * @return
     */
    public static LocalDateTime getFirstDayOrWeek(LocalDateTime time) {

        return time.with(DayOfWeek.MONDAY);
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static Date getCurrentDate() {
        return new Date();
    }

}
