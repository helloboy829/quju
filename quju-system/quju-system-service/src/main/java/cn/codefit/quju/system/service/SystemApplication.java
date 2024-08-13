package cn.codefit.quju.system.service;

import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Some description here.
 *
 * @author CodeFit.cn
 * @version 1.0, 2024/07/11
 */
@DubboComponentScan(value = "cn.codefit.quju.system.service.service.impl")
@EnableDiscoveryClient
@EnableScheduling //允许使用定时任务
@MapperScan({"cn.codefit.quju.system.model.dao", "cn.codefit.quju.system.service.biz.dao"})
@SpringBootApplication
public class SystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }
}
