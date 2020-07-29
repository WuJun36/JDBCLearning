package com.mylearn.preparedstatement.crud;

import com.mylearn.bean.UserDto;
import com.mylearn.utils.JdbcUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.*;

/**
 * 查询操作
 */
public class PrepareStatementQueryTest {

    public UserDto queryForUser(String sql, Object... args) {
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
            //获取结果集的元数据（修饰现有数据的数据）
            ResultSetMetaData rsmd = rs.getMetaData();
            //通过ResultSetMetaData获取结果集中的列数
            int columnCount = rsmd.getColumnCount();

            if (rs.next()) {
                UserDto userDto = new UserDto();
                //处理结果集一行数据中的每一列
                for (int i = 0; i < columnCount; i++) {
                    //获取列值
                    Object columnValue = rs.getObject(i + 1);

                    //获取每个列的列名
                    String columnName = rsmd.getColumnName(i + 1);

                    //给userDto指定的columnName属性赋值为columnValue，通过反射
                    Field field = UserDto.class.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(userDto, columnValue);
                }
                return userDto;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResources(conn, ps, rs);
        }

        return null;
    }

    @Test
    public void testQuery1() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            conn = JdbcUtils.getConnection();
            String sql = "SELECT id,username,password,email FROM t_user WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setObject(1, 1);

            //执行，返回结果集
            resultSet = ps.executeQuery();

            //处理结果集
            if (resultSet.next()) { //判断结果集的下一条是否有数据,如果有数据返回true，并指针下移，如果返回false，指针不会下移
                //获取当前这条数据的各个字段值
                int id = resultSet.getInt(1);
                String username = resultSet.getString(2);
                String password = resultSet.getString(3);
                String email = resultSet.getString(4);

                //将数据封装成一个对象
                UserDto userDto = new UserDto(id, username, password, email);
                System.out.println(userDto.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            JdbcUtils.closeResources(conn, ps, resultSet);
        }
    }
}
