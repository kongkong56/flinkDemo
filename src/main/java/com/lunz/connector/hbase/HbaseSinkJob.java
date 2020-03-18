package com.lunz.connector.hbase;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.flink.streaming.connectors.kafka.internals.KeyedSerializationSchemaWrapper;

import java.util.Properties;

/**
 * @author Liuruixia
 * @Description: 将kafka作为source, 收到后打印出来
 * @date 2020/03/12
 */
public class HbaseSinkJob {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "114.215.130.62:9092");
        properties.setProperty("group.id", "test");
        String topic = "user_behavior";
        DataStreamSource<String> source = env.fromElements("1,2,3,4,5,65,67");

        FlinkKafkaProducer kafkaProducer = new FlinkKafkaProducer<>(
                topic, new KeyedSerializationSchemaWrapper<>(new SimpleStringSchema()),properties,FlinkKafkaProducer.Semantic.EXACTLY_ONCE);


        source.addSink(kafkaProducer);
        env.execute("将数据写入到kafka指定的topic当中");
    }

}
