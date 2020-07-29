package com.mylearn.connection;

import org.junit.Test;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;


public class ConnectionTest {

    //方法一
    @Test
    public void getConnection1() throws SQLException {
        //TODO 获取Driver的实现类对象
        Driver driver = new com.mysql.jdbc.Driver();

        //TODO jdbc:mysql: 协议
        //TODO localhost: ip地址
        //TODO 3306: 默认mysql端口号
        //TODO book: 数据库名
        String url = "jdbc:mysql://localhost:3306/book";
        //TODO 将用户名和密码封装在Properties里
        Properties info = new Properties();
        info.setProperty("user", "root");
        info.setProperty("password", "abcd@1234");

        //TODO 获取连接
        Connection conn = driver.connect(url, info);

        System.out.println(conn);
    }

    //方式二，对方法一的迭代
    //没有出现第三方的API，是的程序有更好的移植性
    public void getConnection2() throws Exception {
        //1、获取Driver实现类对象，使用反射
        Class<?> clazz = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver) clazz.newInstance();

        //2、提供连接的数据库
        String url = "jdbc:mysql://localhost:3306/book";
        //TODO 将用户名和密码封装在Properties里
        Properties info = new Properties();
        info.setProperty("user", "root");
        info.setProperty("password", "abcd@1234");

        //TODO 获取连接
        Connection conn = driver.connect(url, info);

        System.out.println(conn);
    }

    //方法三，使用DriverManager替换Driver
    //DriverManager是一个类
    @Test
    public void testConnection3() throws Exception {
        //1、获取Driver实现类对象，使用反射
        Class<?> clazz = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver) clazz.newInstance();

        String url = "jdbc:mysql://localhost:3306/book";
        String user = "root";
        String password = "abcd@1234";

        //注册驱动
        DriverManager.registerDriver(driver);

        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);
    }

    //方法四，只是加载驱动，不用显示的注册驱动了
    @Test
    public void testConnection4() throws Exception {
        //1、提供基本信息
        String url = "jdbc:mysql://localhost:3306/book";
        String user = "root";
        String password = "abcd@1234";

        //2、加载Driver
        Class.forName("com.mysql.jdbc.Driver");
        //相较于方式三，可以省略如下操作
//        Driver driver = (Driver) clazz.newInstance();
//
//        //注册驱动
//        DriverManager.registerDriver(driver);
        /**
         * 为什么可以省略操作，见mySQL的Driver实现类源码，有了静态的代码块
         */


        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);
    }

    /**
     * 方法五(final)，将相关信息放在配置文件中,读取配置文件，获取基本信息
     * TODO 好处有：1、数据与代码分离，实现了解耦
     *  TODO 2、修改配置信息，不需要程序重新打包
     * @throws Exception
     */
    @Test
    public void testConnection5() throws Exception {

        //1、读取配置文件、获取基本信息
        //默认的路径为src
        InputStream is = ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties pros = new Properties();
        pros.load(is);

        String user = pros.getProperty("user");
        String url = pros.getProperty("url");
        String password = pros.getProperty("password");
        String driverClass = pros.getProperty("driverClass");


        //2、加载Driver
        Class.forName(driverClass);

        //3、获取连接
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);

    }


}
