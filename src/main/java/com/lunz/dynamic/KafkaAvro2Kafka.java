package com.lunz.dynamic;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.java.StreamTableEnvironment;

public class KafkaAvro2Kafka {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        EnvironmentSettings envSettings = EnvironmentSettings.newInstance()
                .useBlinkPlanner()
                .inStreamingMode()
                .build();
        StreamTableEnvironment tableEnvironment = StreamTableEnvironment.create(env, envSettings);

        // step 1, should note step 2 when call
         //flinkCsv2Avro(tableEnvironment);

        // step 2, should note step 1 when call
        flinkAvro2Avro(tableEnvironment);

    }

    private static void flinkCsv2Avro(StreamTableEnvironment tableEnvironment) throws Exception{
        String csvSourceDDL = "create table csv(" +
                " id int," +
                " value_text VARCHAR" +
                ") with (" +
                " 'connector.type' = 'filesystem',\n" +
                " 'connector.path' = '/root/yitong/user.csv',\n" +
                " 'format.type' = 'csv',\n" +
                " 'format.fields.0.name' = 'id',\n" +
                " 'format.fields.0.data-type' = 'int',\n" +
                " 'format.fields.1.name' = 'value_text',\n" +
                " 'format.fields.1.data-type' = 'STRING')";

        tableEnvironment.sqlUpdate(csvSourceDDL);

        tableEnvironment.sqlUpdate("CREATE TABLE AvroTest (\n" +
                "  id int,\n" +
                "  value_text VARCHAR" +
                ") WITH (\n" +
                "  'connector.type' = 'kafka',\n" +
                "  'connector.version' = 'universal',\n" +
                "  'connector.topic' = 'csv_test',\n" +
                "  'connector.properties.zookeeper.connect' = '114.215.130.62:2181',\n" +
                "  'connector.properties.bootstrap.servers' = '114.215.130.62:9092',\n" +
                "  'connector.properties.group.id' = 'testGroup3',\n" +
                "  'connector.startup-mode' = 'earliest-offset',\n" +
                "  'format.type' = 'avro',\n" +
                "  'format.record-class' = 'kafka.UserAvro'\n" +
                ")\n");
        String querySQL = "insert into AvroTest select id,value_text from csv ";
        tableEnvironment.sqlUpdate(querySQL);
        tableEnvironment.execute("FlinkCsv2Avro");
    }

    private static void flinkAvro2Avro(StreamTableEnvironment tableEnvironment) throws Exception{

        tableEnvironment.sqlUpdate("CREATE TABLE AvroTest (\n" +
                "  id INT,\n" +
                "  value_text VARCHAR\n" +
                ") WITH (\n" +
                "  'connector.type' = 'kafka',\n" +
                "  'connector.version' = 'universal',\n" +
                "  'connector.topic' = 'flinktest2',\n" +
                "  'connector.properties.zookeeper.connect' = '114.215.130.62:2181',\n" +
                "  'connector.properties.bootstrap.servers' = '114.215.130.62:9092',\n" +
                "  'connector.properties.group.id' = 'testGroup4',\n" +
                "  'connector.startup-mode' = 'earliest-offset',\n" +
                "  'format.type' = 'avro',\n" +
                "  'format.avro-schema' =\n" +
                "    '{ \n" +
                "    \"type\": \"record\",\n" +
                "    \"name\": \"category\",\n" +
                "    \"fields\": [\n" +
                "      {\"name\": \"id\", \"type\": \"int\"},\n" +
                "      {\"name\": \"value_text\", \"type\": \"string\"}\n" +
                "      ]\n" +
                "    }'" +
                ")\n");

        String sinkTableDDL = "CREATE TABLE WikipediaFeed_filtered (\n" +
                "  id int,\n" +
                "  value_text STRING" +
                ") WITH (\n" +
                "  'connector.type' = 'kafka',\n" +
                "  'connector.version' = 'universal',\n" +
                "  'connector.topic' = 'WikipediaFeed2_filtered',\n" +
                "  'connector.properties.zookeeper.connect' = '114.215.130.62:2181',\n" +
                "  'connector.properties.bootstrap.servers' = '114.215.130.62:9092',\n" +
                "  'connector.properties.group.id' = 'testGroup3',\n" +
                "  'connector.startup-mode' = 'earliest-offset',\n" +
                "  'format.type' = 'avro',\n" +
                "  'format.avro-schema' =\n" +
                "    '{ \n" +
                "    \"type\": \"record\",\n" +
                "    \"name\": \"category\",\n" +
                "    \"fields\": [\n" +
                "      {\"name\": \"id\", \"type\": \"int\"},\n" +
                "      {\"name\": \"value_text\", \"type\": \"string\"}\n" +
                "      ]\n" +
                "    }'" +
                ")\n";
        tableEnvironment.sqlUpdate(sinkTableDDL);

        String querySQL = "insert into WikipediaFeed_filtered \n" +
                "select id,value_text \n" +
                "from AvroTest\n" ;

        tableEnvironment.sqlUpdate(querySQL);
        tableEnvironment.execute("FlinkAvro2Avro");
    }
}
