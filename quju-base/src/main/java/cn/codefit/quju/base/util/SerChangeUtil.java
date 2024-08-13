package cn.codefit.quju.base.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

public class SerChangeUtil {

    public static String getCity(Integer cityCode) {
        switch (cityCode) {
            case 1:
                return "杭州";
            case 2:
                return "苏州";
            default:
                return "未知";
        }
    }

    /**
     * 活动时间查询范围
     *
     * @param dateCode 1、今天
     *                 2、明天
     *                 3、本周
     *                 4、本周末
     *                 5、本月
     * @return
     */
    public static SearchDate getDate(Integer dateCode) {
        switch (dateCode) {
            case 1:
                return new SearchDate(LocalDateTime.of(LocalDate.now(), LocalTime.MIN), LocalDateTime.of(LocalDate.now(), LocalTime.MAX));
            case 2:
                return new SearchDate(LocalDateTime.of(LocalDate.from(LocalDateTime.now().plusDays(1)), LocalTime.MIN), LocalDateTime.of(LocalDate.from(LocalDateTime.now().plusDays(1)), LocalTime.MAX));
            case 3:
                return new SearchDate(LocalDateTime.of(LocalDate.now().minusDays(LocalDate.now().getDayOfWeek().getValue() - 1), LocalTime.MIN), LocalDateTime.of(LocalDate.now().plusDays(7 - LocalDate.now().getDayOfWeek().getValue()), LocalTime.MAX));
            case 4:
                return new SearchDate(LocalDateTime.of(LocalDate.now().plusDays(7 - LocalDate.now().getDayOfWeek().getValue() - 1), LocalTime.MIN), LocalDateTime.of(LocalDate.now().plusDays(7 - LocalDate.now().getDayOfWeek().getValue()), LocalTime.MAX));
            case 5:
                return new SearchDate(LocalDateTime.of(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()), LocalTime.MIN), LocalDateTime.of(LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()), LocalTime.MAX));
            default:
                return null;
        }
    }


    public static class SearchDate {
        public SearchDate(LocalDateTime startTime, LocalDateTime endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public LocalDateTime getStartTime() {
            return startTime;
        }

        public void setStartTime(LocalDateTime startTime) {
            this.startTime = startTime;
        }

        public LocalDateTime getEndTime() {
            return endTime;
        }

        public void setEndTime(LocalDateTime endTime) {
            this.endTime = endTime;
        }

        LocalDateTime startTime;
        LocalDateTime endTime;
    }


//    private static double EARTH_RADIUS = 6378.137;
//
//    private static double rad(double d) {
//        return d * Math.PI / 180.0;
//    }

    /**
     * 通过经纬度获取距离(单位：米)
     *
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return 距离
     */
//    public static double getDistance(double lat1, double lng1, double lat2,
//                                     double lng2) {
//        double radLat1 = rad(lat1);
//        double radLat2 = rad(lat2);
//        double a = radLat1 - radLat2;
//        double b = rad(lng1) - rad(lng2);
//        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
//                + Math.cos(radLat1) * Math.cos(radLat2)
//                * Math.pow(Math.sin(b / 2), 2)));
//        s = s * EARTH_RADIUS;
//        s = Math.round(s * 10000d) / 10000d;
//        s = s * 1000;
//        return s;
//    }

    //地球平均半径
    private static final double EARTH_RADIUS = 6378137;

    //把经纬度转为度（°）
    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 根据两点间经纬度坐标（double值），计算两点间距离，单位为千米
     *
     * @param lng1
     * @param lat1
     * @param lng2
     * @param lat2
     * @return
     */
    public static double getDistance(double lng1, double lat1, double lng2, double lat2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(
                Math.sqrt(
                        Math.pow(Math.sin(a / 2), 2)
                                + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)
                )
        );
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000000;
        return s;
    }

    public static String getData(LocalDateTime data) {

        LocalDate ldata = data.toLocalDate();
        LocalDate today = LocalDate.now();
        LocalDate today1 = LocalDate.of(today.getYear(), today.getMonth(), today.getDayOfMonth());
        LocalDate tomorrow = today1.plusDays(1);
        LocalDate nextNextDay = today1.plusDays(2);
        //周六
        LocalDate liu = LocalDate.now().plusDays(7 - LocalDate.now().getDayOfWeek().getValue() - 1);
        //周日
        LocalDate muo = LocalDate.now().plusDays(7 - LocalDate.now().getDayOfWeek().getValue());

        if (today1.equals(ldata)) {
            return "今天 " + data.getHour() + ":" + (data.getMinute() >= 10 ? data.getMinute() : "0" + data.getMinute());
        }
        if (tomorrow.equals(ldata)) {
            return "明天 " + data.getHour() + ":" + (data.getMinute() >= 10 ? data.getMinute() : "0" + data.getMinute());
        }
        if (nextNextDay.equals(ldata)) {
            return "后天 " + data.getHour() + ":" + (data.getMinute() >= 10 ? data.getMinute() : "0" + data.getMinute());
        }
        if (liu.equals(ldata)) {
            return "周六 " + data.getHour() + ":" + (data.getMinute() >= 10 ? data.getMinute() : "0" + data.getMinute());
        }
        if (muo.equals(ldata)) {
            return "周日 " + data.getHour() + ":" + (data.getMinute() >= 10 ? data.getMinute() : "0" + data.getMinute());
        }
        return ldata.toString().substring(5, 10) + " " + data.getHour() + ":" + (data.getMinute() >= 10 ? data.getMinute() : "0" + data.getMinute());
    }

    public static void main(String[] args) {
//        new SerChangeUtil().getDate(2);
//      LocalDateTime d=LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth());
//        double distance = getDistance(34.2675560000, 108.9534750000,
//                34.2464320000, 108.9534750000);
//        System.out.println("距离" + distance / 1000 + "公里");
        LocalDateTime today = LocalDateTime.now().plusDays(2);
        System.out.println(today);
        System.out.println(getData(today));

    }

}
