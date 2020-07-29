package com.mylearn.preparedstatement.crud;

import com.mylearn.bean.Book;
import com.mylearn.utils.JdbcUtils;
import org.junit.Test;

import javax.jws.Oneway;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class QueryForBook {

    public List<Book> bookListForQuery(String sql, Object... args){
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

            List<Book> list = new ArrayList<>();
            while (rs.next()) {
                Book book = new Book();
                for (int i = 0; i < columnCount; i++) {
                    //获取列值
                    Object columnValue = rs.getObject(i + 1);

                    //获取列名
                    //获取列的列名：getColumnName --不推荐使用
                    //获取列的别名：getColumnLabel
//                    String columnName = rsmd.getColumnName(i + 1);
                    String columnLabel = rsmd.getColumnLabel(i + 1);

                    //通过反射，将对象的指定columnName的属性赋值为columnValue
                    Field field = book.getClass().getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(book, columnValue);
                }
                list.add(book);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResources(conn, ps, rs);
        }
        return null;
    }

    @Test
    public void testForQuery() {
        String sql = "SELECT book_id bookId,book_title bookTitle,author,publisher FROM t_book WHERE book_id = ?";
        Book book = bookForQuery(sql, 1);
        System.out.println(book);

        String sql1 = "SELECT book_id bookId,book_title bookTitle,author,publisher FROM t_book";
        List<Book> list = bookListForQuery(sql1);
        list.forEach(System.out::println);
    }

    public Book bookForQuery(String sql, Object... args) {
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
                Book book = new Book();
                for (int i = 0; i < columnCount; i++) {
                    //获取列值
                    Object columnValue = rs.getObject(i + 1);

                    //获取列名
                    //获取列的列名：getColumnName --不推荐使用
                    //获取列的别名：getColumnLabel
//                    String columnName = rsmd.getColumnName(i + 1);
                    String columnLabel = rsmd.getColumnLabel(i + 1);

                    //通过反射，将对象的指定columnName的属性赋值为columnValue
                    Field field = book.getClass().getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(book, columnValue);
                }
                return book;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResources(conn, ps, rs);
        }
        return null;
    }

    @Test
    public void testQuery() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT book_id,book_title,author,publisher FROM t_book WHERE book_id =?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, 1);

            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();


            if (rs.next()) {
                int id = (int) rs.getObject(1);
                String title = (String) rs.getObject(2);
                String author = (String) rs.getObject(3);
                String publisher = (String) rs.getObject(4);

                Book book = new Book(id, title, author, publisher);
                System.out.println(book);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResources(conn, ps, rs);
        }

    }

}
