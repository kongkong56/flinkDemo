package com.lunz.connector.hbase;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.java.StreamTableEnvironment;
/**
 * @author Liuruixia
 */
public class BlinkStreamSQLJobExample5 {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment blinkStreamEnv = StreamExecutionEnvironment.getExecutionEnvironment();
        blinkStreamEnv.setParallelism(1);
        EnvironmentSettings blinkStreamSettings = EnvironmentSettings.newInstance()
                .useOldPlanner()
                .inStreamingMode()
                .build();
        StreamTableEnvironment blinkStreamTableEnv = StreamTableEnvironment.create(blinkStreamEnv, blinkStreamSettings);
        String ddlSource = "CREATE TABLE user_behavior112 ( \n" +
                "user_id BIGINT, \n" +
                "item_id BIGINT, \n" +
                "category_id BIGINT, \n" +
                "behavior STRING, \n" +
                "ts TIMESTAMP(3) \n" +
                ") WITH ( \n" +
                "'connector.type' = 'kafka',  -- 使用 kafka connector\n" +
                "'connector.version' = 'universal',  -- kafka 版本，universal 支持 0.11 以上的版本\n" +
                "'connector.topic' = 'user_behavior',  -- kafka topic\n" +
                "'connector.startup-mode' = 'earliest-offset',  -- 从起始 offset 开始读取\n" +
                "'connector.properties.zookeeper.connect' = '114.215.130.62:2181',  -- zookeeper 地址\n" +
                "'connector.properties.bootstrap.servers' = '114.215.130.62:9092',  -- kafka broker 地址\n" +
                "'format.type' = 'json',  -- 数据源格式为 json\n" +
                "'connector.properties.group.id' = 'test2'\n" +
                ")";

        String ddlSink = "CREATE TABLE user_behavior_hbase112 ( \n" +
                "rowkey BIGINT, \n" +
                "cf ROW<item_id BIGINT, category_id BIGINT> \n" +
                ") WITH ( \n" +
                "'connector.type' = 'hbase', \n" +
                "'connector.version' = '1.4.3', \n" +
                "'connector.table-name' = 'zhisheng02', \n" +
                "'connector.zookeeper.quorum' = '114.215.130.62:2181', \n" +
                "'connector.zookeeper.znode.parent' = '/hbase', \n" +
                "'connector.write.buffer-flush.max-size' = '2mb', \n" +
                "'connector.write.buffer-flush.max-rows' = '1000', \n" +
                "'connector.write.buffer-flush.interval' = '2s' \n" +
                //"'update-mode'='append'\n"+
                ")";


        String sql = "insert into user_behavior_hbase112 select user_id, ROW(item_id, category_id) from user_behavior112";

        System.out.println(ddlSource);
        System.out.println(ddlSink);
        blinkStreamTableEnv.sqlUpdate(ddlSource);
        blinkStreamTableEnv.sqlUpdate(ddlSink);
        blinkStreamTableEnv.sqlUpdate(sql);

        blinkStreamTableEnv.execute("Blink Stream SQL Job5 —— read data from kafka，sink to HBase");
    }
}
