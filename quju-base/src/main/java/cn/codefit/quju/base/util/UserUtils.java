package cn.codefit.quju.base.util;

import cn.codefit.quju.base.constant.Constant;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collections;
import java.util.List;

/**
 * JWT工具类
 */
@Slf4j
public class UserUtils {

    /**
     * 解析JWT获取获取用户-公共参数-平台
     *
     * @return
     */
    public static Integer getPlatform() {
//        return 0;
        String payload = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader(Constant.USER_CURRENT_PLATFORM);
        return Integer.valueOf(payload);
    }

    /**
     * 解析JWT获取获取-是否是游客模式: 0、非游客模式 1、男游客 2、女游客-公共参数-平台
     *
     * @return
     */
    public static Integer getIsTourist() {
//        return 0;
        String payload = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader(Constant.USER_IS_TOURIST);
        return Integer.valueOf(payload);
    }


    /**
     * 解析JWT获取获取用户-公共参数-经度
     *
     * @return
     */
    public static String getLongitude() {
        String longitude = null;
        JSONObject jwtPayload = JwtUtils.getJwtPayload();
        if (jwtPayload != null) {
            longitude = jwtPayload.getStr(Constant.USER_LONGITUDE);
        }
        return longitude;
    }

    /**
     * 解析JWT获取获取用户-公共参数-纬度
     *
     * @return
     */
    public static String getLatitude() {
        String latitude = null;
        JSONObject jwtPayload = JwtUtils.getJwtPayload();
        if (jwtPayload != null) {
            latitude = jwtPayload.getStr(Constant.USER_LATITUDE);
        }
        return latitude;
    }

    /**
     * 解析JWT获取获取web用户-用户ID
     *
     * @return
     */
    public static Long getUserId() {
//        return 11135437L;
        Long userId = null;
        JSONObject jwtPayload = JwtUtils.getJwtPayload();
        if (jwtPayload != null) {
            userId = jwtPayload.getLong(Constant.WEB_USER_ID_KEY);
        }
        return userId;
    }

    /**
     * 解析JWT获取获取web用户-用户名
     *
     * @return
     */
    public static String getUsername() {
        String username = JwtUtils.getJwtPayload().getStr(Constant.WEB_USER_NAME_KEY);
        return username;
    }

    /**
     * 解析JWT获取获取web用户-部门ID
     *
     * @return
     */
    public static Long getDeptId() {
        Long id = JwtUtils.getJwtPayload().getLong(Constant.WEB_USER_DEPT_KEY);
        return id;
    }

    /**
     * 解析JWT获取获取web用户-用户角色列表
     *
     * @return 角色列表
     */
    public static List<String> getRoleCodes() {
        List<String> roles;
        JSONObject payload = JwtUtils.getJwtPayload();
        if (payload.containsKey(Constant.JWT_AUTHORITIES_KEY)) {
            roles = payload.getJSONArray(Constant.JWT_AUTHORITIES_KEY).toList(String.class);
        } else {
            roles = Collections.emptyList();
        }
        return roles;
    }

    /**
     * 解析JWT获取获取web用户-用户角色(目前仅支持单角色)
     *
     * @return 角色
     */
    public static String getRoleCode() {
        String role = null;
        JSONObject payload = JwtUtils.getJwtPayload();
        if (payload.containsKey(Constant.JWT_AUTHORITIES_KEY)) {
            List<String> roles = payload.getJSONArray(Constant.JWT_AUTHORITIES_KEY).toList(String.class);
            role = roles.get(0);
        }
        return role;
    }


    /**
     * 解析JWT获取获取小程序用户-获取小程序用户id
     *
     * @return
     */
    public static Long getWechatUserId() {
        Long memberId = null;
        JSONObject jwtPayload = JwtUtils.getJwtPayload();
        if (jwtPayload != null) {
            memberId = jwtPayload.getLong(Constant.WECHAT_USER_ID_KEY);
        }
        return memberId;
    }

    /**
     * 解析JWT获取获取小程序用户-用户名
     *
     * @return
     */
    public static String getWechatUserName() {
        String username = JwtUtils.getJwtPayload().getStr(Constant.WECHAT_USER_NAME_KEY);
        return username;
    }


    /**
     * 是否「超级管理员」
     *
     * @return
     */
    public static boolean isRoot() {
        List<String> roles = getRoleCodes();
        return CollectionUtil.isNotEmpty(roles) && roles.contains(Constant.ROOT_ROLE_CODE);
    }


}
