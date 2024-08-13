package cn.codefit.quju.base.util;

import com.google.common.base.CaseFormat;

/**
 * java驼峰转换工具类
 */
public class GuavaUtils {


    /**
     * 驼峰转数据库字段工具类
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("CaseFormat = " + transformToDatabaseFieldUpper("createTime"));
        System.out.println("CaseFormat = " + transformToDatabaseFieldLower("createTime"));
    }

    /**
     * 驼峰转数据库-大写
     *
     * @param name
     * @return
     */
    public static String transformToDatabaseFieldUpper(String name) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name).toUpperCase();
    }

    /**
     * 驼峰转数据库-小写
     *
     * @param name
     * @return
     */
    public static String transformToDatabaseFieldLower(String name) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name).toLowerCase();
    }

}
