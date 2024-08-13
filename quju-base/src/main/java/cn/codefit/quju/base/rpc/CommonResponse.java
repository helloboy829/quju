package cn.codefit.quju.base.rpc;

import cn.codefit.quju.base.enums.ResponseCode;
import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回参数
 */
@Data
public class CommonResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private String code;// 返回码

    private String msg;// 返回消息

    private T data;// 数据

    public CommonResponse(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> CommonResponse success(T data) {
        return new CommonResponse(ResponseCode.SUCCESS.getCode(),
                ResponseCode.SUCCESS.getMessage(), data);
    }


    public static <T> CommonResponse success(T data, String msg) {
        return new CommonResponse(ResponseCode.SUCCESS.getCode(), msg, data);
    }

    public static CommonResponse success() {
        return success(null);
    }

    public static <T> CommonResponse fail(T data) {
        return new CommonResponse(ResponseCode.FAIL.getCode(),
                ResponseCode.FAIL.getMessage(), data);
    }

    public static <T> CommonResponse fail() {
        return fail(null);
    }

    public static <T> CommonResponse error(String msg) {
        return new CommonResponse(ResponseCode.FAIL.getCode(), msg, null);
    }

    public static <T> CommonResponse error(String code, String msg) {
        return new CommonResponse<T>(code, msg, null);
    }

    public static <T> CommonResponse error(String code, String msg, T data) {
        return new CommonResponse<T>(code, msg, data);
    }

    public static <T> CommonResponse error(ResponseCode responseCode) {
        return new CommonResponse(responseCode.getCode(), responseCode.getMessage(), null);
    }

    public static boolean isSuccess(CommonResponse<?> result) {
        return result != null && ResponseCode.SUCCESS.getCode().equals(result.getCode());
    }
}
