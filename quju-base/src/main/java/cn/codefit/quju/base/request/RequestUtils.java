package cn.codefit.quju.base.request;

import cn.codefit.quju.base.constant.AuthenticationIdentityEnum;
import cn.codefit.quju.base.constant.Constant;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.nimbusds.jose.JWSObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 请求工具类
 */
@Slf4j
public class RequestUtils {
    @SneakyThrows
    public static String getGrantType() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String grantType = request.getParameter(Constant.GRANT_TYPE_KEY);
        return grantType;
    }

    /**
     * 获取登录认证的客户端ID
     * <p>
     * 兼容两种方式获取OAuth2客户端信息（client_id、client_secret）
     * 方式一：client_id、client_secret放在请求路径中
     * 方式二：放在请求头（Request Headers）中的Authorization字段，且经过加密，例如 Basic Y2xpZW50OnNlY3JldA== 明文等于 client:secret
     *
     * @return
     */
    @SneakyThrows
    public static String getOAuth2ClientId() {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        // 从请求路径中获取
        String clientId = request.getParameter(Constant.CLIENT_ID_KEY);
        if (StrUtil.isNotBlank(clientId)) {
            return clientId;
        }

        // 从请求头获取
        String basic = request.getHeader(Constant.AUTHORIZATION_KEY);
        if (StrUtil.isNotBlank(basic) && basic.startsWith(Constant.BASIC_PREFIX)) {
            basic = basic.replace(Constant.BASIC_PREFIX, Strings.EMPTY);
            String basicPlainText = new String(Base64.getDecoder().decode(basic.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            clientId = basicPlainText.split(":")[0]; //client:secret
        }
        return clientId;
    }

    public static void main(String[] args) {
        String basic = "bWFsbC1hZG1pbi13ZWI6MTIzNDU2";
        String basicPlainText = new String(Base64.getDecoder().decode(basic.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        System.out.println("basicPlainText = " + basicPlainText);
        basic = "week-admin:sZUf@EHwm7H8jTwR3z589EDcALzd5txx";
        String basicPlainText1 = new String(Base64.getEncoder().encode(basic.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        System.out.println("basicPlainText = " + basicPlainText1);
    }

    /**
     * 解析JWT获取获取认证身份标识
     *
     * @return
     */
    @SneakyThrows
    public static String getAuthenticationIdentity() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String refreshToken = request.getParameter(Constant.REFRESH_TOKEN_KEY);

        String payload = StrUtil.toString(JWSObject.parse(refreshToken).getPayload());
        JSONObject jsonObject = JSONUtil.parseObj(payload);

        String authenticationIdentity = jsonObject.getStr(Constant.AUTHENTICATION_IDENTITY_KEY);
        if (StrUtil.isBlank(authenticationIdentity)) {
            authenticationIdentity = AuthenticationIdentityEnum.USERNAME.getValue();
        }
        return authenticationIdentity;
    }
}
