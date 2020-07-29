package com.mylearn.preparedstatement.crud;

import com.mylearn.bean.Customer;
import com.mylearn.utils.JdbcUtils;
import org.junit.Test;

import java.io.*;
import java.sql.*;

public class BlobTest {

    @Test
    public void testQuery() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            conn = JdbcUtils.getConnection();

            String sql = "SELECT cust_id custId,cust_name custName,email,birth,photo FROM customer";
            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();

            is = null;
            fos = null;
            if (rs.next()) {
                int id = rs.getInt("custId");
                String custName = rs.getString("custName");
                String email = rs.getString("email");
                Date birth = rs.getDate("birth");

                Customer customer = new Customer(id, custName, email, birth);
                System.out.println(customer);

                //将Blob类型的字段下载下来，以文件的形式保存在本地
                Blob photo = rs.getBlob("photo");
                is = photo.getBinaryStream();
                fos = new FileOutputStream("jaemin2.jpg");
                byte[] buffer = new byte[1024];
                int len;
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            JdbcUtils.closeResources(conn, ps, rs);
        }
    }

    @Test
    public void testUpdate() {
        Connection conn = null;
        PreparedStatement ps = null;
        FileInputStream is = null;
        try {
            conn = JdbcUtils.getConnection();

            String sql = "UPDATE customer SET photo = ? WHERE cust_id = ?";
            ps = conn.prepareStatement(sql);

            is = new FileInputStream(new File("jeamin.jpg"));
            ps.setBlob(1, is);
            ps.setObject(2, 1);

            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            JdbcUtils.closeResources(conn, ps);
        }


    }


}
