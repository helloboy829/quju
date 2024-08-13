package cn.codefit.quju.base.enums;

/**
 * 消息通知类型
 */
public enum MsgTypeEnum {

    MSG_WECHAT(1, "微信模板消息通知"),
    MSG_DINGDING(2, "钉钉消息通知"),
    MSG_SMS(3, "短信消息通知"),
    MSG_WEB_SOCKET(4, "webSocket通知");

    private Integer code;
    private String description;


    MsgTypeEnum(Integer code, String description) {
        this.code = code;
        this.description = description;

    }

    public static MsgTypeEnum getByCode(Integer value) {
        for (MsgTypeEnum item : MsgTypeEnum.class.getEnumConstants()) {
            if (value.equals(item.getCode())) {
                return item;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }


}
