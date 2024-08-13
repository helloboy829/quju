package cn.codefit.quju.base.util;

import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;

import java.math.BigDecimal;
import java.math.RoundingMode;

/*
 * 地图相关方法
 *
 *  https://blog.csdn.net/YoungLee16/article/details/88540436 java 根据经纬度计算实际距离
 * https://blog.csdn.net/Mli_Mi/article/details/107933711   通过经纬度判断两点的距离 并做离我最近排序
 *
 * https://jingweidu.51240.com/ 经纬度实时查询网站
 *
 * https://lbs.amap.com/console/show/picker 高德经纬度查询
 *
 * http://api.map.baidu.com/lbsapi/getpoint/index.html?qq-pf-to=pcqq.c2c 百度经纬度查询
 * */
public class MapUtils {
    public static void main(String[] args) {
        // //121.717594,31.12055    121.817629,31.090867
        GlobalCoordinates source = new GlobalCoordinates(30.286489, 120.005386);//纬度、经度
        GlobalCoordinates target = new GlobalCoordinates(30.2981, 120.119179);

        Double meter1 = getDistanceMeter(source, target, Ellipsoid.Sphere);
        Double meter2 = getDistanceMeter(source, target, Ellipsoid.WGS84);

        System.out.println("Sphere坐标系计算结果：" + meter1.intValue() + "米");
        System.out.println("WGS84坐标系计算结果：" + meter2.intValue() + "米");

        /*
        * ROUND(
        6378.138 * 2 * ASIN(
            SQRT(
                POW(
                    SIN(
                        (
                            30.281385 * PI() / 180 - latitude * PI() / 180
                        ) / 2
                    ),
                    2
                ) + COS(30.281385 * PI() / 180) * COS(latitude * PI() / 180) * POW(
                    SIN(
                        (
                            120.0063 * PI() / 180 - longitude * PI() / 180
                        ) / 2
                    ),
                    2
                )
            )
        ) * 1000
    )
    *
        * */
        double latitude = 30.297735;//被动坐标 维度
        double longitude = 120.119022;//被动坐标 经度

        double latitude1 = 0L;//查询用户的 维度
        double longitude1 = 0L;//查询用户的 经度
        double PI = 3.14159265358979323846;
        double f = 6378.138 * 2 * Math.asin(
                Math.sqrt(
                        Math.pow(
                                Math.sin(
                                        (
                                                latitude1 * PI / 180 - latitude * PI / 18
                                        ) / 2
                                ),
                                2
                        ) + Math.cos(
                                latitude1 * PI / 180
                        ) * Math.cos(latitude * PI / 180) * Math.pow(
                                Math.sin(
                                        (
                                                longitude1 * PI / 180 - longitude * PI / 180
                                        ) / 2
                                ),
                                2
                        )
                )
        ) * 1000;
        System.out.println("距离:" + f);
    }

    public static double getDistanceMeter(GlobalCoordinates gpsFrom, GlobalCoordinates gpsTo, Ellipsoid ellipsoid) {
        //创建GeodeticCalculator，调用计算方法，传入坐标系、经纬度用于计算距离
        GeodeticCurve geoCurve = new GeodeticCalculator().calculateGeodeticCurve(ellipsoid, gpsFrom, gpsTo);

        return geoCurve.getEllipsoidalDistance();
    }

    /**
     * 根据经纬度计算两个人之间的距离
     *
     * @param latitude1  纬度
     * @param longitude1 经度
     * @param latitude2  纬度
     * @param longitude2 经度
     * @return
     */
    public static String getDistanceByLatitudeAndLongitude(double latitude1, double longitude1, double latitude2, double longitude2) {
        GlobalCoordinates source = new GlobalCoordinates(latitude1, longitude1);//纬度、经度
        GlobalCoordinates target = new GlobalCoordinates(latitude2, longitude2);

        Double meter1 = getDistanceMeter(source, target, Ellipsoid.Sphere);
        String content = "";
        int juli = meter1.intValue();
        if (juli < 1000) {
            content = juli + "米";
        } else {
            content = divide(meter1, new Double(1000), 2) + "km";
        }
        return content;
    }

    /**
     * 两个double类型的数相除，保留两位小数
     *
     * @param a
     * @param b
     * @param scale
     * @return
     */
    public static double divide(double a, double b, int scale) {
        BigDecimal bd1 = new BigDecimal(Double.toString(a));
        BigDecimal bd2 = new BigDecimal(Double.toString(b));
        return bd1.divide(bd2, scale, RoundingMode.HALF_UP).doubleValue();
    }


}
