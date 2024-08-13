package cn.codefit.quju.base.enums;

/**
 * 微信模板枚举值
 */
public enum WechatTemplateEnum {

    TEMPLATE_ACTIVITY_CREATE("KWmqLj4we_VDRMAnIkHDBrcjbI7DkYpsJnzik1SMwxM", "活动发起(创建)成功"),
    TEMPLATE_ACTIVITY_JOIN("d2qVF60-NhQYewpnJmntTQyjayZVyurhWKhtKOpJ1Ms", "有新朋友报名成功活动了"),
    ;

    private final String code;

    private final String desc;

    WechatTemplateEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static WechatTemplateEnum getByCode(String code) {
        // 通过反射取出Enum所有常量的属性值
        for (WechatTemplateEnum item : WechatTemplateEnum.class.getEnumConstants()) {
            if (code.equals(item.getCode())) {
                return item;
            }
        }
        throw new IllegalArgumentException();
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
