package cn.codefit.base.redis.config;

import cn.codefit.base.redis.constant.Constant;
import io.micrometer.core.instrument.util.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class RedissonConfig {
    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private String port;

//    @Value("${spring.redis.password}")
//    private String password;

    @Value("${spring.redis.database}")
    private String database;
    /**
     * 集群
     */
//    @Value("${spring.config.cluster}")
    private String cluster;
    private static final Logger logger = LoggerFactory.getLogger(RedissonConfig.class);

    /**
     * 配置redisson --单节点
     *
     * @return
     */
    @Bean(destroyMethod = "shutdown")
    @ConditionalOnMissingBean
    public RedissonClient getRedisson() {
        Config config = new Config();
        String address = "redis://" + host + ":" + port;
        config
                .useSingleServer()
                .setAddress(address)
//                .setPassword(password)
                .setDatabase(Integer.valueOf(database))
                .setPingConnectionInterval(10000)
                .setConnectionMinimumIdleSize(10);
        RedissonClient client = Redisson.create(config);
        logger.info(String.format(Constant.INITIALIZE_PROMPT, "redisson单节点模式"));
        return client;
    }

    /**
     * 配置redisson --集群方式
     * Redisson是RedissonClient的实现类
     *
     * @return
     */
//    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson() {
        //集群模式配置
        List<String> clusters = Arrays.asList(cluster.split(","));
        List<String> clusterNodes = new ArrayList<>();
        for (int i = 0; i < clusters.size(); i++) {
            clusterNodes.add("redis://" + clusters.get(i));
        }
        Config config = new Config();
        ClusterServersConfig clusterServersConfig = config.useClusterServers()
                .addNodeAddress(clusterNodes.toArray(new String[clusterNodes.size()])).setScanInterval(2000).setPingConnectionInterval(1000);

//        if (StringUtils.isNotBlank(password)) {
//            clusterServersConfig.setPassword(password);
//        }
        RedissonClient client = Redisson.create(config);
        logger.info(String.format(Constant.INITIALIZE_PROMPT, "redisson集群模式"));
        return client;
    }


}
