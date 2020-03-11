package com.lunz.tablesql.blink;

import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.TableEnvironment;
/**
 * @author Liuruixia
 * @Description:
 * @date 2020/03/06
 */
public class BlinkSQLApp {
    public static void main(String[] args) {
        EnvironmentSettings bbSettings = EnvironmentSettings.newInstance().useBlinkPlanner().inBatchMode().build();
        TableEnvironment bbTableEnv = TableEnvironment.create(bbSettings);
    }
}
