package cn.codefit.quju.base.enums;

/**
 * 通用返回枚举值
 */
public enum ResponseCode {
    SUCCESS("200", "返回成功", "返回成功", "Return Success"),

    FAIL("500", "返回失败", "返回失敗", "Return Failed"),

    SERVICE_UNAVAILABLE("503", "服务器未响应", "服務器未響應", "Service Unavailable"),

    ILLEGAL_ARGUMENT("400", "非法参数", "非法參數", "Illegal Argument"),

    UNAUTHORIZED("401", "认证失败", "認證失敗", "Unauthorized"),

    FORBIDDEN("403", "禁止访问", "Forbidden"),

    TOKEN_EXPIRED("410", "访问令牌已过期", "Token Expired"),

    INCORRECTCREDENTIAL("428", "用户名或密码错误", "User name or password is incorrect"),

    USER_NOT_EXIST("5002", "用户不存在", ""),
    ONLY_POST("400", "仅允许post请求", ""),

    ACCESS_UNAUTHORIZED("5003", "权限不足,请联系管理员"),
    TOKEN_INVALID_OR_EXPIRED("5004", "您的token无效或已过期,请重新获取"),
    TOKEN_ACCESS_FORBIDDEN("5005", "您已被被禁止访问"),
    TOKEN_NO_GRANT_TYPE("5006", "不支持的授权方式"),

    CLIENT_NOT_EXIST("5007", "客户端信息不存在", ""),


    FAIL_COMMON("500", "调用失败,请联系管理员", "调用失败,请联系管理员", "Return Failed"),


    HAS_NO_DATA("50000", "数据不存在"),
    MORE_DICT("60000", "数据字典项重复,请更换字典编码"),
    MORE_DEPT("60001", "部门名称重复,请更换部门名称"),
    MORE_ROLE("60002", "角色编码重复,请更换角色编码"),
    MORE_MENU("60003", "菜单名称重复,请更换角色编码"),

    CIRCLE_CREATE_ERROR("60100", "圈子名称已存在,请更换"),
    ACTIVITY_CREATE_ERROR("60200", "活动名称已存在,请更换"),
    CAN_NOT_UPDATE_ERR("60201", "活动报名费用方式不可修改"),
    CAN_NOT_CANCLE_ERR("60202", "您非当前活动发起人,不可作废"),
    ACTIVITY_PAY_ERR("60203", "签名失败"),

    USER_CREATE_ERROR("60300", "当前账号已被使用,请更换"),
    USER_ROLE_ERROR("60301", "当前仅支持单角色绑定"),
    USER_DELETET_ERROR("60302", "当前用户不允许删除"),
    USER_UPDATE_ERROR("60303", "管理员账号不可被修改"),


    ERR_QUERY_USEE_INFO("60502", "用户信息查询失败"),
    PAY_ORDER_EROR("60503", "不存在对应订单数据"),
    ERR_ORDER_STATUS("60504", "订单状态异常"),


    ERR_USER_LOGIN_NO_STATUS("70000", "您的账户已被禁止使用,如有疑问请联系管理员"),
    ERR_USER_LOGIN_LOCK("70001", "您的账户已被锁定,不可使用,如有疑问请联系管理员"),
    ERR_USER_LOGIN_EXPIRE("70002", "您的账户已过有效期,不可使用,如有疑问请联系管理员"),

    ERPORT_ERROR("80001", "导出失败,查询数据异常"),


    DATABASE_ERROR("5001", "数据库错误", "數據庫錯誤", "Database error");

    private final String code;

    private final String message;

    private String enMessage = "";

    private String tcMessage = "";

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    ResponseCode(String code, String message, String enMessage) {
        this.code = code;
        this.message = message;
        this.enMessage = enMessage;
    }

    ResponseCode(String code, String message, String tcMessage, String enMessage) {
        this.code = code;
        this.message = message;
        this.tcMessage = tcMessage;
        this.enMessage = enMessage;
    }

    public static String getEnumMessage(ResponseCode responseCode) {
        for (ResponseCode farmErrorCode : values()) {
            if (farmErrorCode.equals(responseCode)) {
                return farmErrorCode.message;
            }
        }
        return null;
    }


    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getTcMessage() {
        return tcMessage;
    }

    public String getEnMessage() {
        return enMessage;
    }


}
