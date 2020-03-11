
package com.lunz;

import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.io.jdbc.JDBCAppendTableSink;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.table.descriptors.Json;
import org.apache.flink.table.descriptors.Kafka;
import org.apache.flink.table.descriptors.Schema;

public class kafkaStreamSqlJob {

    public static void main(String[] args) throws Exception {
        System.out.println("dddd");
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);
        //EnvironmentSettings settings = EnvironmentSettings.newInstance()...
       // TableEnvironment tableEnv = TableEnvironment.create(settings);

// SQL query with a registered table
// register a table named "Orders"
        tableEnv.sqlUpdate("CREATE SOURCE STREAM TRIP_INPUT (\n" +
                "VendorID STRING,\n" +
                "tpep_pickup_datetime STRING,\n" +
                "tpep_dropoff_datetime STRING,\n" +
                "passenger_count STRING,\n" +
                "trip_distance STRING,\n" +
                "store_and_fwd_flag STRING,\n" +
                "PULocationID STRING,\n" +
                "DOLocationID STRING,\n" +
                "payment_type STRING,\n" +
                "ratecodeid STRING,\n" +
                "fare_amount STRING,\n" +
                "extra STRING,\n" +
                "mta_tax STRING,\n" +
                "tip_amount STRING,\n" +
                "tolls_amount STRING,\n" +
                "improvement_surcharge STRING,\n" +
                "total_amount STRING\n" +
                ")\n" +
                "WITH (\n" +
                "    'connector.type' = 'kafka',\n" +
                "    'connector.version' = 'universal',\n" +
                "    'connector.topic' = 'user_behavior',\n" +
                "    'connector.startup-mode' = 'earliest-offset',\n" +
                "    'connector.properties.0.key' = 'zookeeper.connect',\n" +
                "    'connector.properties.0.value' = 'zookeeper:2181',\n" +
                "    'connector.properties.1.key' = 'bootstrap.servers',\n" +
                "    'connector.properties.1.value' = '47.104.60.155:9092',\n" +
                "    'update-mode' = 'append',\n" +
                "    'format.type' = 'json',\n" +
                "    'format.derive-schema' = 'true'\n" +
                ") ;");
// run a SQL query on the Table and retrieve the result as a new Table
//        Table result = tableEnv.sqlQuery(
//                "SELECT product, amount FROM Orders WHERE product LIKE '%Rubber%'");

// SQL update with a registered table
// register a TableSink
        tableEnv.sqlUpdate("CREATE SINK STREAM TRIP_OUTPUT(\n" +
                "VendorID STRING,\n" +
                "tpep_pickup_datetime STRING,\n" +
                "tpep_dropoff_datetime STRING,\n" +
                "passenger_count STRING,\n" +
                "trip_distance STRING,\n" +
                "store_and_fwd_flag STRING,\n" +
                "PULocationID STRING,\n" +
                "DOLocationID STRING,\n" +
                "payment_type STRING,\n" +
                "ratecodeid STRING,\n" +
                "fare_amount STRING,\n" +
                "extra STRING,\n" +
                "mta_tax STRING,\n" +
                "tip_amount STRING,\n" +
                "tolls_amount STRING,\n" +
                "improvement_surcharge STRING,\n" +
                "total_amount STRING\n" +
                ")\n" +
                "WITH (\n" +
                "    'connector.type' = 'kafka',\n" +
                "    'connector.version' = 'universal',\n" +
                "    'connector.topic' = 'user_behavior',\n" +
                "    'connector.startup-mode' = 'earliest-offset',\n" +
                "    'connector.properties.0.key' = 'zookeeper.connect',\n" +
                "    'connector.properties.0.value' = 'zookeeper:2181',\n" +
                "    'connector.properties.1.key' = 'bootstrap.servers',\n" +
                "    'connector.properties.1.value' = '47.104.60.155:9092',\n" +
                "    'update-mode' = 'append',\n" +
                "    'format.type' = 'json',\n" +
                "    'format.derive-schema' = 'true'\n" +
                ");");
// run a SQL update query on the Table and emit the result to the TableSink
        tableEnv.sqlUpdate(
                "insert into TRIP_OUTPUT select * from TRIP_INPUT;");
        tableEnv.execute("模拟数据进入到ods");

    }

    //private void  tableSink(StreamExecutionEnvironment streamExecutionEnvironment, String url, String name, String connectString, Map<String,String> fieldmaps){
    //    ArrayList<TypeInformation<String>> dataFieldTypes=new ArrayList<TypeInformation<String>>();
    //
    //    JDBCAppendTableSink sink = JDBCAppendTableSink.builder()
    //            .setDrivername("org.apache.derby.jdbc.EmbeddedDriver")
    //            .setDBUrl("jdbc:derby:memory:ebookshop")
    //            .setQuery("INSERT INTO books (id) VALUES (?)")
    //            .setParameterTypes(INT_TYPE_INFO)
    //            .build();
    //
    //}
    private static void source(StreamTableEnvironment tEnv, String topicName, String bootStrapServers) {
        tEnv.connect(
                new Kafka()
                        .version("universal")
                        .topic("taxi_behavior")
                        .startFromEarliest()
                        .property("bootstrap.servers", "47.104.134.253:9092")
        ).withFormat(
                new Json()
        ).withSchema(
                new Schema()
                        .field("user_id", "Types.LONG")
                        .field("item_id", "Types.LONG")
                        .field("category_id", "Types.INT")
                        .field("behavior", "Types.STRING")
                        .field("ts", "Types.LONG")
        )
                .inAppendMode()
                .registerTableSource("user_log");
    }

    private static void sink1(StreamTableEnvironment tEnv, String sql) {
        JDBCAppendTableSink tableSink = JDBCAppendTableSink.builder()
                .setDrivername("com.mysql.cj.jdbc.Driver")
                .setDBUrl("jdbc:mysql://localhost:3306/flink_dev")
                .setUsername("root").setPassword("123456")
                .setQuery("INSERT INTO pvuv_sink(dt, pv, uv) VALUES (?, ?, ?)")
                .setParameterTypes(Types.STRING, Types.LONG, Types.LONG)
                .build();

        tEnv.registerTableSink("pvuv_sink",
                new String[]{"dt", "pv", "uv"},
                new TypeInformation[]{Types.STRING, Types.LONG, Types.LONG},
                tableSink);

        Table t1 = tEnv.sqlQuery("select " +
                "date_format(ts, 'yyyy-mm-dd hh:00') as dt, count(*) as pv, count(distinct user_id) as uv " +
                "from user_log " +
                "group by date_format(ts, 'yyyy-mm-dd hh:00')");
        t1.insertInto("pvuv_sink");
    }

}
