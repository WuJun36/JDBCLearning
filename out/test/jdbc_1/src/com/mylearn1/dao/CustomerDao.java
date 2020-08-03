package com.mylearn1.dao;

import com.mylearn.bean.Customer;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

public interface CustomerDao {
    /**
     * 插入一条记录
     *
     * @param conn
     * @param cust
     */
    void insert(Connection conn, Customer cust);

    /**
     * 根据ID删除
     *
     * @param conn
     * @param id
     */
    void deleteById(Connection conn, int id);

    /**
     * 根据ID获取Customer实例
     *
     * @param conn
     * @param id
     * @return
     */
    Customer getCustomerById(Connection conn, int id);

    /**
     * 获取全部记录
     *
     * @param conn
     * @return
     */
    List<Customer> getCustomerAll(Connection conn);

    /**
     * 更新表中记录
     *
     * @param conn
     * @param cust
     */
    void update(Connection conn, Customer cust);

    /**
     * 获取表中的总记录数
     * @param conn
     * @return
     */
    long getCount(Connection conn);

    /**
     * 获取最大生日
     * @param conn
     * @return
     */
    Date getMaxBirth(Connection conn);
}
