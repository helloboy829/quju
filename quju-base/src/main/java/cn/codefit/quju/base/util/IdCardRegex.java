package cn.codefit.quju.base.util;


import cn.hutool.core.bean.BeanUtil;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IdCardRegex {

    private final String regex = "^((([1-9]{2})(\\d{2})(\\d{2}))([1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1]))(\\d{3}))([0-9]|[Xx])$";
    //private String regex = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$";

    //每位数的加权因子
    private final int[] power = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

    //补码表
    private final char[] comp = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};

    private static final Properties districts = new Properties();

    private final SimpleDateFormat birthdayFmtForCard = new SimpleDateFormat("yyyyMMdd");

    private final SimpleDateFormat birthdayFmtForOut = new SimpleDateFormat("yyyy年M月d日");

    private String error = "无效的身份证号码";

    private boolean validate = false;

    //提取出来的信息
    private final String cardNo;   //身份证号
    private String addr;     //住址
    private String sex;      //性别
    private String birthday; //出生年月
    private int age;         //计算年龄

    //这里是原始信息
    private String simpleCode;   //身份证前17位
    private String birthCode;    //生日
    private String seriCode;         //顺序码
    private String districtCode;    //行政编码
    private String province; //省
    private String city;     //市
    private String county; //区(县)
    private String valiCode;    //识别码


    public IdCardRegex(String cardNo) {
        this.cardNo = cardNo;
        this.validate();
    }

   /* static{
        InputStream is = IdCardRegex.class.getClassLoader().getResourceAsStream("district.properties");

        InputStreamReader isr = null;

        try {
            isr = new InputStreamReader(is,"GBK");
            districts.load(isr);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                is.close();
                isr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }*/


    //开始校验
    private void validate() {

        //验证格式
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(this.cardNo);
        if (m.matches()) {

//            for (int i = 1; i <= m.groupCount(); i ++){
//                System.out.println(i + "," + m.group(i));
//            }

            //前17位
            this.simpleCode = m.group(1);

            //行政代码
            this.districtCode = m.group(2);

//            //省
//            this.provinceCode = m.group(3);
//            //市
//            this.cityCode = m.group(4);
//            //区(县)
//            this.countyCode = m.group(5);

            //出生年月
            this.birthCode = m.group(6);
            //顺序码
            this.seriCode = m.group(12);

            //识别码
            this.valiCode = m.group(13);

        } else {
            this.error = "身份证号为18位";
            return;
        }

        //校验行政区划(必须是有效的行政区划)
        if (!districts.containsKey(this.districtCode)) {
            this.error = "行政编码错误";
            return;
        } else {
//        	StringBuffer sb = new StringBuffer();
//        	sb.append(districts.getProperty(this.provinceCode + "0000"));
//        	sb.append(districts.getProperty(this.provinceCode + this.cityCode + "00"));
//        	sb.append(districts.getProperty(this.provinceCode + this.cityCode + this.countyCode));
            this.addr = districts.getProperty(this.districtCode);
//        	this.addr = sb.toString();
        }

        //校验出生年月(可以是今天，必须小于明天)
        try {
            Date birthDate = birthdayFmtForCard.parse(this.birthCode);

            Calendar birthday = Calendar.getInstance();
            birthday.setTime(birthDate);

            Calendar today = Calendar.getInstance();

            if (birthDate.after(today.getTime())) {
                this.error = "你不能出生在未来";
                return;
            }

            this.birthday = birthdayFmtForOut.format(birthDate);
            this.age = today.get(Calendar.YEAR) - birthday.get(Calendar.YEAR);
        } catch (Exception e) {
            this.error = "出生年月格式不对";
            return;
        }


        //校验性别
        this.sex = Integer.valueOf(this.seriCode) % 2 == 0 ? "女" : "男";


        //验证补码
        int sum = 0;
        char[] ids = this.simpleCode.toCharArray();
        for (int i = 0; i < ids.length; i++) {
            sum += Integer.valueOf("" + ids[i]) * power[i];
        }
        if (!Character.toString(comp[sum % 11]).equals(valiCode)) {
            this.error = "识别码错误";
            return;
        }


        this.validate = true;
    }

    /**
     * 解析地址
     *
     * @param address
     * @return
     */
    public static Map<String, String> addressResolution(String address) {
        String regex = "(?<province>[^省]+自治区|.*?省|.*?行政区|.*?市)(?<city>[^市]+自治州|.*?地区|.*?行政单位|.+盟|市辖区|.*?市|.*?县)(?<county>[^县]+县|.+区|.+市|.+旗|.+海域|.+岛)?(?<town>[^区]+区|.+镇)?(?<village>.*)";
        Matcher m = Pattern.compile(regex).matcher(address);
        String province = null, city = null, county = null, town = null, village = null;
//        List<Map<String,String>> table=new ArrayList<Map<String,String>>();
        Map<String, String> row = null;
        while (m.find()) {
            row = new LinkedHashMap<String, String>();
            province = m.group("province");
            row.put("province", province == null ? "" : province.trim());
            city = m.group("city");
            row.put("city", city == null ? "" : city.trim());
            county = m.group("county");
            row.put("county", county == null ? "" : county.trim());
            town = m.group("town");
            row.put("town", town == null ? "" : town.trim());
            village = m.group("village");
            row.put("village", village == null ? "" : village.trim());

        }
        return row;
    }

    public Identity getInfo() {

        if (!this.validate) {
            return null;
        }

        StringBuffer sb = new StringBuffer();
        if (null == this.addr || "".equals(this.addr.trim())) {
            return null;
        }
        Map<String, String> sm = addressResolution(this.addr);

        province = sm.get("province");
        city = sm.get("city");
        county = sm.get("county");
        sb.append("身份证号:" + this.cardNo + "\n");
        sb.append("出生年月:" + this.birthday + "\n");
        sb.append("年龄:" + this.age + "岁\n");
        sb.append("性别:" + this.sex + "\n");
        sb.append("住址:" + this.addr + "\n");
        sb.append("省:" + this.province + "\n");
        sb.append("市:" + this.city + "\n");
        sb.append("县:" + this.county + "\n");
        Identity i = new Identity();
        i.addr = this.addr;
        i.age = this.age;
        i.birthday = this.birthday;
        i.cardNo = this.cardNo;
        i.province = province;
        i.city = city;
        i.county = county;
        i.sex = sex;
        return i;
    }

    @Data
    public static class Identity {
        private String province;
        private String city;
        private String county;
        private String addr;
        private String sex;
        private int age;
        private String birthday;
        private String cardNo;
    }

    public static void main(String[] args) {
        IdCardRegex idCardRegex = new IdCardRegex("410221199701107610");
        System.out.println(idCardRegex.getInfo());
        Identity i = BeanUtil.copyProperties(idCardRegex.getInfo(), Identity.class);
        System.out.println(i);
    }
}
