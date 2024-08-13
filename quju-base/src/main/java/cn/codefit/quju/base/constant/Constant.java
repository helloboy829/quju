package cn.codefit.quju.base.constant;

/**
 * 常量
 */
public abstract class Constant {

    /**
     * 系统接口版本号
     */
    public static final String VERSION = "66011305";

    public static final String SHARD_DB = "city_code";
    public static final String SHARD_TABLE2 = "create_time";
    public static final String SHARD_TABLES = "city_code,create_time";
    /**
     * 超级管理员账号
     */
    public static final String USERNAME_SUPER_ADMIN = "root";
    /**
     * 系统管理员账号
     */
    public static final String USERNAME_SYSTEM_ADMIN = "admin";
    public static final String DEFAUT_PASSWORD = "$2a$10$s4Z4MPxxdSbnUHTpK6Ev/.ENwfAvPw7M0utNIB5mgKxRdQpTRS.vu";
    public static final String REDIS_WECHAT_LOGIN = "wechat:login:code:";

    /**
     * 超级管理员角色编码
     */
    public static final String ROOT_ROLE_CODE = "ROOT";
    public static final String WECHAT_ROLE_CODE = "wechat";
    /**
     * 公共配置
     * 1、用户默认密码为123456
     * 2、系统用户、中文名
     */
    public static final String DEFAULT_WEBUSER_PASSWORD = "123456";
    public static final String SYSTEM_USER_CODE = "system";
    public static final String SYSTEM_USER_CN = "system";
    /**
     * config-key有效时长
     */
    public static final Long ACCTOKEN_VALID_TIME = 3600L;
    public static final Long QINIUTOKEN_VALID_TIME = 7200L;
    public static final Long CODE_VALID_TIME = 5 * 60L;
    /**
     * 七牛云相关常量
     */
    public static final String KEY_QINIU_TOKEN = "qiniutoken";
    /**
     * 微信相关
     */
    public static final String KEY_ACCESS_TOKEN = "weckat:accesstoken:";
    public static final String REDIS_WEXIN_LOGINCODE = "weixin:login:code:";

    /**
     * redis缓存---角色-菜单关联关系key
     */
    public static final String REDIS_HASH_ROLE_MENU_KEY = "quju:menu_roles:menu";

    /**
     * redis缓存---热点模板数据key
     */
    public static final String REDIS_HASH_DYNAMIC_TEMPLATE = "quju:dynamic:template";


    /**
     * 授权token-请求头-ken
     */
    public static final String AUTHORIZATION_KEY = "Authorization";
    /**
     * 请求头-JWT令牌前缀
     */
    public static final String JWT_PREFIX = "Bearer ";


    /**
     * JWT用户权限(角色)
     */
    public static final String JWT_AUTHORITIES_KEY = "authorities";
    /**
     * JWT存储权限前缀
     */
    public static final String AUTHORITY_PREFIX = "ROLE_";
    /**
     * JWT-载体key
     */
    public static final String JWT_PAYLOAD_KEY = "payload";
    /**
     * JWT-ID-唯一标识
     */
    public static final String JWT_JTI = "jti";


    public static final String USER_LATITUDE = "latitude";
    public static final String USER_LONGITUDE = "longitude";
    public static final String USER_CURRENT_PLATFORM = "platform";
    public static final String USER_IS_TOURIST = "isTourist";
    public static final String WEB_USER_ID_KEY = "userId";
    public static final String WEB_USER_NAME_KEY = "username";
    public static final String WEB_USER_DEPT_KEY = "deptId";
    public static final String WECHAT_USER_ID_KEY = "wechatuserid";
    public static final String WECHAT_USER_NAME_KEY = "wechatusername";


    /**
     * 根部门ID
     */
    public static final Long ROOT_DEPT_ID = 0l;
    /**
     * 根菜单ID
     */
    public static final Long ROOT_MENU_ID = 0l;

    /**
     * 是否是默认值 0、否 1、是
     */
    public static final Integer IS_DEFAULT = 1;
    public static final Integer IS_NO_DEFAULT = 0;


    public static final Integer IS_YES = 1;
    public static final Integer IS_NO = 1;
    public static final String GRANT_TYPE_KEY = "grant_type";
    public static final String CLIENT_ID_KEY = "client_id";

    /**
     * 状态：0、正常(true) 1、禁用(否)
     */
    public static final Integer STATUS_ENABLE = 0;
    public static final Integer STATUS_NOT_ENABLE = 1;


    /**
     * Basic认证前缀
     */
    public static final String BASIC_PREFIX = "Basic ";
    public static final String REFRESH_TOKEN_KEY = "refresh_token";
    /**
     * 认证身份标识
     */
    public static final String AUTHENTICATION_IDENTITY_KEY = "authenticationIdentity";


    /**
     * 黑名单token前缀
     */
    public static final String TOKEN_BLACKLIST_PREFIX = "auth:token:blacklist:";


    public static final String INITIALIZE_PROMPT = "初始化%s配置成功";
    public static final String DINGDING_MSG_CIR_DYC_CREATE = "%s-创建了一个圈子-%s";
    public static final String DINGDING_MSG_CIR_ACDY_CREATE = "%s-发布了一个活动-%s";
    //周周发起了一个读书活动2888的活动，活动付费类型为在线支付,费用金额为10元,请及时关注
    public static final String DINGDING_MSG_CIR_ACDY_CREATE_TO_PLATFORM = "%s发起了一个%s的活动，活动付费类型为%s,费用金额为%s元,请及时关注";
    public static final String DINGDING_MSG_JOIN_ACTIVITY = "%s参加了%s,且已支付活动费用%s元,请及时关注";


    public static final String REBOTE_KEY001 = "ding_notice_001";


}
