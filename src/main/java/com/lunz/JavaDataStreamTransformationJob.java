package com.lunz;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.collector.selector.OutputSelector;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Liuruixia
 * @Description: map, filter的处理  流数据的处理
 * @date 2020/03/12
 */
public class JavaDataStreamTransformationJob {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
      //  filterFunction(env);
      //  unionFunction(env);
        spiltSelectFunction(env);
        env.execute("transformation 方法读取数据");
    }

    public static void filterFunction(StreamExecutionEnvironment env) {
        DataStreamSource<Long> data = env.addSource(new ParallelSourceFunction());
        data.map((MapFunction<Long, Object>) value -> {
            System.out.println("value:" + value);
            return value;
        }).filter((FilterFunction<Object>) value -> (Long) value % 2 == 0)
                .print().setParallelism(1);

    }

    public static void unionFunction(StreamExecutionEnvironment env) {
        DataStreamSource<Long> data = env.addSource(new ParallelSourceFunction());
        DataStreamSource<Long> data2 = env.addSource(new ParallelSourceFunction());
        data.union(data2).print().setParallelism(1);
    }

    public static void spiltSelectFunction(StreamExecutionEnvironment env) {
        DataStreamSource<Long> data = env.addSource(new noParallelSourceFunction());
        DataStreamSource<Long> data2 = env.addSource(new noParallelSourceFunction());
        data.split(new OutputSelector<Long>() {
            @Override
            public Iterable<String> select(Long value) {
                List<String> list=new ArrayList<String>();
                if(value%2==0){
                    list.add("even");
                }else{
                    list.add("odd");
                }
                return list;
            }
        }).select("odd").print().setParallelism(1);

    }
}
