package cn.codefit.quju.base.enums;

/**
 * 平台枚举值
 */
public enum ColorEnum {

    COLOR_BLUE("#173177", "蓝色"),
    PLATEFORM_RED("#FF0000", "红色"),
    ;

    private final String colorCode;

    private final String desc;

    ColorEnum(String colorCode, String desc) {
        this.colorCode = colorCode;
        this.desc = desc;
    }

    public static ColorEnum getByCode(String colorCode) {
        // 通过反射取出Enum所有常量的属性值
        for (ColorEnum item : ColorEnum.class.getEnumConstants()) {
            if (colorCode.equals(item.getColorCode())) {
                return item;
            }
        }
        throw new IllegalArgumentException();
    }

    public String getColorCode() {
        return colorCode;
    }

    public String getDesc() {
        return desc;
    }
}
