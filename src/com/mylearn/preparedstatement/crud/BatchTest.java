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
    public void testInsert(){
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
            JdbcUtils.closeResources(conn,ps);
        }
    }

}
