package cn.codefit.quju.base.constant;

import lombok.Getter;

public enum PasswordEncoderTypeEnum {

    MD5("{MD5}", "MD5加密"),
    BCRYPT("{bcrypt}", "BCRYPT加密"),
    NOOP("{noop}", "无加密明文");

    @Getter
    private String prefix;

    PasswordEncoderTypeEnum(String prefix, String desc) {
        this.prefix = prefix;
    }

}
