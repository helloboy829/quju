package cn.codefit.quju.base.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class CommonUtil {

    /**
     * 定义分割常量
     * #用于list中每个元素间的分割
     * |用于map中每一个kv对间的分割
     * =用于map中key与value间的分割
     */
    private static final String SEP1 = ",";
    private static final String SEP2 = "|";
    private static final String SEP3 = "=";


    /**
     * 成功唯一的标识
     *
     * @return
     */
    public static String createUUID() {

        return UUID.randomUUID().toString();
    }

    //生成随机数
    public static Integer getCard(int size) {
        Random rand = new Random();//生成随机数
        String cardNnumer = "";
        for (int a = 0; a < size; a++) {
            cardNnumer += rand.nextInt(9) + 1;//生成6位数字
        }
        int userid = Integer.parseInt(cardNnumer);
        return userid;
    }

    /**
     * 根据生日获取年龄
     *
     * @param birthday
     * @return
     * @throws Exception
     */
    public static Integer getAge(String birthday) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date birth = df.parse(birthday);
        Calendar now = Calendar.getInstance();
        Calendar born = Calendar.getInstance();

        now.setTime(new Date());
        born.setTime(birth);

        if (born.after(now)) {
            throw new IllegalArgumentException("Can't be born in the future");
        }

        int age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);
        if (now.get(Calendar.DAY_OF_YEAR) < born.get(Calendar.DAY_OF_YEAR)) {
            age -= 1;
        }
        return age;
    }
}
