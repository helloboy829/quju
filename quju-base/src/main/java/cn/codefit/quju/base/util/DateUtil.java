package cn.codefit.quju.base.util;

public class DateUtil {


    /**
     * 获取一个double类型的时间字段
     *
     * @return
     */
    public static double getDoubleTimeStamp() {
        double d = Double.valueOf(timeStamp());
        return d;
    }


    /**
     * 取得当前时间戳（精确到秒）
     *
     * @return
     */
    public static String timeStamp() {
        long time = System.currentTimeMillis();
        String t = String.valueOf(time / 1000);
        return t;
    }


}


