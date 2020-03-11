package com.lunz.tablesql.flink;

import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.java.BatchTableEnvironment;
import org.apache.flink.table.functions.FunctionContext;
import org.apache.flink.table.functions.ScalarFunction;

/**
 * @author Liuruixia
 * @Description:
 * @date 2020/03/06
 */
public class udfApp {
    public static void main(String[] args) {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        BatchTableEnvironment tableEnv = BatchTableEnvironment.create(env);

        // set job parameter
        Configuration conf = new Configuration();
        conf.setString("hashcode_factor", "31");
        env.getConfig().setGlobalJobParameters(conf);

// register the function
        tableEnv.registerFunction("hashCode", new HashCode());

// use the function in Java Table API
      Table myTable = null;
      myTable.select("string, string.hashCode(), hashCode(string)");

// use the function in SQL
        tableEnv.sqlQuery("SELECT string, HASHCODE(string) FROM MyTable");
    }

    public static  class HashCode extends ScalarFunction {

        private int factor = 0;

        @Override
        public void open(FunctionContext context) throws Exception {
            // access "hashcode_factor" parameter
            // "12" would be the default value if parameter does not exist
            factor = Integer.valueOf(context.getJobParameter("hashcode_factor", "12"));
        }

        public int eval(String s) {
            return s.hashCode() * factor;
        }
    }

    public static class TimestampModifier extends ScalarFunction {
        public long eval(long t) {
            return t % 1000;
        }

        @Override
        public TypeInformation<?> getResultType(Class<?>[] signature) {
            return Types.SQL_TIMESTAMP;
        }
    }


}
