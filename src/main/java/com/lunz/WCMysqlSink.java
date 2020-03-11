package com.lunz;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

import java.sql.*;

public class WCMysqlSink extends RichSinkFunction<String> {


    private static final long serialVersionUID = 1L;

    private Connection connection = null;

    private PreparedStatement preparedStatement = null;

    //mysql连接信息
    //private String username = "user_flink";
    //private String password = "Lunz2017";
    //private String drivername = "com.mysql.jdbc.Driver";
    //private String dburl = "jdbc:mysql://rm-bp1oydriw2vi7fc68qo.mysql.rds.aliyuncs.com/flink_dev?useUnicode=true&characterEncoding=UTF-8&useSSL=true";
    private String username = "root";
    private String password = "123456";
    private String drivername = "com.mysql.jdbc.Driver";
    private String dburl = "jdbc:mysql://localhost:3306/flink_dev?useUnicode=true&characterEncoding=UTF-8";


    private static Statement statement;


    @Override
    public void open(Configuration parameters) throws Exception {
        connection = DriverManager.getConnection(dburl,username,password);
        Class.forName(drivername);//加载数据库驱动
     // String sql = "update wingcloud.shop_money set money=money+(?) where money in (select temp.money from (select money from wingcloud.shop_money)temp)";
       // preparedStatement = connection.prepareStatement(sql);
        super.open(parameters);
    }


    @Override
    public void invoke(String money) throws SQLException, ClassNotFoundException {
        if(connection==null) {
            Class.forName(drivername);
            connection = DriverManager.getConnection(dburl, username, password);
        }



    }


    @Override
    public void close() throws Exception {
        if(preparedStatement!=null){
            preparedStatement.close();
        }
        if(connection!=null){
            connection.close();
        }
        super.close();
    }






}
