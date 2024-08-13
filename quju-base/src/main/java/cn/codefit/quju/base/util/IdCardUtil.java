package cn.codefit.quju.base.util;

public class IdCardUtil {
    private static String IdNum = null;
    private static Boolean flag = false;
    private static String changed;

    //校验身份证
    public static boolean Verify1(String s1) {

        String[] code = {"1", "0", "x", "9", "8", "7", "6", "5", "4", "3", "2"};//校验码
        String[] wi = {"7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2", "1"};//加权因子
        int verifyNum = 0;
        int m = 0, n = 17;
        IdNum = s1;

        char[] id = IdNum.toCharArray();

        //检验格式

        if (IdNum.length() != 15 && IdNum.length() != 18) {
            System.out.println("请输入正确的身份证位数（15或者18位）");
            flag = false;
            return flag;
        }
        if (IdNum.length() == 15) {
            if (!(IdNum.matches("[0-9]{17}[0-9|x]|[0-9]{15}"))) {
                System.out.println("请输入正确格式的15位身份证号码");
                flag = false;
                return flag;
            } else {
                //15位转换为18位
                String s2 = IdNum.substring(0, 6);
                String s3 = IdNum.substring(6, 15);
                changed = s2.concat("19").concat(s3);
                char[] c_Id = changed.toCharArray();
                for (; m < n; m++) {
                    verifyNum += (Integer.parseInt(wi[m]) * Integer.parseInt(String.valueOf(c_Id[m])));
                }
                int y = verifyNum % 11;
                changed = changed.concat(code[y]);

                flag = true;
                return flag;
            }
        }
        if (IdNum.length() == 18) {
            if (!(IdNum.matches("[0-9]{17}[0-9|x]|[0-9]{15}"))) {
                System.out.println("请输入正确格式的18位身份证号码");
                flag = false;
                return flag;
            } else {
                for (; m < n; m++) {
                    verifyNum += (Integer.parseInt(wi[m]) * Integer.parseInt(String.valueOf(id[m])));
                }
                int y = verifyNum % 11;
                if (code[y].equals(IdNum.substring(17, 18))) {
                    flag = true;
                    return flag;
                } else {
                    System.out.println("请输入合法的身份证号码!");
                    flag = false;
                    return flag;
                }
            }
        }
        return flag;

    }

    public static void ShowInfo() {
        if (IdNum.length() == 15) {
            System.out.println("15位身份证自动转化成18位...");
            System.out.println("你的18位身份证是：" + changed);
        } else {
            System.out.println("你的身份证是：" + IdNum);
            System.out.print("你的出生日期是:" + IdNum.substring(6, 10) + "年" + IdNum.substring(10, 12) + "月" + IdNum.substring(12, 14) + "日\n");
            String sex;
            if (Integer.parseInt(IdNum.substring(16, 17)) % 2 == 0) sex = "女士";
            else sex = "男士";
            System.out.print("你的性别是:" + sex);
        }
    }

    public static void main(String[] args) {
        boolean r = Verify1("411524199101023217");
        if (r) {
            ShowInfo();
        }
    }
}
