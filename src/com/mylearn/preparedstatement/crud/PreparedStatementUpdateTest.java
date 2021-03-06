package com.mylearn.preparedstatement.crud;

import com.mylearn.connection.ConnectionTest;
import com.mylearn.utils.JdbcUtils;
import org.junit.Test;

import javax.sql.rowset.JdbcRowSet;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;


/**
 * 增删改，查
 * 1、什么叫做数据库事务？
 * 事务：一组逻辑操作单元，使数据从一种状态变换到另一种状态
 * > 一组逻辑操作单元，一个或多个DML语句
 * <p>
 * 2、事务处理的原则
 * 一个事务里的DML操作要么都执行，要么都不执行
 * <p>
 * 3、数据一旦提交，就不可回滚
 * <p>
 * 4、哪些操作会导致数据的自动提交
 * >DDL操作，一旦执行都会自动提交
 * >DML操作，默认情况下，一旦执行就会自动提交
 * 但是可以通过 set autocommit = false的方式取消DML的自动提交（对DDL无效）
 * >
 */
public class PreparedStatementUpdateTest {



    @Test
    public void testCommonUpdate() {
//        String sql = "INSERT INTO t_user(username,password,email) VALUES(?,?,?)";
//        commonUpdate(sql,"winwin","winwin","winwin@gmail.cn");

        String sql = "UPDATE t_user SET email = ? WHERE id = ?";
        commonUpdate(sql, "winwin@dsc.cn", 3);

    }

    /**
     * 通用的【增删改】操作
     */
    public void commonUpdate(String sql, Object... args) {

        Connection conn = null;
        PreparedStatement ps = null;
        try {
            //TODO 1、获取数据库连接
            conn = JdbcUtils.getConnection();
            //TODO 2、预编译SQL
            ps = conn.prepareStatement(sql);
            //TODO 3、补充占位符
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            //TODO 4、执行
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //TODO 5、关闭资源
            JdbcUtils.closeResources(conn, ps);
        }

    }

    /**
     * 向表中添加一条记录
     */
    @Test
    public void testInsert() throws Exception {
        //1、读取配置文件、获取基本信息
        //默认的路径为src
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
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

        //4、预编译SQL语句
        String sql = "INSERT INTO t_user(username,password,email) VALUES(?,?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);

        //5、补充占位符
        ps.setString(1, "winwin");
        ps.setString(2, "winwin");
        ps.setString(3, "winwin@gmail.com");

        boolean isSuccess = ps.execute();  //返回值为false时，说明没有返回集或者是更新的数量
        System.out.println(isSuccess);
    }

    /**
     * 从表中删除一条数据
     */
    @Test
    public void testDelete() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            //TODO 1、获取连接
            conn = JdbcUtils.getConnection();
            //TODO 2、预编译SQL语句
            String sql = "DELETE FROM t_user WHERE id = ?";
            ps = conn.prepareStatement(sql);

            //TODO 3、补充占位符
            ps.setObject(1, 2);

            //TODO 4、执行
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            //TODO 5、关闭资源
            JdbcUtils.closeResources(conn, ps);
        }

    }
}
