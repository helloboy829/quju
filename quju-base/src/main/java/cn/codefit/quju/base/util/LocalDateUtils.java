package cn.codefit.quju.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 * LocalDateTime工具类
 * LocalDateTime年月日十分秒；LocalDate日期；LocalTime时间；
 * LocalDateTime 类型都带的有毫秒
 */
public class LocalDateUtils {

    public static void main(String[] args) {
        //获取当前时间
        LocalDateTime nowLocalDateTime = LocalDateTime.now();
//        LocalDateTime oFnocalDateTime=LocalDateTime.of(2022,6,15,0,0);
        LocalDateTime oFnocalDateTime = LocalDateTime.of(2022, 10, 16, 0, 0);

        for (int i = 0; i < 8; i++) {
            //1、拆解时间子项
            System.out.println("i = " + dismantleTime(oFnocalDateTime, i));
        }
        //2、比较两个时间的先后
        System.out.println(compareLocalDateTime(oFnocalDateTime, nowLocalDateTime));
        //3、 LocalDateTime 转 LocalTime(时间)
        System.out.println(localdatetimeToTime(nowLocalDateTime));
        //4、 LocalDateTime 转 LocalDate(日期)
        System.out.println(localdatetimeToDate(nowLocalDateTime));
        //5、LocalDateTime 转各种字符串格式
        System.out.println(localDateTimeFormat(nowLocalDateTime, null));
        System.out.println(localDateTimeFormat(nowLocalDateTime, "yyyy/MM/dd"));
        //6、字符串 转 LocalDateTime todo （暂时报错）
//        System.out.println(foramtTolocalDateTime("2022/10/17","yyyy/MM/dd"));
        //7、获取一天的起始和结束一刻
        System.out.println(getDayStart(nowLocalDateTime));
        System.out.println(getDayEnd(nowLocalDateTime));
        //8、LocalDateTime 转 Date
        System.out.println(toDate(getDayEnd(nowLocalDateTime)));
        //9、日期加上一个数，根据field不同加不同值
        System.out.println(plus(getDayEnd(nowLocalDateTime), 1, ChronoUnit.DAYS));
        //10、日期减去一个数，根据field不同加不同值
        System.out.println(minus(getDayEnd(nowLocalDateTime), 1, ChronoUnit.DAYS));

        //11、获取昨天的最后一刻
        LocalDateTime yesterdayTime = minus(getDayEnd(nowLocalDateTime), 1, ChronoUnit.DAYS);
        LocalDateTime yesterdayTimeEnd = getDayEnd(yesterdayTime);
        Date dateEnd = toDate(yesterdayTimeEnd);
        //12、获取昨天两个月前的起始时间
        LocalDateTime twoMonthBefore = minus(getDayStart(yesterdayTime), 2, ChronoUnit.MONTHS);
        LocalDateTime twoMonthBefore2 = plus(twoMonthBefore, 1, ChronoUnit.DAYS);
        Date dateEndtwoMonthBefore = toDate(twoMonthBefore2);

        //13、获取当前日期所在月份的起始时间和结束时间
        System.out.println(getMonthStartOyEnd(nowLocalDateTime, 0));
        System.out.println(getMonthStartOyEnd(nowLocalDateTime, 1));
    }

