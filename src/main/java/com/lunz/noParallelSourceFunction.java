package com.lunz;

import org.apache.flink.streaming.api.functions.source.SourceFunction;

/**
 * @author Liuruixia
 * @Description:
 * @date 2020/03/12
 */
public class noParallelSourceFunction implements SourceFunction<Long> {

    boolean isRunning = true;
    long count = 1;

    @Override
    public void run(SourceContext<Long> ctx) throws Exception {
        while (true) {
            ctx.collect(count);
            count += 1;
            Thread.sleep(1000);
        }
    }

    @Override
    public void cancel() {
        isRunning = false;
    }
}
