package com.example.shopping.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {
    @SerializedName("orderId")
    private int orderId;

    @SerializedName("customerId")
    private int customerId;

    @SerializedName("totalMoney")
    private float totalMoney;

    @SerializedName("orderDetails")
    private ArrayList<OrderDetail> orderDetails;

    public Order(int customerId, float totalMoney) {
        this.customerId = customerId;
        this.totalMoney = totalMoney;
        this.orderId = 0;
    }

    public Order(int customerId, float totalMoney, ArrayList<OrderDetail> orderDetails) {
        this.customerId = customerId;
        this.totalMoney = totalMoney;
        this.orderDetails = orderDetails;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public float getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(float totalMoney) {
        this.totalMoney = totalMoney;
    }
}
