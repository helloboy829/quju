package cn.codefit.quju.base.enums;

/**
 * 活动报名支付方式
 */
public enum FeeTypeEnum {
    Online_Pay(1, "在线支付"),
    Xianchang_Pay(2, "现场支付"),
    Free(3, "免费");

    private Integer value;
    private String description;


    FeeTypeEnum(Integer value, String description) {
        this.value = value;
        this.description = description;

    }

    public static FeeTypeEnum getByValue(Integer value) {
        for (FeeTypeEnum item : FeeTypeEnum.class.getEnumConstants()) {
            if (value.equals(item.getValue())) {
                return item;
            }
        }
        return null;
    }

    public Integer getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }


}
