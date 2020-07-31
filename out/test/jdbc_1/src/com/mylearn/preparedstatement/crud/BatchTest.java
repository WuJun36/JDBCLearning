package com.mylearn.preparedstatement.crud;

import com.mylearn.utils.JdbcUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * 利用PreparedStatement实现批量数据的操作
 * <p>
 * update、delete本身就具有批量操作的效果
 * <p>
 * 此时的批量操作，主要指批量插入，使用PreparedStatement如何实现更高效的批量插入？
 * <p>
 * 题目：向goods表中插入20000条数据
 * CREATE TABLE goods(
 * id INT PRIMARY KEY AUTO_INCREMENT,
 * NAME VARCHAR(25)
 * );
 */
public class BatchTest {
    @Test
    public void testInsert() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            long start = System.currentTimeMillis();
            conn = JdbcUtils.getConnection();

            String sql = "INSERT INTO goods(NAME) VALUES(?)";
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < 20000; i++) {
                ps.setObject(1, "name_" + i);
                ps.execute();
            }
            long end = System.currentTimeMillis();
            System.out.println("花费的时间为：" + (end - start));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResources(conn, ps);
        }
    }

    /**
     * 批量插入的方式二
     * 1、addBatch(),executeBatch(),clearBatch()
     * 2、mysql服务器默认是关闭批处理的，我们需要通过一个参数，让mysql开启批处理的支持
     *    ?rewriteBatchedStatements=true 写在配置文件的url后面
     * 3、更新驱动jar包 以前的版本不支持批量写入，可更新为mysql-connector-java-5.1.37.jar
     */
    @Test
    public void testInsert2() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            long start = System.currentTimeMillis();
            conn = JdbcUtils.getConnection();

            String sql = "INSERT INTO goods(NAME) VALUES(?)";
            ps = conn.prepareStatement(sql);
            for (int i = 1; i <= 20000; i++) {
                ps.setObject(1, "name_" + i);
                //TODO 1、"攒"SQL
                ps.addBatch();

                if (i % 500 == 0) {
                    //TODO 2、执行Batch
                    ps.executeBatch();

                    //TODO 3、清空Batch
                    ps.clearBatch();
                }
            }
            long end = System.currentTimeMillis();
            System.out.println("花费的时间为：" + (end - start));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResources(conn, ps);
        }
    }

}

