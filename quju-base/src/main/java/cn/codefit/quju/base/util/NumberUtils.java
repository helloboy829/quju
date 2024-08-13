package cn.codefit.quju.base.util;

import org.apache.commons.lang3.StringUtils;

/**
 * https://blog.csdn.net/qq_44750696/article/details/121230073
 * java判断字符串是否为数字的几种方式
 */
public class NumberUtils {
    /**
     * 判断是否为数字可以使用工具类 StringUtils
     * 通过方法 isNumeric 进行判断是否为数字
     *
     * @param str
     * @return
     */
    private static boolean isNumber(String str) {
        return StringUtils.isNumeric(str);
    }

    /**
     * 使用ACSII码；
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            int chr = str.charAt(i);
            if (chr < 48 || chr > 57) {
                return false;
            }
        }
        return true;
    }

    /**
     * ，使用Java自带的函数
     *
     * @param str
     * @return
     */
    public static boolean isNumericD(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 通过抛出异常判断是否是数字
     *
     * @param str
     * @return
     */
    private static boolean isNumberTry(String str) {
        // 这个代码意思是如果没有抛出异常 就证明是数字，抛出异常了那么就不是数字
        // 异常不适合做逻辑判断，不适合做业务逻辑，异常使用不合理，不符合代码规范
        try {
            // parseInt 是将字符串转换为整数类型，返回一个int类型，如果字符串中有非数字类型字符，则会抛出一个NumberFormatException的异常
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 采用正则表达式的方式来判断一个字符串是否为数字，这种方式判断面比较全面，可以判断正负、整数小数 （推荐）
     *
     * @param str
     * @return
     */
    public static boolean isNumericMat(String str) {
        //?:0或1个, *:0或多个, +:1或多个
        Boolean strResult = str.matches("-?[0-9]+.?[0-9]*");
        if (strResult) {
            System.out.println("Is Number!");
        } else {
            System.out.println("Is not Number!");
        }
        return strResult;
    }


}
