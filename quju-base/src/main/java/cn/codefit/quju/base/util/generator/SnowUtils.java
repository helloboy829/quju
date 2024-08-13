package cn.codefit.quju.base.util.generator;

public class SnowUtils {

    public static Long getId() {
        SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(0L);
        return snowflakeIdWorker.nextId();
    }

    public static void main(String[] args) {
        SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(0L);
        for (int i = 0; i < 200; i++) {
            Long id = snowflakeIdWorker.nextId();
            System.out.println("id = " + id);
        }
    }
}
