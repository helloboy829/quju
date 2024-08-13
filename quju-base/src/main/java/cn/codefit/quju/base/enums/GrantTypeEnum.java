package cn.codefit.quju.base.enums;

import cn.codefit.quju.base.exception.QuJuException;

/**
 * oauth2认证方式-枚举值
 */
public enum GrantTypeEnum {

    GRANT_TYPE_WEEK_WECHAT("wechat", "趣聚小程序-微信登录模式"),
    GRANT_TYPE_LOVE_PASWORD("lovePassword", "账号密码登录模式"),
    GRANT_TYPE_LOVE_SMS("lovesms", "短信登录模式"),
    GRANT_TYPE_WEEK_PASSWORD("password", "默认账号密码登录模式"),
    GRANT_TYPE_REFRESH_TOKEN("refresh_token", "刷新令牌"),
    ;

    private final String code;

    private final String desc;

    GrantTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static GrantTypeEnum getByCode(String colorCode) {
        // 通过反射取出Enum所有常量的属性值
        for (GrantTypeEnum item : GrantTypeEnum.class.getEnumConstants()) {
            if (colorCode.equals(item.getCode())) {
                return item;
            }
        }
        throw new QuJuException(ResponseCode.TOKEN_NO_GRANT_TYPE);
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
