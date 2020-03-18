
package com.lunz.connector.hbase;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.java.StreamTableEnvironment;

public class kafkaStream2HBaseJob {

    public static void main(String[] args) throws Exception {
        System.out.println("---kafka同步到hbase---");

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        EnvironmentSettings settings = EnvironmentSettings.newInstance()
                .useOldPlanner()
                .inStreamingMode()
                .build();

        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env, settings);
        env.enableCheckpointing(5000);
String ddlSource="CREATE TABLE tKAFKA_INPUT_TABLE456( \n" +
        "vendorid VARCHAR,\n"+
        "tpep_pickup_datetime VARCHAR,\n"+
        "tpep_dropoff_datetime VARCHAR,\n"+
        "passenger_count SMALLINT,\n"+
        "trip_distance FLOAT,\n"+
        "PULocationID VARCHAR,\n"+
        "DOLocationID VARCHAR,\n"+
        "fare_amount FLOAT,\n"+
        "extra FLOAT,\n"+
        "mta_tax FLOAT,\n"+
        "tip_amount FLOAT,\n"+
        "tolls_amount FLOAT,\n"+
        "improvement_surcharge FLOAT,\n"+
        "ratecodeid VARCHAR,\n"+
        "payment_type VARCHAR,\n"+
        "total_amount VARCHAR\n" +
        ") \n" +
        "WITH ( \n" +
        "    'connector.type' = 'kafka',  -- 使用 kafka connector\n" +
        "    'connector.version' = 'universal',  -- kafka 版本，universal 支持 0.11 以上的版本\n" +
        "    'connector.topic' = 'user_behavior',  -- kafka topic\n" +
        "    'connector.startup-mode' = 'earliest-offset',  -- 从起始 offset 开始读取\n" +
        "    'connector.properties.zookeeper.connect' = 'localhost:2181',  -- zookeeper 地址\n" +
        "    'connector.properties.bootstrap.servers' = 'localhost:9092',  -- kafka broker 地址\n" +
        "    'format.type' = 'json',  -- 数据源格式为 json\n" +
        "     'connector.properties.group.id' = 'test2'\n" +
        ")";
String ddlSink="CREATE TABLE test456( \n" +
        "vendorid BIGINT,\n"+
        "tpep_pickup_datetime ROW<>,\n"+
        "tpep_dropoff_datetime ROW<>,\n"+
        "passenger_count ROW<>,\n"+
        "trip_distance ROW<>,\n"+
        "PULocationID ROW<>,\n"+
        "DOLocationID ROW<>,\n"+
        "fare_amount ROW<>,\n"+
        "extra ROW<>,\n"+
        "mta_tax ROW<>,\n"+
        "tip_amount ROW<>,\n"+
        "tolls_amount ROW<>,\n"+
        "improvement_surcharge ROW<>,\n"+
        "ratecodeid ROW<>,\n"+
        "payment_type ROW<>,\n"+
        "total_amount ROW<>\n" +
        ") \n" +
        "WITH ( \n" +
        "'connector.type' = 'hbase', \n" +
        "'connector.version' = '1.4.3', \n" +
        "'connector.table-name' = 'test', \n" +
        "'connector.zookeeper.quorum' = 'localhost:2181', \n" +
        "'connector.zookeeper.znode.parent' = '/hbase'\n" +
        ")";
        tableEnv.sqlUpdate(ddlSource);

        tableEnv.sqlUpdate(ddlSink);

        System.out.println(ddlSource);
        System.out.println(ddlSink);

