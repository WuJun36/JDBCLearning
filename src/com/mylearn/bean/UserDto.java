package com.mylearn.bean;

/**
 * ORM编程思想，(Object relational mapping)'
 * 一个数据表对应一个JAVA类，
 * 表中的一条记录对应JAVA类的一个对象
 * 表中的一个字段对应JAVA类的一个属性
 */
public class UserDto {
    Integer id;
    String username;
    String password;
    String email;

    public UserDto(Integer id, String username, String password, String email) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public UserDto(){
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
