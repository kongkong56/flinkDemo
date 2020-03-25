package com.lunz.dynamic;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.java.StreamTableEnvironment;
/**
 * @author Liuruixia
 * @Description: 加载动态sql执行添加任务
 * @date 2020/03/17
 */
public class DynamicKafkaFormatSqlJob {
    public static void main(String[] args) throws Exception {
        System.out.println("------kafka同步到mysql---");

        final String SOURCE_SQL = "CREATE TABLE input_table_kafka( \n" +
                "        `payload` " +
                "          Row<`after` ROW<id VARCHAR >,op VARCHAR>\n" +
                "        ) \n" +
                "        WITH ( \n" +
                "            'connector.type' = 'kafka',  -- 使用 kafka connector\n" +
                "            'connector.version' = 'universal',  -- kafka 版本，universal 支持 0.11 以上的版本\n" +
                "            'connector.topic' = 'devflinktopic.flink_dev.dimension',  -- kafka topic\n" +
                "            'connector.topic' = 'devflinktopic.flink_dev.dimension',  -- kafka topic\n" +
                "            'connector.startup-mode' = 'earliest-offset',  -- 从起始 offset 开始读取\n" +
                "            'connector.properties.zookeeper.connect' = '114.215.130.62:2181',  -- zookeeper 地址\n" +
                "            'connector.properties.bootstrap.servers' = '114.215.130.62:9092',  -- kafka broker 地址\n" +
                "            'format.type' = 'json',  -- 数据源格式为 json\n" +
                "             'connector.properties.group.id' = 'test'\n" +
                "        )";
        //final String SOURCE_DELETE_SQL = "CREATE TABLE input_table_delete_kafka( \n" +
        //        "        `payload` Row<`before`  ROW<`id` VARCHAR,`code` VARCHAR,`level_id` VARCHAR,`name` VARCHAR>,op VARCHAR>\n" +
        //        "        ) \n" +
        //        "        WITH ( \n" +
        //        "            'connector.type' = 'kafka',  -- 使用 kafka connector\n" +
        //        "            'connector.version' = 'universal',  -- kafka 版本，universal 支持 0.11 以上的版本\n" +
        //        "            'connector.topic' = 'devflinktopic.flink_dev.dimension',  -- kafka topic\n" +
        //        "            'connector.topic' = 'test4',  -- kafka topic\n" +
        //        "            'connector.startup-mode' = 'earliest-offset',  -- 从起始 offset 开始读取\n" +
        //        "            'connector.properties.zookeeper.connect' = '114.215.130.62:2181',  -- zookeeper 地址\n" +
        //        "            'connector.properties.bootstrap.servers' = '114.215.130.62:9092',  -- kafka broker 地址\n" +
        //        "            'format.type' = 'json',  -- 数据源格式为 json\n" +
        //        "             'connector.properties.group.id' = 'test2'\n" +
        //        "        )";
        final String SINK_SQL="CREATE TABLE test( \n" +
                "        id VARCHAR\n" +
                //"        code VARCHAR,\n" +
                //"        level_id VARCHAR,\n" +
                //"        name VARCHAR\n" +
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
       final String KAFKA_SELECT_ALL = "insert into test   select id from input_table_kafka where op like 'c'  group by id";
    //   final String KAFKA_SELECT_ALL = "insert into test select * from input_table_kafka";
   //     final String KAFKA_SELECT_NON_DELETED=" insert into non-deleted select id,code,level_id,name;"

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
       // tableEnv.sqlUpdate(SOURCE_DELETE_SQL);
        tableEnv.sqlUpdate(SINK_SQL);
      //  tableEnv.sqlUpdate("");
      //  tableEnv.sqlUpdate("");
        tableEnv.sqlUpdate(KAFKA_SELECT_ALL);
        //获取plan，此处的Plan和官方的visualization展示的不一样。
        // 两个source ,进行join的时候，plan里也会展示两个源头
        //System.out.println(env.getExecutionPlan());
        tableEnv.execute("模拟数据进入到事实表");

    }
}
