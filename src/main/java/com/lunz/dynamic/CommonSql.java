package com.lunz.dynamic;

/**
 * @author Liuruixia
 * @Description:
 * @date 2020/03/17
 */

public class CommonSql {
    public static final String SOURCE_SQL = "CREATE TABLE input_table_kafka( \n" +
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
    public static final String SOURCE_SQL_2 = "CREATE TABLE input_table_kafka_2( \n" +
            "        vendorid VARCHAR,\n" +
            "        tpep_pickup_datetime VARCHAR,\n" +
            "        tpep_dropoff_datetime VARCHAR,\n" +
            "        passenger_count SMALLINT,\n" +
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
    public static final String SINK_SQL = "\n" +
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
    public static final String TRANSFORMATION = "insert into out_test select t.*  from input_table_kafka t join input_table_kafka_2 t2 on t.passenger_count=t2.passenger_count";
    public static final String SOURCE_SQL_KAFKA = "CREATE TABLE KAFKA_AVRO1( \n" +
            "        id int,\n" +
            "        value_text STRING\n" +
            "        ) \n" +
            "        WITH ( \n" +
            "            'connector.type' = 'kafka',  -- 使用 kafka connector\n" +
            "            'connector.version' = 'universal',  -- kafka 版本，universal 支持 0.11 以上的版本\n" +
            "            'connector.topic' = 'flinktest',  -- kafka topic\n" +
            "            'connector.startup-mode' = 'earliest-offset',  -- 从起始 offset 开始读取\n" +
            "            'connector.properties.zookeeper.connect' = '114.215.130.62:2181',  -- zookeeper 地址\n" +
            "            'connector.properties.bootstrap.servers' = '114.215.130.62:9092',  -- kafka broker 地址\n" +
            "            'format.type' = 'avro',  -- 数据源格式为 avro\n" +
            //"            'format.record-class' = 'com.alibaba.dts.formats.avro.Message',\n" +
            //"'format.avro-schema'='{\"namespace\":\"com.alibaba.dts.formats.avro\",\n" +
            //" \"type\": \"record\",\n" +
            //"      \"name\": \"category\",\n" +
            //"      \"fields\" : [\n" +
            //"            {\"name\": \"id\", \"type\": \"int\"},\n" +
            //"            {\"name\": \"value_text\", \"type\": \"string\"}\n" +
            //"      ]\n" +
            //" }',"+


            "'format.avro-schema'='{\n" +
            //"    \"namespace\": \"com.alibaba.dts.formats.avro\",\n" +
            //"    \"name\": \"category\",\n" +
            //"    \"type\": \"record\",\n" +
            //"    \"fields\": [\n" +
            //"      {\n" +
            //"        \"name\": \"id\",\n" +
            //"        \"type\": \"int\"\n" +
            //"      },\n" +
            //"      {\n" +
            //"        \"name\": \"value_text\",\n" +
            //"        \"type\": \"string\"\n" +
            //"      }\n" +
            //"    ]\n" +
            //"  } \n,"+
            //"     {\n" +
            "    \"namespace\": \"com.alibaba.dts.formats.avro\",\n" +
            "    \"type\": \"record\",\n" +
            "    \"name\": \"Integer\",\n" +
            "    \"fields\": [\n" +
            "      {\n" +
            "        \"name\": \"precision\",\n" +
            "        \"type\": \"int\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"name\": \"value\",\n" +
            "        \"type\": \"string\"\n" +
            "      }\n" +
            "    ]\n" +
            "  }' \n,"+

