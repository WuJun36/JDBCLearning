package com.mylearn1.dao;

import com.mylearn.bean.Customer;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

public class CustomerDaoImpl extends BaseDAO<Customer> implements CustomerDao {
    @Override
    public void insert(Connection conn, Customer cust) {
        String sql = "INSERT INTO customer(cust_name,email,birth) VALUES(?,?,?)";
        update(conn, sql, cust.getCustName(), cust.getEmail(), cust.getBirth());
    }

    @Override
    public void deleteById(Connection conn, int id) {
        String sql = "DELETE FROM customer WHERE cust_id = ?";
        update(conn, sql, id);
    }

    @Override
    public Customer getCustomerById(Connection conn, int id) {
        String sql = "SELECT cust_id custId, cust_name custName,email,birth FROM customer WHERE cust_id = ?";
        return getInstance(conn, sql, id);
    }

    @Override
    public List<Customer> getCustomerAll(Connection conn) {
        String sql = "SELECT cust_id custId, cust_name custName,email,birth FROM customer";
        return getInstances(conn, sql);
    }

    @Override
    public void update(Connection conn, Customer cust) {
        String sql = "UPDATE customer SET cust_name = ?,email = ?,birth = ? WHERE cust_id = ?";
        update(conn, sql, cust.getCustName(), cust.getEmail(), cust.getBirth(), cust.getCustId());
    }

    @Override
    public long getCount(Connection conn) {
        String sql = "SELECT COUNT(*) FROM customer";
        return getValue(conn, sql);
    }

    @Override
    public Date getMaxBirth(Connection conn) {
        String sql = "SELECT MAX(birth) FROM customer";
        return getValue(conn, sql);
    }

}
