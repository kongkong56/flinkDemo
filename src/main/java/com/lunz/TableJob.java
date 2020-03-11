/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lunz;

import org.apache.flink.api.java.ExecutionEnvironment;

/**
 * Skeleton for a Flink Streaming Job.
 *
 * <p>For a tutorial how to write a Flink streaming application, check the
 * tutorials and examples on the <a href="https://flink.apache.org/docs/stable/">Flink Website</a>.
 *
 * <p>To package your application into a JAR file for execution, run
 * 'mvn clean package' on the command line.
 *
 * <p>If you change the name of the main class (with the public static void main(String[] args))
 * method, change the respective entry in the POM.xml file (simply search for 'mainClass').
 */
public class TableJob {

    public static void main(String[] args) throws Exception {
//        // create a TableEnvironment for specific planner batch or streaming
//        TableEnvironment tableEnv = ...; // see "Create a TableEnvironment" section
//
//// create a Table
//        tableEnv.connect(...).createTemporaryTable("table1");
//// register an output Table
//        tableEnv.connect(...).createTemporaryTable("outputTable");
//
//// create a Table object from a Table API query
//        Table tapiResult = tableEnv.from("table1").select(...);
//// create a Table object from a SQL query
//        Table sqlResult = tableEnv.sqlQuery("SELECT ... FROM table1 ... ");
//
//// emit a Table API result Table to a TableSink, same for SQL result
//        tapiResult.insertInto("outputTable");
//
//// execute
//        tableEnv.execute("java_job");
//    }
//
//    // ******************
//// FLINK BATCH QUERY
//// ******************
//import org.apache.flink.api.java.ExecutionEnvironment;
//import org.apache.flink.table.api.java.BatchTableEnvironment;
//
//    ExecutionEnvironment fbEnv = ExecutionEnvironment.getExecutionEnvironment();
//    BatchTableEnvironment fbTableEnv = BatchTableEnvironment.create(fbEnv);


        //private static void sink(StreamTableEnvironment tEnv) {
        //    JDBCAppendTableSink tableSink = JDBCAppendTableSink.builder()
        //            .setDrivername("com.mysql.cj.jdbc.Driver")
        //            .setDBUrl("jdbc:mysql://localhost:3306/boot?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai")
        //            .setUsername("root").setPassword("1121")
        //            .setQuery("INSERT INTO user_behavior(user_id, item_id, category_id, behavior, ts) VALUES (?, ?, ?, ?, ?)")
        //            .setParameterTypes(Types.LONG, Types.LONG, Types.INT, Types.STRING, Types.LONG)
        //            .build();
        //    tableSink = (JDBCAppendTableSink) tableSink.configure(
        //            new String[]{"user_id", "item_id", "category_id", "behavior", "ts"},
        //            new TypeInformation[]{Types.LONG, Types.LONG, Types.INT, Types.STRING, Types.LONG}
        //    );
        //
        //    tEnv.registerTableSink("user_behavior", tableSink);
        //
        //    Table t1 = tEnv.sqlQuery("select * from user_log");
        //    /// TODO 为什么insert不进去
        //    // t1.insertInto("user_behavior");
        //    // tEnv.insertInto(t1, "user_behavior");
        //    // tEnv.sqlUpdate("insert into user_behavior select * from user_log");
        //    // tEnv.sqlQuery("select * from user_log").insertInto("user_behavior");
        //
        //    tEnv.toAppendStream(t1, UserBehaviorEvent1.class).print("foo");
        //}


        //String sourcetopic = "taxi_behavior";
        //Properties props = new Properties();
        //props.put("bootstrap.servers", "http://47.104.134.253:9092");
        //props.put("group.id", "metric-group");
        //props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        //props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        //props.put("auto.offset.reset", "earliest");
        //
        //DataStreamSource<String> dataStreamSource = env.addSource(new FlinkKafkaConsumer<>(
        //        sourcetopic,
        //        new SimpleStringSchema(),
        //        props));
        //
        //dataStreamSource.print().setParallelism(1); //把从 kafka 读取到的数据打印在控制台

        //  DataStream<HashMap<String, String>> taxiSourceData=kafkasourcedata.keyBy(0).flatMap();


        //tableEnv.registerTableSink(
        //        "jdbcOutputTable",
        //        // specify table schema
        //        new String[]{"id"},
        //        new TypeInformation[]{Types.INT},
        //        sink);



        // dataStreamSource.addSink(new WCMysqlSink());
        //  DataStream<HashMap<String, String>> taxiSourceData=kafkasourcedata.keyBy(0).flatMap();
}}
