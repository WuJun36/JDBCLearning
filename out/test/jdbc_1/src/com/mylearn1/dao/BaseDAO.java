package com.mylearn1.dao;

import com.mylearn.utils.JdbcUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDAO<T> {
    Class<T> clazz = null;

    {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        Type actualTypeArgument = pt.getActualTypeArguments()[0];
        clazz = (Class<T>) actualTypeArgument;
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


    /**
     * 通用的查询操作，用于返回表中的一条记录（考虑上事务）
     * 返回单个记录
     *
     * @param conn
     * @param sql
     * @param args
     * @return
     */
    public T getInstance(Connection conn, String sql, Object... args) {

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

    /**
     * 查询操作，返回多条记录
     *
     * @param sql
     * @param args
     * @return
     */
    public List<T> getInstances(Connection conn, String sql, Object... args) {
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
            JdbcUtils.closeResources(null, ps, rs);
        }
        return null;
    }

    /**
     * 获取某些特殊统计值
     *
     * @param conn
     */
    public <E> E getValue(Connection conn, String sql, Object... args) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            rs = ps.executeQuery();
            if (rs.next()) {
                E e = (E) rs.getObject(1);
                return e;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtils.closeResources(null, ps, rs);
        }
        return null;
    }
}
