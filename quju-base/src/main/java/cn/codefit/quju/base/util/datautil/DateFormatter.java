package cn.codefit.quju.base.util.datautil;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 日期格式化工具
 */
public interface DateFormatter {

    List<DateFormatter> supportFormatter = new ArrayList<>(Arrays.asList(
            /*
             * 最常用格式
             */
            // yyyy-MM-dd
            new DefaultDateFormatter(Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}"), "yyyy-MM-dd", false)
            // yyyy-MM-dd HH:mm:ss
            , new DefaultDateFormatter(Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}"), "yyyy-MM-dd HH:mm:ss", true)
            /*
             * 常见格式
             */
            // yyyyMMdd
            , new DefaultDateFormatter(Pattern.compile("[0-9]{4}[0-9]{2}[0-9]{2}"), "yyyyMMdd", false)
            // yyyy/MM/dd
            , new DefaultDateFormatter(Pattern.compile("[0-9]{4}/[0-9]{2}/[0-9]{2}"), "yyyy/MM/dd", false)
            //yyyy年MM月dd日
            , new DefaultDateFormatter(Pattern.compile("[0-9]{4}年[0-9]{2}月[0-9]{2}日"), "yyyy年MM月dd日", false)
            //yyyy年M月d日
            , new DefaultDateFormatter(Pattern.compile("[0-9]{4}年[0-9]月[0-9]日"), "yyyy年M月d日", false)
            //yyyy年MM月d日
            , new DefaultDateFormatter(Pattern.compile("[0-9]{4}年[0-9]{2}月[0-9]日"), "yyyy年MM月d日", false)
            //yyyy年M月dd日
            , new DefaultDateFormatter(Pattern.compile("[0-9]{4}年[0-9]月[0-9]{2}日"), "yyyy年M月dd日", false)
            // yyyy-M-d
            , new DefaultDateFormatter(Pattern.compile("[0-9]{4}-[0-9]-[0-9]"), "yyyy-M-d", false)
            // yyyy-MM-d
            , new DefaultDateFormatter(Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]"), "yyyy-MM-d", false)
            // yyyy-M-dd
            , new DefaultDateFormatter(Pattern.compile("[0-9]{4}-[0-9]-[0-9]{2}"), "yyyy-M-dd", false)
            // yyyy/M/d
            , new DefaultDateFormatter(Pattern.compile("[0-9]{4}/[0-9]/[0-9]"), "yyyy/M/d", false)
            // yyyy/MM/d
            , new DefaultDateFormatter(Pattern.compile("[0-9]{4}/[0-9]{2}/[0-9]"), "yyyy/MM/d", false)
            // yyyy/M/dd
            , new DefaultDateFormatter(Pattern.compile("[0-9]{4}/[0-9]/[0-9]{2}"), "yyyy/M/dd", false)
            // yyyy/MM/dd HH:mm:ss
            , new DefaultDateFormatter(Pattern.compile("[0-9]{4}/[0-9]{2}/[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}"), "yyyy/MM/dd HH:mm:ss", true)
            // yyyy-MM-dd HH:mm:ssZ
            , new DefaultDateFormatter(Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}\\+[0-9]{4}"), "yyyy-MM-dd HH:mm:ssZ", true)
            //yyyy-MM-dd'T'HH:mm:ss
            , new DefaultDateFormatter(Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}"), "yyyy-MM-dd'T'HH:mm:ss", true)
            //yyyy-MM-dd'T'HH:mm:ssZ
            , new DefaultDateFormatter(Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}\\+[0-9]{4}"), "yyyy-MM-dd'T'HH:mm:ssZ", true)
            //yyyy-MM-dd'T'HH:mm:ss.SSSZ,2018-08-14T18:59:09.709+0800
            , new DefaultDateFormatter(Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}.[0-9]{3}\\+[0-9]{4}"), "yyyy-MM-dd'T'HH:mm:ss.SSSZ", true)
            //yyyy-MM-dd'T'HH:mm:ss.SSSXXX,2018-08-14T18:59:09.709Z
            , new DefaultDateFormatter(Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}.[0-9]{3}Z"), "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", true)
            //yyyy年MM月dd日HH时mm分ss秒
            , new DefaultDateFormatter(Pattern.compile("[0-9]{4}年[0-9]{2}月[0-9]{2}日[0-9]{2}时[0-9]{2}分[0-9]{2}秒"), "yyyy年MM月dd日HH时mm分ss秒", true)
            //yyyy年MM月dd日 HH时mm分ss秒
            , new DefaultDateFormatter(Pattern.compile("[0-9]{4}年[0-9]{2}月[0-9]{2}日 [0-9]{2}时[0-9]{2}分[0-9]{2}秒"), "yyyy年MM月dd日 HH时mm分ss秒", true)
            /*
             * 奇奇怪怪的格式
             */
            // yyyyMMdd HH:mm:ss
            , new DefaultDateFormatter(Pattern.compile("[0-9]{4}[0-9]{2}[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}"), "yyyyMMdd HH:mm:ss", true)
            // yyyyMMddHHmmdss
            , new DefaultDateFormatter(Pattern.compile("[0-9]{4}[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2}"), "yyyyMMddHHmmss", true)
            // yyyyMMdd HHmmss
            , new DefaultDateFormatter(Pattern.compile("[0-9]{4}[0-9]{2}[0-9]{2} [0-9]{2}[0-9]{2}[0-9]{2}"), "yyyyMMdd HHmmss", true)
            // yyyy-M-dd HH:mm:ss
            , new DefaultDateFormatter(Pattern.compile("[0-9]{4}-[0-9]-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}"), "yyyy-M-dd HH:mm:ss", true)
    ));

    boolean support(String dateString);

    Date format(String dateString) throws ParseException;

    DateFormat getDateFormat();

    String getFormat();

    boolean isContainTime();

    static Date fromString(String dateString) {
        DateFormatter formatter = getFormatter(dateString);
        if (formatter == null)
            throw new RuntimeException("Unsupported date format for " + dateString);
        try {
            return formatter.format(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    static boolean isSupport(String dateString) {
        return !(dateString == null || dateString.length() < 4) && supportFormatter.parallelStream().anyMatch(formatter -> formatter.support(dateString));
    }

    static DateFormatter getFormatter(String dateString) {
        if (dateString == null || dateString.length() < 4) return null;
        for (DateFormatter formatter : supportFormatter) {
            if (formatter.support(dateString))
                return formatter;
        }
        return null;
    }
}
