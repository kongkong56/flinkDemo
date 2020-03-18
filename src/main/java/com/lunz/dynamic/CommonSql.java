package com.lunz.dynamic;

/**
 * @author Liuruixia
 * @Description:
 * @date 2020/03/17
 */

public    class CommonSql {
    public static final String SOURCE_SQL="CREATE TABLE input_table_kafka( \n" +
            "        vendorid VARCHAR,\n" +
            "        tpep_pickup_datetime VARCHAR,\n" +
            "        tpep_dropoff_datetime VARCHAR,\n" +
            "        passenger_count SMALLINT,\n" +
            "        trip_distance FLOAT,\n" +
            "        PULocationID VARCHAR,\n" +
            "        DOLocationID VARCHAR,\n" +
            "        fare_amount FLOAT,\n" +
            "        extra FLOAT,\n" +
            "        mta_tax FLOAT,\n" +
            "        tip_amount FLOAT,\n" +
            "        tolls_amount FLOAT,\n" +
            "        improvement_surcharge FLOAT,\n" +
            "        ratecodeid VARCHAR,\n" +
            "        payment_type VARCHAR,\n" +
            "        total_amount VARCHAR\n" +
            "        ) \n" +
            "        WITH ( \n" +
            "            'connector.type' = 'kafka',  -- 使用 kafka connector\n" +
            "            'connector.version' = 'universal',  -- kafka 版本，universal 支持 0.11 以上的版本\n" +
            "            'connector.topic' = 'taxi_behavior',  -- kafka topic\n" +
            "            'connector.startup-mode' = 'earliest-offset',  -- 从起始 offset 开始读取\n" +
            "            'connector.properties.zookeeper.connect' = '114.215.130.62:2181',  -- zookeeper 地址\n" +
            "            'connector.properties.bootstrap.servers' = '114.215.130.62:9092',  -- kafka broker 地址\n" +
            "            'format.type' = 'json',  -- 数据源格式为 json\n" +
            "             'connector.properties.group.id' = 'test2'\n" +
            "        )";
    public static  final String SINK_SQL="\n" +
            "CREATE TABLE out_test(\n" +
            "                vendorid VARCHAR,\n" +
            "                tpep_pickup_datetime VARCHAR,\n" +
            "                tpep_dropoff_datetime VARCHAR,\n" +
            "                passenger_count SMALLINT,\n" +
            "                trip_distance FLOAT,\n" +
            "                PULocationID VARCHAR,\n" +
            "                DOLocationID VARCHAR,\n" +
            "                fare_amount FLOAT,\n" +
            "                extra FLOAT,\n" +
            "                mta_tax FLOAT,\n" +
            "                tip_amount FLOAT,\n" +
            "                tolls_amount FLOAT,\n" +
            "                improvement_surcharge FLOAT,\n" +
            "                ratecodeid VARCHAR,\n" +
            "                payment_type VARCHAR,\n" +
            "                total_amount VARCHAR \n" +
            "                ) \n" +
            "                WITH ( \n" +
            "                'connector.type' = 'jdbc', \n" +
            "                'connector.url' = 'jdbc:mysql://114.215.130.62:3306/flink_dev', \n" +
            "                'connector.table' = 'fact_taxi_trip_data', \n" +
            "                'connector.driver' = 'com.mysql.jdbc.Driver', \n" +
            "                'connector.username' = 'root', \n" +
            "                'connector.password' = '123456', \n" +
            "                'connector.lookup.cache.max-rows' = '5000', \n" +
            "                'connector.lookup.cache.ttl' = '10min', \n" +
            "                'connector.write.flush.max-rows' = '5', \n" +
            "                'connector.write.flush.interval' = '2s', \n" +
            "                'connector.write.max-retries' = '3' \n" +
            "                )";
    public static final String TRANSFORMATION="insert into out_test select *  from input_table_kafka";
}
