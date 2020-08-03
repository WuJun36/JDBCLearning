package com.mylearn1.dao;

import com.mylearn.bean.Customer;
import com.mylearn.utils.JdbcUtils;
import junit.framework.TestCase;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

public class CustomerDaoImplTest extends TestCase {

    private CustomerDaoImpl dao = new CustomerDaoImpl();

    public void testInsert() {
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();
            Customer cust = new Customer(1, "Jeno", "jeno@gmail.com", new Date(190754352L));
            dao.insert(conn, cust);
            System.out.println("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResources(conn, null);
        }
    }

    public void testDeleteById() {
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();
            dao.deleteById(conn, 3);
            System.out.println("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResources(conn, null);
        }
    }

    public void testGetCustomerById() {
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();
            Customer cust = dao.getCustomerById(conn, 1);
            System.out.println(cust);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResources(conn, null);
        }
    }

    public void testGetCustomerAll() {
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();
            List<Customer> list = dao.getCustomerAll(conn);
            list.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResources(conn, null);
        }
    }

    public void testUpdate() {
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();
            Customer cust = new Customer(1, "JaeMin", "jaemin@gmail.com", new Date(190754352L));
            dao.update(conn, cust);
            System.out.println("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResources(conn, null);
        }
    }

    public void testGetCount() {
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();
            long count = dao.getCount(conn);
            System.out.println(count);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResources(conn, null);
        }
    }

    public void testGetMaxBirth() {
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();
            Date maxBirth = dao.getMaxBirth(conn);
            System.out.println(maxBirth);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResources(conn, null);
        }
    }
}