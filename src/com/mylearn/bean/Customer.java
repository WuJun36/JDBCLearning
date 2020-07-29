package com.mylearn.bean;

import java.sql.Blob;
import java.sql.Date;
import java.util.Arrays;

public class Customer {
    int custId;
    String custName;
    String email;
    Date birth;
    Blob photo;

    public Customer() {
    }

    public Customer(int custId, String custName, String email, Date birth) {
        this.custId = custId;
        this.custName = custName;
        this.email = email;
        this.birth = birth;
    }

    public int getCustId() {
        return custId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public Blob getPhoto() {
        return photo;
    }

    public void setPhoto(Blob photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "custId=" + custId +
                ", custName='" + custName + '\'' +
                ", email='" + email + '\'' +
                ", birth=" + birth +
                '}';
    }
}
