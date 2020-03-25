package com.lunz.dynamic;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.java.StreamTableEnvironment;

import java.util.Map;

/**
 * @author Liuruixia
 * @Description: 加载动态sql执行添加任务
 * @date 2020/03/17
 */
public class DynamicSqlJob {
    public static void main(String[] args) throws Exception {
        System.out.println("------kafka同步到mysql---");

        // 1. 从mysql数据库读取脚本配置，转换成对应的Option类型
        // 2.
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        EnvironmentSettings settings = EnvironmentSettings.newInstance()
                .useOldPlanner()
                .inStreamingMode()
                .build();

        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env, settings);
        env.enableCheckpointing(5000);
        env.setParallelism(1);

        tableEnv.sqlUpdate(CommonSql.SOURCE_SQL);
        tableEnv.sqlUpdate(CommonSql.SOURCE_SQL_2);
        tableEnv.sqlUpdate(CommonSql.SINK_SQL);

        tableEnv.sqlUpdate(
                CommonSql.TRANSFORMATION);
        //tableEnv.listCatalogs();
        //tableEnv.listDatabases();
        //tableEnv.listTables();
        //获取plan，此处的Plan和官方的visualization展示的不一样。
        // 两个source ,进行join的时候，plan里也会展示两个源头
        System.out.println(env.getExecutionPlan());
        tableEnv.execute("模拟数据进入到事实表");

    }
}
