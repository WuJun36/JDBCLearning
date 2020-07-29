package com.mylearn.exer;

import com.mylearn.utils.JdbcUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

//课后联系1
public class ExerTest {

    @Test
    public void testInsert() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入用户名：");
        String name = scanner.next();
        System.out.println("请输入邮箱：");
        String email = scanner.next();
        System.out.println("请输入生日：");
        String birthDay = scanner.next();

        String sql = "INSERT INTO customer(cust_name,email,birth) VALUES(?,?,?)";
        int insertCount = update(sql, name, email, birthDay);

        if (insertCount > 0) {
            System.out.println("插入成功");
        } else {
            System.out.println("插入失败");
        }
    }

    @Test
    public void testQuery(){

    }

    public int update(String sql, Object... args) {
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
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //TODO 5、关闭资源
            JdbcUtils.closeResources(conn, ps);
        }
        return 0;
    }
}
