package com.lunz.connector;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

import java.util.Properties;

/**
 * @author Liuruixia
 * @Description: 将kafka作为source,收到后打印出来
 * @date 2020/03/12
 */
public class kafkaSourceJob {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers","114.215.130.62:9092");
        properties.setProperty("group.id", "test");
        String topic = "user_behavior";
        DataStreamSource<String> source = env.addSource(new FlinkKafkaConsumer<>(topic, new SimpleStringSchema(), properties));
        source.print();
        env.execute("dataStream 方法读取数据");
    }

}
