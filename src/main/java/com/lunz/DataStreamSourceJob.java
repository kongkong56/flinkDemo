package com.lunz;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author Liuruixia
 * @Description: 从socket读取数据并打印出来
 * @date 2020/03/12
 */
public class DataStreamSourceJob {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //socketFunction(env);
        ParallelSourceFunction(env);
        env.execute("dataStream 方法读取数据");
    }

    public static void socketFunction(StreamExecutionEnvironment env) {
        DataStreamSource<String> data = env.socketTextStream("localhost", 9999);
        data.print().setParallelism(1);

    }

    public static void noParallelSourceFunction(StreamExecutionEnvironment env) {
        DataStreamSource<Long> data = env.addSource(new noParallelSourceFunction());
        data.print().setParallelism(1);
    }
    public static void ParallelSourceFunction(StreamExecutionEnvironment env) {
        DataStreamSource<Long> data = env.addSource(new ParallelSourceFunction());
        data.print().setParallelism(2);
    }
    public static void RichParallelSourceFunction(StreamExecutionEnvironment env) {
        DataStreamSource<Long> data = env.addSource(new RichParallelSourceFunction());
        data.print().setParallelism(2);
    }
}
