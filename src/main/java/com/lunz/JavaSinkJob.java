package com.lunz;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author Liuruixia
 * @Description: 自定义sink, 通过socket发送的数据，将string成对象，然后保存到mysql
 * @date 2020/03/12
 */
public class JavaSinkJob {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        sinkFunction(env);
        env.execute("自定义sink 方法写入到mysql数据");
    }

    public static void sinkFunction(StreamExecutionEnvironment env) {
        //DataStreamSource<String> source = env.socketTextStream("localhost", 9999);
         //接收字符串
        DataStreamSource<String> source = env.fromElements("15,testtytryrt,3");
        SingleOutputStreamOperator<Test> test = source.map(new MapFunction<String, Test>() {
            @Override
            public Test map(String value) throws Exception {
                String[] splits = value.split(",");
                Test test = Test.builder().id(Integer.parseInt(splits[0])).name(splits[1]).age(Integer.parseInt(splits[2])).build();
                return test;
            }
        });
        test.addSink(new MysqlSink());

    }


}
