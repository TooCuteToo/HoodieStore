package com.example.shopping.Model;

import com.google.gson.annotations.SerializedName;

public class OrderDetail {
    @SerializedName("name")
    private String name;

    @SerializedName("img")
    private String image;

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

    public OrderDetail(String name, String image, int id, String typeId, int quantity, float money) {
        this.name = name;
        this.image = image;
        this.id = id;
        this.typeId = typeId;
        this.quantity = quantity;
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public float calculateTotalMoney() {
        return this.money * this.quantity;
    }
}
