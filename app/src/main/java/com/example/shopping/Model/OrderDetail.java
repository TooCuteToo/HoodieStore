package com.example.shopping.Model;

import com.google.gson.annotations.SerializedName;

public class OrderDetail {
    @SerializedName("Id")
    private int id;

    @SerializedName("typeId")
    private String typeId;

    @SerializedName("quantity")
    private int quantity;

    @SerializedName("money")
    private float money;

    public OrderDetail(int id, String typeId, int quantity, float money) {
        this.id = id;
        this.typeId = typeId;
        this.quantity = quantity;
        this.money = money;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }
}
