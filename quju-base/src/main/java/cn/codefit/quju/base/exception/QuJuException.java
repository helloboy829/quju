package cn.codefit.quju.base.exception;


import cn.codefit.quju.base.enums.ResponseCode;

public class QuJuException extends RuntimeException {
    public QuJuException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public QuJuException(ResponseCode responseCode) {
        this(responseCode.getCode(), ResponseCode.getEnumMessage(responseCode));
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    private String code;

    private String message;
}
