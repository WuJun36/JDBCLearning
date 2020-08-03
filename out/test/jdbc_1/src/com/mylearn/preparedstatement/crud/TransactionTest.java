package com.mylearn.preparedstatement.crud;

import com.mylearn.bean.ExamStudent;
import com.mylearn.utils.JdbcUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.*;

public class TransactionTest {

    //**********************考虑数据库事务的操作******************************
    @Test
    public void testTransaction() throws Exception {

        Connection conn = JdbcUtils.getConnection();
        try {
            //TODO 设置DML操作的自动提交为false
            conn.setAutoCommit(false);
            String sql = "UPDATE exam_student SET grade = grade - 1 WHERE flow_id = ?";
            update(conn, sql, 1);
            System.out.println("sql1 done");
            System.out.println(14 / 0);
            String sql2 = "UPDATE exam_student SET grade = grade + 1 WHERE flow_id = ?";
            update(conn, sql2, 2);
            //TODO 事务操作完成，提交修改
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            //TODO 中途出现异常则回退
            conn.rollback();
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            JdbcUtils.closeResources(conn, null);
        }
    }


    /**
     * 通用的【增删改】操作
     */
    public void update(Connection conn, String sql, Object... args) {

        PreparedStatement ps = null;
        try {
//            //TODO 1、获取数据库连接
//            conn = JdbcUtils.getConnection();
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
            JdbcUtils.closeResources(null, ps);
        }
    }


    //************************************************************
    @Test
    public void testTransactionUpdate() throws Exception {

        Connection conn = JdbcUtils.getConnection();
        //TODO 取消自动提交数据
        conn.setAutoCommit(false);
        String sql = "UPDATE exam_student set grade = ? where flow_id = ?";
        update(conn, sql, 80, 1);

        Thread.sleep(15000);
        System.out.println("修改结束");
    }

    @Test
    public void testTransactionQuery() throws Exception {
        Connection conn = JdbcUtils.getConnection();
        //TODO 获取当前链接的隔离级别
        conn.setTransactionIsolation(conn.TRANSACTION_READ_COMMITTED);
        System.out.println(conn.getTransactionIsolation());
        //TODO 取消自动提交数据
        conn.setAutoCommit(false);
        String sql = "SELECT flow_id flowId,student_name studentName,grade FROM exam_student WHERE flow_id = ?";
        ExamStudent student = getInstance(conn, ExamStudent.class, sql, 1);

        System.out.println(student);
    }


    //通用的查询操作，用于返回表中的一条记录（考虑上事务）
    public <T> T getInstance(Connection conn, Class<T> clazz, String sql, Object... args) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            if (rs.next()) {
                T t = clazz.newInstance();
                for (int i = 0; i < columnCount; i++) {
                    //获取列值
                    Object columnValue = rs.getObject(i + 1);
                    //获取列别名
                    String columnLabel = rsmd.getColumnLabel(i + 1);

                    //把列值放入对应的属性中
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, columnValue);
                }
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResources(null, ps, rs);
        }
        return null;
    }
}
