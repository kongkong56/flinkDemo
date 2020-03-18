package com.lunz.dynamic;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.java.StreamTableEnvironment;
/**
 * @author Liuruixia
 * @Description: 加载动态sql执行添加任务
 * @date 2020/03/17
 */
public class DynamicSqlJob {
    public static void main(String[] args) throws Exception {
        System.out.println("------kafka同步到mysql---");

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        EnvironmentSettings settings = EnvironmentSettings.newInstance()
                .useOldPlanner()
                .inStreamingMode()
                .build();

        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env, settings);
        env.enableCheckpointing(5000);
        tableEnv.sqlUpdate(CommonSql.SOURCE_SQL);

        tableEnv.sqlUpdate(CommonSql.SINK_SQL);

        tableEnv.sqlUpdate(
                CommonSql.TRANSFORMATION);
        tableEnv.execute("模拟数据进入到事实表");
    }
}
