package cn.codefit.quju.base.util.generator;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 推荐使用
 * Hutool是一个小而全的Java工具类库，通过静态方法封装，降低相关API的学习成本，提高工作效率
 * https://www.hutool.cn/docs/#/core/%E5%B7%A5%E5%85%B7%E7%B1%BB/%E5%94%AF%E4%B8%80ID%E5%B7%A5%E5%85%B7-IdUtil
 */
@Component
public class SnowFlakeUtil {

    private long workerId = 0L;
    private final long datacenterId = 1L;
    //参数1为终端ID
    //参数2为数据中心ID
    private final Snowflake snowflake = IdUtil.getSnowflake(workerId, datacenterId);

    @PostConstruct
    public void init() {
        try {
            workerId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr());
        } catch (Exception e) {
            workerId = NetUtil.getLocalhostStr().hashCode();
        }

    }

    public synchronized long snowflakeId() {
        return snowflake.nextId();
    }

}
