package cn.codefit.quju.base.enums;

/**
 * 平台枚举值
 */
public enum PlatformEnum {

    PLATEFORM_0(0, "趣聚后台"),
    PLATEFORM_1(1, "趣聚小程序"),
    PLATEFORM_2(2, "趣聚呦服务号"),
    ;

    private final Integer code;

    private final String message;

    PlatformEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getMessage(PlatformEnum platformEnum) {
        for (PlatformEnum plaftorm : values()) {
            if (plaftorm.equals(platformEnum)) {
                return platformEnum.message;
            }
        }
        return null;
    }

    public static PlatformEnum getByCode(String code) {
        // 通过反射取出Enum所有常量的属性值
        for (PlatformEnum item : PlatformEnum.class.getEnumConstants()) {
            if (code.equals(item.getCode())) {
                return item;
            }
        }
        throw new IllegalArgumentException();
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
