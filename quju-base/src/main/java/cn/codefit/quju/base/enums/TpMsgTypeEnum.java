package cn.codefit.quju.base.enums;

/**
 * 模板消息类型
 */
public enum TpMsgTypeEnum {
    APPLY_ACTIVITY_SUCCELL(1, "活动报名成功"),
    CANCEL_ACTIVITY(2, "用户取消参加活动通知"),
    CREATE_ACTIVITY(3, "您的活动已发起成功,请去分享招人吧"),
    ;

    private Integer code;
    private String description;


    TpMsgTypeEnum(Integer code, String description) {
        this.code = code;
        this.description = description;

    }

    public static TpMsgTypeEnum getByValue(Integer code) {
        for (TpMsgTypeEnum item : TpMsgTypeEnum.class.getEnumConstants()) {
            if (code.equals(item.getCode())) {
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
