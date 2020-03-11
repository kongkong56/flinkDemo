package com.lunz.tablesql.flink;

import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.java.BatchTableEnvironment;

/**
 * @author Liuruixia
 * @Description:
 * @date 2020/03/06
 */
public class SQLApp {
    public static void main(String[] args) {
        // 1.获取批处理环境
        ExecutionEnvironment fbEnv = ExecutionEnvironment.getExecutionEnvironment();
        BatchTableEnvironment fbTableEnv = BatchTableEnvironment.create(fbEnv);


        // 2.input data

        //3.dataset --->table

        //4.注册table,table==>table

        //5.resultTable
        Table revenue = fbTableEnv.sqlQuery(
                "SELECT cID, cName, SUM(revenue) AS revSum " +
                        "FROM Orders " +
                        "WHERE cCountry = 'FRANCE' " +
                        "GROUP BY cID, cName"
        );
        fbTableEnv.sqlUpdate(
                "INSERT INTO RevenueFrance " +
                        "SELECT cID, cName, SUM(revenue) AS revSum " +
                        "FROM Orders " +
                        "WHERE cCountry = 'FRANCE' " +
                        "GROUP BY cID, cName"
        );


        //6.输出Table

    }
}
