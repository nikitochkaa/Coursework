package com.company.coursework.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue
    private int id;
    @Column
    private Boolean isAdmin;
    @Column(unique = true)
    private String username;
    @Column
    private String password;

    public User() {
    }

    public User(String username, String password, Boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public Boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {

        isAdmin = admin;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
