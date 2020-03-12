package com.lunz;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

import java.sql.*;

public class MysqlSink extends RichSinkFunction<Test> {


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
    private String dburl = "jdbc:mysql://114.215.130.62:3306/flink_dev?useUnicode=true&characterEncoding=UTF-8";


    private static Statement statement;


    @Override
    public void open(Configuration parameters) throws Exception {

        super.open(parameters);
        connection = DriverManager.getConnection(dburl, username, password);
        Class.forName(drivername);
        String sql = "insert into testsink(id,name,age) values(?,?,?)";

        if (connection != null) {
            preparedStatement = this.connection.prepareStatement(sql);
        }

    }


    @Override
    public void invoke(Test test, Context context) throws SQLException, ClassNotFoundException {
        if(preparedStatement==null) {
          return ;
        }
        preparedStatement.setInt(1, test.getId());
        preparedStatement.setString(2, test.getName());
        preparedStatement.setInt(3, test.getAge());
        preparedStatement.executeUpdate();
    }


    @Override
    public void close() throws Exception {
        super.close();
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        if (connection != null) {
            connection.close();
        }
    }


}