        tableEnv.sqlUpdate(
                "insert into test456 select *  from tKAFKA_INPUT_TABLE456");
        tableEnv.execute("模拟将kafka数据进入到hbase");






// SQL query with a registered table
// register a table named "Orders"
//        tableEnv.sqlUpdate("CREATE TABLE tKAFKA_INPUT_TABLE456_4 (\n" +
//                //"VendorID VARCHAR,\n" +
//                //"tpep_pickup_datetime VARCHAR,\n" +
//                //"tpep_dropoff_datetime VARCHAR,\n" +
//                //"passenger_count VARCHAR,\n" +
//                //"trip_distance VARCHAR,\n" +
//                //"store_and_fwd_flag VARCHAR,\n" +
//                //"PULocationID VARCHAR,\n" +
//                //"DOLocationID VARCHAR,\n" +
//                //"payment_type VARCHAR,\n" +
//                //"ratecodeid VARCHAR,\n" +
//                //"fare_amount VARCHAR,\n" +
//                //"extra VARCHAR,\n" +
//                //"mta_tax VARCHAR,\n" +
//                //"tip_amount VARCHAR,\n" +
//                //"tolls_amount VARCHAR,\n" +
//                //"improvement_surcharge VARCHAR,\n" +
//                "total_amount VARCHAR\n" +
//                ")\n" +
//                "WITH (\n" +
//                "    'connector.type' = 'kafka',\n" +
//                "    'connector.version' = 'universal',\n" +
//                "    'connector.topic' = 'user_behavior',\n" +
//                "    'connector.startup-mode' = 'earliest-offset',\n" +
//                "    'connector.properties.0.key' = 'zookeeper.connect',\n" +
//                "    'connector.properties.0.value' = 'zookeeper:2181',\n" +
//                "    'connector.properties.1.key' = 'bootstrap.servers',\n" +
//                "    'connector.properties.1.value' = '47.104.134.2553:9092',\n" +
//                "    'update-mode' = 'append',\n" +
//                "    'format.type' = 'json',\n" +
//                "    'format.derive-schema' = 'true'\n" +
//                ")");
//// run a SQL query on the Table and retrieve the result as a new Table
////        Table result = tableEnv.sqlQuery(
////                "SELECT product, amount FROM Orders WHERE product LIKE '%Rubber%'");
//
//// SQL update with a registered table
//// register a TableSink
//        tableEnv.sqlUpdate("CREATE TABLE test4(\n" +
//              //  "VendorID STRING,\n" +
//              //  "tpep_pickup_datetime TIMESTAMP,\n" +
//              //  "tpep_dropoff_datetime TIMESTAMP,\n" +
//              //  "passenger_count INT,\n" +
//              //  "trip_distance FLOAT,\n" +
//              //  "PULocationID VARCHAR,\n" +
//              //  "DOLocationID VARCHAR,\n" +
//              //  "fare_amount FLOAT,\n" +
//              //  "extra FLOAT,\n" +
//              //  "mta_tax FLOAT,\n" +
//              //  "tip_amount FLOAT,\n" +
//              //  "tolls_amount FLOAT,\n" +
//              //  "improvement_surcharge FLOAT,\n" +
//                "total_amount VARCHAR\n" +
//                ")\n" +
//                "WITH (\n" +
//                "'connector.type' = 'jdbc',\n" +
//                "'connector.url' = 'jdbc:mysql://localhost:3306/flink_dev',\n" +
//                "'connector.table' = 'test1',\n" +
//                "'connector.driver' = 'com.mysql.jdbc.Driver',\n" +
//                "'connector.username' = 'root',\n" +
//                "'connector.password' = '1234556',\n" +
//                "'connector.lookup.cache.max-rows' = '55000',\n" +
//                "'connector.lookup.cache.ttl' = '10min'\n" +
//                ")");
//// run a SQL update query on the Table and emit the result to the TableSink
//        tableEnv.sqlUpdate(
//                "insert into test4 select *  from tKAFKA_INPUT_TABLE456_4");
//        tableEnv.execute("模拟数据进入到事实表");

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
    //private static void source(StreamTableEnvironment tEnv, String topicName, String bootStrapServers) {
    //    tEnv.connect(
    //            new Kafka()
    //                    .version("universal")
    //                    .topic("taxi_behavior")
    //                    .startFromEarliest()
    //                    .property("bootstrap.servers", "47.104.134.2553:9092")
    //    ).withFormat(
    //            new Json()
    //    ).withSchema(
    //            new Schema()
    //                    .field("user_id", "Types.LONG")
    //                    .field("item_id", "Types.LONG")
    //                    .field("category_id", "Types.INT")
    //                    .field("behavior", "Types.STRING")
    //                    .field("ts", "Types.LONG")
    //    )
    //            .inAppendMode()
    //            .registerTableSource("user_log");
    //}
    //
    //private static void sink1(StreamTableEnvironment tEnv, String sql) {
    //    JDBCAppendTableSink tableSink = JDBCAppendTableSink.builder()
    //            .setDrivername("com.mysql.cj.jdbc.Driver")
    //            .setDBUrl("jdbc:mysql://localhost:3306/flink_dev")
    //            .setUsername("root").setPassword("1234556")
    //            .setQuery("INSERT INTO pvuv_sink(dt, pv, uv) VALUES (?, ?, ?)")
    //            .setParameterTypes(Types.STRING, Types.LONG, Types.LONG)
    //            .build();
    //
    //    tEnv.registerTableSink("pvuv_sink",
    //            new String[]{"dt", "pv", "uv"},
    //            new TypeInformation[]{Types.STRING, Types.LONG, Types.LONG},
    //            tableSink);
    //
    //    Table t1 = tEnv.sqlQuery("select " +
    //            "date_format(ts, 'yyyy-mm-dd hh:00') as dt, count(*) as pv, count(distinct user_id) as uv " +
    //            "from user_log " +
    //            "group by date_format(ts, 'yyyy-mm-dd hh:00')");
    //    t1.insertInto("pvuv_sink");
    //}

}
