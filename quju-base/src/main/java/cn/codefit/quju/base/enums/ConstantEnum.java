package cn.codefit.quju.base.enums;

/**
 * 通用返回枚举值
 */
public enum ConstantEnum {


    IS_DELETE_NO(0, "未删除"),
    IS_DELETE_YES(1, "已删除"),
    ;

    private final Integer code;

    private final String message;

    ConstantEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