    /**
     * 获取某个时间所在月份的开始时间和结束时间
     *
     * @param time
     * @param type 0、获取开始时间  1、获取结束时间
     * @return
     */
    public static LocalDateTime getMonthStartOyEnd(LocalDateTime time, int type) {
        if (type == 0) {
            LocalDateTime startDateTime = time.with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
            return startDateTime;
        } else {
            LocalDateTime endDateTime = time.with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);
            return endDateTime;
        }
    }


    /**
     * 日期加上一个数，根据field不同加不同值
     *
     * @param dateTime 日期时间
     * @param number   数值
     * @param field    单位
     * @return 已添加数值后的日期时间
     */
    public static LocalDateTime plus(LocalDateTime dateTime, long number, ChronoUnit field) {
        return dateTime.plus(number, field);
    }

    /**
     * 日期减去一个数，根据field不同减不同值
     *
     * @param dateTime 日期时间
     * @param number   数值
     * @param field    单位
     * @return 已减少数值后的日期时间
     */
    public static LocalDateTime minus(LocalDateTime dateTime, long number, ChronoUnit field) {
        return dateTime.minus(number, field);
    }


    /**
     * LocalDateTime转换为Date
     *
     * @param dateTime 日期时间
     * @return Date
     */
    public static Date toDate(LocalDateTime dateTime) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
        String dateString = simpleDateFormat.format(date);
        Date result = null;//parse():String--->date
        try {
            result = simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Date 转换为 LocalDateTime
     *
     * @param date 日期时间
     * @return LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }


    /**
     * 自定义时间
     *
     * @return
     */
    public static LocalDateTime getCustom(int year, int month, int day, int hour, int minute, int second) {
        return LocalDateTime.of(year, month, day, hour, minute, second);
    }


    /**
     * 获取某天的开始时间，例如：yyyy,MM,dd 00:00
     *
     * @param dateTime 某天的日期时间
     * @return 某天的开始时间
     */
    public static LocalDateTime getDayStart(LocalDateTime dateTime) {
        return LocalDateTime.of(dateTime.toLocalDate(), LocalTime.MIN);
    }

    /**
     * 获取某天的结束时间，例如：yyy,MM,dd 23:59:59
     *
     * @param dateTime 某天的日期时间
     * @return 某天的结束时间
     */
    public static LocalDateTime getDayEnd(LocalDateTime dateTime) {
        return LocalDateTime.of(dateTime.toLocalDate(), LocalTime.MAX);
    }


    /**
     * 字符串 转 LocalDateTime
     *
     * @param string
     * @param pattern
     * @return
     */
    private static LocalDateTime foramtTolocalDateTime(String string, String pattern) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(string, df);
    }

    /**
     * LocalDateTime 转各种字符串格式
     *
     * @param time
     * @param pattern
     * @return
     */
    private static String localDateTimeFormat(LocalDateTime time, String pattern) {
        if (pattern == null) {
            pattern = "yyyy-MM-dd HH:mm:ss";
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        return df.format(time);
    }

    /**
     * LocalDateTime 转 LocalDate
     *
     * @param time
     * @return (注意带了毫秒)
     */
    private static LocalDate localdatetimeToDate(LocalDateTime time) {
        LocalDate localDate = time.toLocalDate();
        System.out.println("localDate = " + localDate.toString());
        return localDate;
    }

    /**
     * LocalDateTime 转 LocalTime
     *
     * @param time
     * @return (注意带了毫秒)
     */
    private static LocalTime localdatetimeToTime(LocalDateTime time) {
        LocalTime localTime = time.toLocalTime();
        System.out.println("localTime = " + localTime.toString());
        return localTime;
    }


    /**
     * 获取两个日期的差
     *
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     * @param field         单位
     * @return 两个日期之间的差值
     */
    public static long between(LocalDateTime startDateTime, LocalDateTime endDateTime, ChronoUnit field) {
        Period period = Period.between(LocalDate.from(startDateTime), LocalDate.from(endDateTime));
        if (field == ChronoUnit.YEARS) {
            return period.getYears();
        }
        if (field == ChronoUnit.MONTHS) {
            return period.getYears() * 12L + period.getMonths();
        }
        return field.between(startDateTime, endDateTime);
    }


    /**
     * 比较两个时间的先后
     *
     * @param time1
     * @param time2
     * @return 0、相等
     * -1、time1小于time2
     * 1、tim1大于time2
     */
    public static Integer compareLocalDateTime(LocalDateTime time1, LocalDateTime time2) {
        if (time1.equals(time2)) {
            return 0;
        } else if (time1.isBefore(time2)) {
            return -1;
        } else {
            return 1;
        }
    }

    /**
     * 拆解时间子项
     *
     * @param time
     * @param type 0、获取年份
     *             1、获取月份
     *             2、获取日期为所在月的几号
     *             3、获取日期为周几(LocalDateTime---星期一为周一)
     *             4、获取日期为当年的第几天
     *             5、获取小时
     *             6、获取分钟
     *             7、获取秒
     * @return
     */
    public static Integer dismantleTime(LocalDateTime time, int type) {
        switch (type) {
            case 0:
                return time.getYear();
            case 1:
                return time.getMonthValue();
            case 2:
                return time.getDayOfMonth();
            case 3:
                return time.getDayOfWeek().getValue();
            case 4:
                return time.getDayOfYear();
            case 5:
                return time.getHour();
            case 6:
                return time.getMinute();
            case 7:
                return time.getSecond();
            default:
                return 0;
        }
    }


}
