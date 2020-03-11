
package com.lunz;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.io.jdbc.JDBCAppendTableSink;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.table.descriptors.Json;
import org.apache.flink.table.descriptors.Kafka;
import org.apache.flink.table.descriptors.Schema;

import java.util.Properties;

public class kafkaStreamTableJob {

    public static void main(String[] args) throws Exception {
        System.out.println("dddd");
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        EnvironmentSettings settings = EnvironmentSettings.newInstance()
                .useOldPlanner()
                .inStreamingMode()
                .build();
        Properties props = new Properties();
        props.put("bootstrap.servers", "http://47.104.134.253:9092");
        props.put("group.id", "metric-group");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("auto.offset.reset", "earliest");

        StreamTableEnvironment tEnv = StreamTableEnvironment.create(env, settings);
        String sourcetopic = "taxi_behavior";
        DataStreamSource<String> dataStreamSource = env.addSource(new FlinkKafkaConsumer<>(
                sourcetopic,
                new SimpleStringSchema(),
                props));

        Table table = tEnv.fromDataStream(dataStreamSource);
        tEnv.registerTable("test", table);

        tEnv.sqlQuery("select * from test");
        // source(tEnv);
        sink1(tEnv, "");
        env.execute("test init taxi fact table");
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
