package com.mylearn.preparedstatement.crud;

import com.mylearn.bean.Book;
import com.mylearn.bean.UserDto;
import com.mylearn.utils.JdbcUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class CommonQuery {

    @Test
    public void testQuery() {
        String sql = "SELECT book_id bookId,book_title bookTitle,author,publisher FROM t_book WHERE book_id = ?";
        Book book = getInstance(Book.class, sql, 1);
        System.out.println(book);

        String sql1 = "SELECT book_id bookId,book_title bookTitle,author,publisher FROM t_book";
        List<Book> list1 = getInstances(Book.class, sql1);
        list1.forEach(System.out::println);

        String sql2 = "SELECT id,username,password FROM t_user";
        List<UserDto> list2 = getInstances(UserDto.class, sql2);
        list2.forEach(System.out::println);
    }

    public <T> List<T> getInstances(Class<T> clazz, String sql, Object... args) {

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
            List<T> list = new ArrayList<>();

            while (rs.next()) {
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
                list.add(t);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResources(conn, ps, rs);
        }

        return null;

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
}
