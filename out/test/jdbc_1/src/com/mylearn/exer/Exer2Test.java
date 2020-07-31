package com.mylearn.exer;

import com.mylearn.bean.ExamStudent;
import com.mylearn.utils.JdbcUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 课后练习2
 */
public class Exer2Test {

    @Test
    public void testDelete() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入要删除的准考证号：");
        String examCard = scanner.next();
        String sql = "DELETE FROM exam_student WHERE exam_card = ?";
        int deleteCount = update(sql, examCard);
        if (deleteCount > 0) {
            System.out.println("删除成功");
        } else {
            System.out.println("查无此人");
        }
    }

    @Test
    public void testQuery() {
        Scanner scanner = new Scanner(System.in);
        StringBuilder sql = new StringBuilder("SELECT flow_id flowId,type,id_card IDCard,exam_card examCard,student_name studentName,location,grade FROM exam_student WHERE ");
        while (true) {
            System.out.println("用身份证查询请输入1，用准考证查询请输入2：");
            int selectType = scanner.nextInt();
            if (selectType == 1) {
                sql.append("id_card = ?");
                System.out.println("请输入身份证:");
                break;
            } else if (selectType == 2) {
                sql.append("exam_card = ?");
                System.out.println("请输入准考证:");
                break;
            } else {
                System.out.println("输入类型错误，请重新输入！");
                continue;
            }
        }

        String cardNumber = scanner.next();

        ExamStudent examStudent = getInstance(ExamStudent.class, sql.toString(), cardNumber);
        if (examStudent != null) {
            System.out.println(examStudent);
        } else {
            System.out.println("结果为空");
        }

    }

    public <T> T getInstance(Class<T> clazz, String sql, Object... args) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
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
            JdbcUtils.closeResources(conn, ps, rs);
        }

        return null;

    }

    //向exam_student中插入数据
    @Test
    public void testInsert() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("四/六级：");
        int type = scanner.nextInt();
        System.out.println("身份证号：");
        String IDCard = scanner.next();
        System.out.println("准考证号：");
        String examCard = scanner.next();
        System.out.println("学生姓名：");
        String studentName = scanner.next();
        System.out.println("所在城市：");
        String location = scanner.next();
        System.out.println("考试成绩：");
        int grade = scanner.nextInt();

        String sql = "INSERT INTO exam_student(type,id_card,exam_card,student_name,location,grade) VALUES(?,?,?,?,?,?)";
        int insertCount = update(sql, type, IDCard, examCard, studentName, location, grade);

        if (insertCount > 0) {
            System.out.println("插入成功");
        } else {
            System.out.println("插入失败");
        }
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