            "             'connector.properties.group.id' = 'test2'\n" +
            "        )";
    public static final String SINK_SQL_KAFKA_TEST = "CREATE TABLE kafkazzz( \n" +
            "        id int,\n" +
            "        value_text STRING\n" +
            "        ) \n" +
            "        WITH ( \n" +
            "            'connector.type' = 'kafka',  -- 使用 kafka connector\n" +
            "            'connector.version' = 'universal',  -- kafka 版本，universal 支持 0.11 以上的版本\n" +
            "            'connector.topic' = 'test',  -- kafka topic\n" +
            "            'connector.startup-mode' = 'earliest-offset',  -- 从起始 offset 开始读取\n" +
            "            'connector.properties.zookeeper.connect' = '114.215.130.62:2181',  -- zookeeper 地址\n" +
            "            'connector.properties.bootstrap.servers' = '114.215.130.62:9092',  -- kafka broker 地址\n" +
            "            'format.type' = 'json',  -- 数据源格式为 avro\n" +
            //"'format.avro-schema'='\n" +
            //"  {\n" +
            //"    \"namespace\": \"com.alibaba.dts.formats.avro\",\n" +
            //"    \"name\": \"category\",\n" +
            //"    \"type\": \"record\",\n" +
            //"    \"fields\": [\n" +
            //"      {\n" +
            //"        \"name\": \"value_text\",\n" +
            //"        \"type\": \"string\"\n" +
            //"      },\n" +
            //"      {\n" +
            //"        \"name\": \"id\",\n" +
            //"        \"type\": \"int\"\n" +
            //"      }\n" +
            //"    ]\n" +
            //"  }\n,"+ " {\n" +
            //"    \"namespace\": \"com.alibaba.dts.formats.avro\",\n" +
            //"    \"name\": \"Field\",\n" +
            //"    \"type\": \"record\",\n" +
            //"    \"fields\": [\n" +
            //"      {\n" +
            //"        \"name\": \"name\",\n" +
            //"        \"type\": \"string\"\n" +
            //"      },\n" +
            //"      {\n" +
            //"        \"name\": \"dataTypeNumber\",\n" +
            //"        \"type\": \"int\"\n" +
            //"      }\n" +
            //"    ]\n" +
            //"  },\n" +
            //"  {\n" +
            //"    \"namespace\": \"com.alibaba.dts.formats.avro\",\n" +
            //"    \"type\": \"record\",\n" +
            //"    \"name\": \"Integer\",\n" +
            //"    \"fields\": [\n" +
            //"      {\n" +
            //"        \"name\": \"precision\",\n" +
            //"        \"type\": \"int\"\n" +
            //"      },\n" +
            //"      {\n" +
            //"        \"name\": \"value\",\n" +
            //"        \"type\": \"string\"\n" +
            //"      }\n" +
            //"    ]\n" +
            //"  },\n" +
            //"  {\n" +
            //"    \"namespace\": \"com.alibaba.dts.formats.avro\",\n" +
            //"    \"type\": \"record\",\n" +
            //"    \"name\": \"Character\",\n" +
            //"    \"fields\": [\n" +
            //"      {\n" +
            //"        \"name\": \"charset\",\n" +
            //"        \"type\": \"string\"\n" +
            //"      },\n" +
            //"      {\n" +
            //"        \"name\": \"value\",\n" +
            //"        \"type\": \"bytes\"\n" +
            //"      }\n" +
            //"    ]\n" +
            //"  },\n" +
            //"  {\n" +
            //"    \"namespace\": \"com.alibaba.dts.formats.avro\",\n" +
            //"    \"type\": \"record\",\n" +
            //"    \"name\": \"Float\",\n" +
            //"    \"fields\": [\n" +
            //"      {\n" +
            //"        \"name\": \"value\",\n" +
            //"        \"type\": \"double\"\n" +
            //"      },\n" +
            //"      {\n" +
            //"        \"name\": \"precision\",\n" +
            //"        \"type\": \"int\"\n" +
            //"      },\n" +
            //"      {\n" +
            //"        \"name\": \"scale\",\n" +
            //"        \"type\": \"int\"\n" +
            //"      }\n" +
            //"    ]\n" +
            //"  },\n" +
            //"  {\n" +
            //"    \"namespace\": \"com.alibaba.dts.formats.avro\",\n" +
            //"    \"type\": \"record\",\n" +
            //"    \"name\": \"Decimal\",\n" +
            //"    \"fields\": [\n" +
            //"      {\n" +
            //"        \"name\": \"value\",\n" +
            //"        \"type\": \"string\"\n" +
            //"      },\n" +
            //"      {\n" +
            //"        \"name\": \"precision\",\n" +
            //"        \"type\": \"int\"\n" +
            //"      },\n" +
            //"      {\n" +
            //"        \"name\": \"scale\",\n" +
            //"        \"type\": \"int\"\n" +
            //"      }\n" +
            //"    ]\n" +
            //"  },\n" +
            //"  {\n" +
            //"    \"namespace\": \"com.alibaba.dts.formats.avro\",\n" +
            //"    \"type\": \"record\",\n" +
            //"    \"name\": \"Timestamp\",\n" +
            //"    \"fields\": [\n" +
            //"      {\n" +
            //"        \"name\": \"timestamp\",\n" +
            //"        \"type\": \"long\"\n" +
            //"      },\n" +
            //"      {\n" +
            //"        \"name\": \"millis\",\n" +
            //"        \"type\": \"int\"\n" +
            //"      }\n" +
            //"    ]\n" +
            //"  },\n" +
            //"  {\n" +
            //"    \"namespace\": \"com.alibaba.dts.formats.avro\",\n" +
            //"    \"type\": \"record\",\n" +
            //"    \"name\": \"DateTime\",\n" +
            //"    \"fields\": [\n" +
            //"      {\n" +
            //"        \"name\": \"year\",\n" +
            //"        \"default\": null,\n" +
            //"        \"type\": [\n" +
            //"          \"null\",\n" +
            //"          \"int\"\n" +
            //"        ]\n" +
            //"      },\n" +
            //"      {\n" +
            //"        \"name\": \"month\",\n" +
            //"        \"default\": null,\n" +
            //"        \"type\": [\n" +
            //"          \"null\",\n" +
            //"          \"int\"\n" +
            //"        ]\n" +
            //"      },\n" +
            //"      {\n" +
            //"        \"name\": \"day\",\n" +
            //"        \"default\": null,\n" +
            //"        \"type\": [\n" +
            //"          \"null\",\n" +
            //"          \"int\"\n" +
            //"        ]\n" +
            //"      },\n" +
            //"      {\n" +
            //"        \"name\": \"hour\",\n" +
            //"        \"default\": null,\n" +
            //"        \"type\": [\n" +
            //"          \"null\",\n" +
            //"          \"int\"\n" +
            //"        ]\n" +
            //"      },\n" +
            //"      {\n" +
            //"        \"name\": \"minute\",\n" +
            //"        \"default\": null,\n" +
            //"        \"type\": [\n" +
            //"          \"null\",\n" +
            //"          \"int\"\n" +
            //"        ]\n" +
            //"      },\n" +
            //"      {\n" +
            //"        \"name\": \"second\",\n" +
            //"        \"default\": null,\n" +
            //"        \"type\": [\n" +
            //"          \"null\",\n" +
            //"          \"int\"\n" +
            //"        ]\n" +
            //"      },\n" +
            //"      {\n" +
            //"        \"name\": \"millis\",\n" +
            //"        \"default\": null,\n" +
            //"        \"type\": [\n" +
            //"          \"null\",\n" +
            //"          \"int\"\n" +
            //"        ]\n" +
            //"      }\n" +
            //"    ]\n" +
            //"  },\n" +
            //"  {\n" +
            //"    \"namespace\": \"com.alibaba.dts.formats.avro\",\n" +
            //"    \"type\": \"record\",\n" +
            //"    \"name\": \"TimestampWithTimeZone\",\n" +
            //"    \"fields\": [\n" +
            //"      {\n" +
            //"        \"name\": \"value\",\n" +
            //"        \"type\": \"DateTime\"\n" +
            //"      },\n" +
            //"      {\n" +
            //"        \"name\": \"timezone\",\n" +
            //"        \"type\": \"string\"\n" +
            //"      }\n" +
            //"    ]\n" +
            //"  },\n" +
            //"  {\n" +
            //"    \"namespace\": \"com.alibaba.dts.formats.avro\",\n" +
            //"    \"type\": \"record\",\n" +
            //"    \"name\": \"BinaryGeometry\",\n" +
            //"    \"fields\": [\n" +
            //"      {\n" +
            //"        \"name\": \"type\",\n" +
            //"        \"type\": \"string\"\n" +
            //"      },\n" +
            //"      {\n" +
            //"        \"name\": \"value\",\n" +
            //"        \"type\": \"bytes\"\n" +
            //"      }\n" +
            //"    ]\n" +
            //"  },\n" +
            //"  {\n" +
            //"    \"namespace\": \"com.alibaba.dts.formats.avro\",\n" +
            //"    \"type\": \"record\",\n" +
            //"    \"name\": \"TextGeometry\",\n" +
            //"    \"fields\": [\n" +
            //"      {\n" +
            //"        \"name\": \"type\",\n" +
            //"        \"type\": \"string\"\n" +
            //"      },\n" +
            //"      {\n" +
            //"        \"name\": \"value\",\n" +
            //"        \"type\": \"string\"\n" +
            //"      }\n" +
            //"    ]\n" +
            //"  },\n" +
            //"  {\n" +
            //"    \"namespace\": \"com.alibaba.dts.formats.avro\",\n" +
            //"    \"type\": \"record\",\n" +
            //"    \"name\": \"BinaryObject\",\n" +
            //"    \"fields\": [\n" +
            //"      {\n" +
            //"        \"name\": \"type\",\n" +
            //"        \"type\": \"string\"\n" +
            //"      },\n" +
            //"      {\n" +
            //"        \"name\": \"value\",\n" +
            //"        \"type\": \"bytes\"\n" +
            //"      }\n" +
            //"    ]\n" +
            //"  },\n" +
            //"  {\n" +
            //"    \"namespace\": \"com.alibaba.dts.formats.avro\",\n" +
            //"    \"type\": \"record\",\n" +
            //"    \"name\": \"TextObject\",\n" +
            //"    \"fields\": [\n" +
            //"      {\n" +
            //"        \"name\": \"type\",\n" +
            //"        \"type\": \"string\"\n" +
            //"      },\n" +
            //"      {\n" +
            //"        \"name\": \"value\",\n" +
            //"        \"type\": \"string\"\n" +
            //"      }\n" +
            //"    ]\n" +
            //"  },\n" +
            //"  {\n" +
            //"      \"namespace\": \"com.alibaba.dts.formats.avro\",\n" +
            //"      \"type\": \"enum\",\n" +
            //"      \"name\": \"EmptyObject\",\n" +
            //"      \"symbols\" : [\"NULL\", \"NONE\"]\n" +
            //"  },\n" +
            //
            //"  {\n" +
            //"    \"namespace\": \"com.alibaba.dts.formats.avro\",\n" +
            //"    \"type\": \"record\",\n" +
            //"    \"name\": \"Record\",\n" +
            //"    \"fields\": [\n" +
            //"      {\n" +
            //"        \"name\": \"version\",\n" +
            //"        \"type\": \"int\",\n" +
            //"        \"doc\": \"version infomation\"\n" +
            //"      },\n" +
            //"      {\n" +
            //"        \"name\": \"id\",\n" +
            //"        \"type\": \"long\",\n" +
            //"        \"doc\": \"unique id of this record in the whole stream\"\n" +
            //"      },\n" +
            //"      {\n" +
            //"        \"name\": \"sourceTimestamp\",\n" +
            //"        \"type\": \"long\",\n" +
            //"        \"doc\": \"record log timestamp\"\n" +
            //"      },\n" +
            //"      {\n" +
            //"        \"name\": \"sourcePosition\",\n" +
            //"        \"type\": \"string\",\n" +
            //"        \"doc\": \"record source location information\"\n" +
            //"      },\n" +
            //"      {\n" +
            //"        \"name\": \"safeSourcePosition\",\n" +
            //"        \"type\": \"string\",\n" +
            //"        \"default\": \"\",\n" +
            //"        \"doc\": \"safe record source location information, use to recovery.\"\n" +
            //"      },\n" +
            //"      {\n" +
            //"        \"name\": \"sourceTxid\",\n" +
            //"        \"type\": \"string\",\n" +
            //"        \"default\": \"\",\n" +
            //"        \"doc\": \"record transation id\"\n" +
            //"      },\n" +
            //"      {\n" +
            //"        \"name\": \"source\",\n" +
            //"        \"doc\": \"source dataource\",\n" +
            //"        \"type\": {\n" +
            //"          \"namespace\": \"com.alibaba.dts.formats.avro\",\n" +
            //"          \"name\": \"Source\",\n" +
            //"          \"type\": \"record\",\n" +
            //"          \"fields\": [\n" +
            //"            {\n" +
            //"              \"name\": \"sourceType\",\n" +
            //"              \"type\": {\n" +
            //"                \"namespace\": \"com.alibaba.dts.formats.avro\",\n" +
            //"                \"name\": \"SourceType\",\n" +
            //"                \"type\": \"enum\",\n" +
            //"                \"symbols\": [\n" +
            //"                  \"MySQL\",\n" +
            //"                  \"Oracle\",\n" +
            //"                  \"SQLServer\",\n" +
            //"                  \"PostgreSQL\",\n" +
            //"                  \"MongoDB\",\n" +
            //"                  \"Redis\",\n" +
            //"                  \"DB2\",\n" +
            //"                  \"PPAS\",\n" +
            //"                  \"DRDS\",\n" +
            //"                  \"HBASE\",\n" +
            //"                  \"HDFS\",\n" +
            //"                  \"FILE\",\n" +
            //"                  \"OTHER\"\n" +
            //"                ]\n" +
            //"              }\n" +
            //"            },\n" +
            //"            {\n" +
            //"              \"name\": \"version\",\n" +
            //"              \"type\": \"string\",\n" +
            //"              \"doc\": \"source datasource version information\"\n" +
            //"            }\n" +
            //"          ]\n" +
            //"        }\n" +
            //"      },\n" +
            //"      {\n" +
            //"        \"namespace\": \"com.alibaba.dts.formats.avro\",\n" +
            //"        \"name\": \"operation\",\n" +
            //"        \"type\": {\n" +
            //"          \"name\": \"Operation\",\n" +
            //"          \"type\": \"enum\",\n" +
            //"          \"symbols\": [\n" +
            //"            \"INSERT\",\n" +
            //"            \"UPDATE\",\n" +
            //"            \"DELETE\",\n" +
            //"            \"DDL\",\n" +
            //"            \"BEGIN\",\n" +
            //"            \"COMMIT\",\n" +
            //"            \"ROLLBACK\",\n" +
            //"            \"ABORT\",\n" +
            //"            \"HEARTBEAT\",\n" +
            //"            \"CHECKPOINT\",\n" +
            //"            \"COMMAND\",\n" +
            //"            \"FILL\",\n" +
            //"            \"FINISH\",\n" +
            //"            \"CONTROL\",\n" +
            //"            \"RDB\",\n" +
            //"            \"NOOP\",\n" +
            //"\t    \"INIT\"\n" +
            //"          ]\n" +
            //"        }\n" +
            //"      },\n" +
            //"      {\n" +
            //"        \"name\": \"objectName\",\n" +
            //"        \"default\": null,\n" +
            //"        \"type\": [\n" +
            //"          \"null\",\n" +
            //"          \"string\"\n" +
            //"        ]\n" +
            //"      },\n" +
            //"      {\n" +
            //"        \"name\": \"processTimestamps\",\n" +
            //"        \"default\": null,\n" +
            //"        \"type\": [\n" +
            //"          \"null\",\n" +
            //"          {\n" +
            //"            \"type\": \"array\",\n" +
            //"            \"items\": \"long\"\n" +
            //"          }\n" +
            //"        ],\n" +
            //"        \"doc\": \"time when this record is processed along the stream dataflow\"\n" +
            //"      },\n" +
            //"      {\n" +
            //"        \"name\": \"tags\",\n" +
            //"        \"default\": {},\n" +
            //"        \"type\": {\n" +
            //"          \"type\": \"map\",\n" +
            //"          \"values\": \"string\"\n" +
            //"        },\n" +
            //"        \"doc\": \"tags to identify properties of this record\"\n" +
            //"      },\n" +
            //"      {\n" +
            //"        \"name\": \"fields\",\n" +
            //"        \"default\": null,\n" +
            //"        \"type\": [\n" +
            //"          \"null\",\n" +
            //"          \"string\",\n" +
            //"          {\n" +
            //"            \"type\": \"array\",\n" +
            //"            \"items\": \"com.alibaba.dts.formats.avro.Field\"\n" +
            //"          }\n" +
            //"        ]\n" +
            //"      },\n" +
            //"      {\n" +
            //"        \"name\": \"beforeImages\",\n" +
            //"        \"default\": null,\n" +
            //"        \"type\": [\n" +
            //"          \"null\",\n" +
            //"          \"string\",\n" +
            //"          {\n" +
            //"            \"type\": \"array\",\n" +
            //"            \"items\": [\n" +
            //"              \"null\",\n" +
            //"              \"com.alibaba.dts.formats.avro.Integer\",\n" +
            //"              \"com.alibaba.dts.formats.avro.Character\",\n" +
            //"              \"com.alibaba.dts.formats.avro.Decimal\",\n" +
            //"              \"com.alibaba.dts.formats.avro.Float\",\n" +
            //"              \"com.alibaba.dts.formats.avro.Timestamp\",\n" +
            //"              \"com.alibaba.dts.formats.avro.DateTime\",\n" +
            //"              \"com.alibaba.dts.formats.avro.TimestampWithTimeZone\",\n" +
            //"              \"com.alibaba.dts.formats.avro.BinaryGeometry\",\n" +
            //"              \"com.alibaba.dts.formats.avro.TextGeometry\",\n" +
            //"              \"com.alibaba.dts.formats.avro.BinaryObject\",\n" +
            //"              \"com.alibaba.dts.formats.avro.TextObject\",\n" +
            //"              \"com.alibaba.dts.formats.avro.EmptyObject\"\n" +
            //"            ]\n" +
            //"          }\n" +
            //"        ]\n" +
            //"      },\n" +
            //"      {\n" +
            //"        \"name\": \"afterImages\",\n" +
            //"        \"default\": null,\n" +
            //"        \"type\": [\n" +
            //"          \"null\",\n" +
            //"          \"string\",\n" +
            //"          {\n" +
            //"            \"type\": \"array\",\n" +
            //"            \"items\": [\n" +
            //"              \"null\",\n" +
            //"              \"com.alibaba.dts.formats.avro.Integer\",\n" +
            //"              \"com.alibaba.dts.formats.avro.Character\",\n" +
            //"              \"com.alibaba.dts.formats.avro.Decimal\",\n" +
            //"              \"com.alibaba.dts.formats.avro.Float\",\n" +
            //"              \"com.alibaba.dts.formats.avro.Timestamp\",\n" +
            //"              \"com.alibaba.dts.formats.avro.DateTime\",\n" +
            //"              \"com.alibaba.dts.formats.avro.TimestampWithTimeZone\",\n" +
            //"              \"com.alibaba.dts.formats.avro.BinaryGeometry\",\n" +
            //"              \"com.alibaba.dts.formats.avro.TextGeometry\",\n" +
            //"              \"com.alibaba.dts.formats.avro.BinaryObject\",\n" +
            //"              \"com.alibaba.dts.formats.avro.TextObject\",\n" +
            //"              \"com.alibaba.dts.formats.avro.EmptyObject\"\n" +
            //"            ]\n" +
            //"          }\n" +
            //"        ]\n" +
            //"      }\n" +
            //"    ]\n" +
            //"  }'\n,"+


            "             'connector.properties.group.id' = 'test2'\n" +
            "        )";

    public static final String KAFKA_SELECT_ALL = "insert into kafkazzz   select id,value_text from ot_test2";

    public static final String SOURCE_MYSQL_NEW = "\n" +
            "CREATE TABLE ot_test2(\n" +
            "                id int,\n" +
            "                value_text VARCHAR\n" +
            "                ) \n" +
            "                WITH ( \n" +
            "                'connector.type' = 'jdbc', \n" +
            "                'connector.url' = 'jdbc:mysql://114.215.130.62:3306/flink', \n" +
            "                'connector.table' = 'test', \n" +
           // "                'update-mode' = 'append'  ,"+
            "                'connector.driver' = 'com.mysql.jdbc.Driver', \n" +
            "                'connector.username' = 'root', \n" +
            "                'connector.password' = '123456'\n" +
          //  "                'connector.lookup.cache.max-rows' = '5000', \n" +
           // "                'connector.lookup.cache.ttl' = '10min' \n" +
            //"                'connector.write.flush.max-rows' = '5', \n" +
            //"                'connector.write.flush.interval' = '2s', \n" +
            //"                'connector.write.max-retries' = '3' \n" +
            "                )";

}
