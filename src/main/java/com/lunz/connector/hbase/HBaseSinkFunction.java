package com.lunz.connector.hbase;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

/**
 * @author Liuruixia
 * @Description: 自定义hbase sink
 * @date 2020/03/13
 */

public class HBaseSinkFunction extends RichSinkFunction<String>{
@Override
public void open(Configuration parameters) throws Exception {
        //super.open(parameters);
        //HbaseUtils.connectHbase();
        //TableName table=TableName.valueOf(Constants.tableNameStr);
        //Admin admin = HbaseUtils.connection.getAdmin();
        //if(!admin.tableExists(table)){
        //HTableDescriptor tableDescriptor = new HTableDescriptor(Constants.tableNameStr);
        //tableDescriptor.addFamily(new HColumnDescriptor(Constants.columnFamily));
        //admin.createTable(tableDescriptor);
        //}
        //}
        //@Override
        //public void invoke(List<Put> putList, Context context) throws Exception {
        //Table table=HbaseUtils.connection.getTable(TableName.valueOf(Constants.tableNameStr));
        //table.put(putList);
        //}
        //@Override
        //public void close() throws Exception {
        //super.close();
        //HbaseUtils.closeHBaseConnect();
        //}
}}
