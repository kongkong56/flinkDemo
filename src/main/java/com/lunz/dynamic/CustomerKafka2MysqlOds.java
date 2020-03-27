package com.lunz.dynamic;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.java.StreamTableEnvironment;

/**
 * @author Liuruixia
 * @Description: 加载动态sql执行添加任务
 * @date 2020/03/17
 */
public class CustomerKafka2MysqlOds {
    public static void main(String[] args) throws Exception {
        System.out.println("------kafka同步到mysql---");

        final String SOURCE_SQL = "CREATE TABLE input_table_kafka( \n" +
                //"WATERMARK wk1 FOR c as withOffset(c, 1000)  --Watermark计算方法"+
                "        `payload` " +
                "          Row<`after` ROW<`id` VARCHAR,`code` VARCHAR,`level_id` VARCHAR,`name` VARCHAR,`remarks` VARCHAR>,`source` Row< `ts_ms` BIGINT >,`op` VARCHAR>,\n" +
               // "       , insertat AS TO_TIMESTAMP(FROM_UNIXTIME(payload.source.ts_ms,'yyyy-MM-dd HH:mm:ss'))" +
                "  `TS` AS `TO_TIMESTAMP`(FROM_UNIXTIME(payload.source.ts_ms,'yyyy-MM-dd HH:mm:ss'))\n" +
                //"  WATERMARK FOR `TS` AS (`TS` + INTERVAL '1' SECOND)\n" +

               ") \n" +
                "        WITH ( \n" +
                "            'connector.type' = 'kafka',  -- 使用 kafka connector\n" +
                "            'connector.version' = 'universal',  -- kafka 版本，universal 支持 0.11 以上的版本\n" +
                "            'connector.topic' = 'devflink-1.flink_dev.dimension',  -- kafka topic\n" +
                "            'connector.startup-mode' = 'earliest-offset',  -- 从起始 offset 开始读取\n" +
                "            'connector.properties.zookeeper.connect' = '114.215.130.62:2181',  -- zookeeper 地址\n" +
                "            'connector.properties.bootstrap.servers' = '114.215.130.62:9092',  -- kafka broker 地址\n" +
                "            'format.type' = 'json',  -- 数据源格式为 json\n" +
                "             'connector.properties.group.id' = 'test'\n" +
                "        )";


        final String SINK_SQL = "CREATE TABLE test( \n" +
                "        ts_ms BIGINT,"+
                "        insertat timestamp(3),"+
                "        old_id VARCHAR ,\n" +
                "        code VARCHAR,\n" +
                "        level_id VARCHAR,\n" +
                "        name VARCHAR,\n" +
                "        remarks VARCHAR,\n" +
                "        op VARCHAR\n" +
                "        ) \n" +
                "                WITH ( \n" +
                "                'connector.type' = 'jdbc', \n" +
                "                'connector.url' = 'jdbc:mysql://114.215.130.62:3306/flink', \n" +
                "                'connector.table' = 'dimension', \n" +
                "                'connector.driver' = 'com.mysql.jdbc.Driver', \n" +
                "                'connector.username' = 'zoeliu', \n" +
                "                'connector.password' = 'qsczxa123!', \n" +
                "                'connector.lookup.cache.max-rows' = '5000', \n" +
                "                'connector.lookup.cache.ttl' = '10min', \n" +
                "                'connector.write.flush.max-rows' = '5', \n" +
                "                'connector.write.flush.interval' = '2s', \n" +
                "                'connector.write.max-retries' = '3' \n" +
                "                )";
       // final String KAFKA_SELECT_ALL = "insert into test  select id,code,name,level_id,remarks,op from  input_table_kafka group by id,code ,name,level_id,remarks,op";
        final String KAFKA_SELECT_ALL = "insert into test  select  ts_ms, TS as insertat," +
                //"  ts_ms AS TO_TIMESTAMP(FROM_UNIXTIME(ts / 1000, 'yyyy-MM-dd HH:mm:ss')), -- 事件时间" +
                "  id as old_id,code,name,level_id,remarks,op     from  input_table_kafka";
        // 1. 从mysql数据库读取脚本配置，转换成对应的Option类型
        // 2.
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        EnvironmentSettings settings = EnvironmentSettings.newInstance()
                .useBlinkPlanner()
                .inStreamingMode()
                .build();

        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env, settings);
        env.enableCheckpointing(5000);
        env.setParallelism(1);
        tableEnv.sqlUpdate(SOURCE_SQL);
        tableEnv.sqlUpdate(SINK_SQL);
        tableEnv.sqlUpdate(KAFKA_SELECT_ALL);
        //获取plan，此处的Plan和官方的visualization展示的不一样。
        // 两个source ,进行join的时候，plan里也会展示两个源头
        //System.out.println(env.getExecutionPlan());
        //tableEnv.toRetractStream(result, Row.class);
        tableEnv.execute("模拟数据进入到事实表");

    }
}
