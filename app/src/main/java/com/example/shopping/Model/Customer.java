package com.example.shopping.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Customer implements Serializable {

    @SerializedName("customerId")
    private int customerId;

    @SerializedName("fullName")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("userName")
    private String userName;

    @SerializedName("pass")
    private String pass;

    public Customer(int customerId, String name, String email, String userName, String pass) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.userName = userName;
        this.pass = pass;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String userName) {
        this.userName = userName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
