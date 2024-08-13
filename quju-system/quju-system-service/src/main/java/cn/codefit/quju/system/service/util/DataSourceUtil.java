package cn.codefit.quju.system.service.util;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.util.Map;

public class DataSourceUtil {

    /**
     * 手动配置数据源
     *
     * @param dataSourceMap
     * @return
     */
    public static Map<String, DataSource> createDataSourcemanualControl(Map<String, DataSource> dataSourceMap) {
        // 配置主库
        HikariDataSource masterDs = new HikariDataSource();
        masterDs.setDriverClassName("com.mysql.cj.jdbc.Driver");
        masterDs.setJdbcUrl(
                "jdbc:mysql://119.91.60.138:3306/db_quju_01?characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&rewriteBatchedStatements=true");
        masterDs.setMinimumIdle(4);
        masterDs.setMaximumPoolSize(8);
        masterDs.setConnectionInitSql("SET NAMES utf8mb4");
        masterDs.setUsername("codefit");
        masterDs.setPassword("codefitTest123!");
        dataSourceMap.put("ds0", masterDs);
        // 配置读库1
        HikariDataSource replicaDs1 = new HikariDataSource();
        replicaDs1.setDriverClassName("com.mysql.cj.jdbc.Driver");
        replicaDs1.setJdbcUrl(
                "jdbc:mysql://119.91.60.138:3306/db_quju_01?characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&rewriteBatchedStatements=true");
        replicaDs1.setMinimumIdle(4);
        replicaDs1.setMaximumPoolSize(8);
        replicaDs1.setConnectionInitSql("SET NAMES utf8mb4");
        replicaDs1.setUsername("codefit");
        replicaDs1.setPassword("codefitTest123!");
        dataSourceMap.put("ds1", replicaDs1);
        return dataSourceMap;
    }

}
