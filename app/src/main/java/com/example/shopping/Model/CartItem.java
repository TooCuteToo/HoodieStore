package com.example.shopping.Model;

import com.google.gson.annotations.SerializedName;

public class CartItem {
    private int id;
    private String typeId;
    private String name;
    private String image;
    private int quantity;
    private float money;

    public CartItem() {}

    public CartItem(int id, String typeId, String image, String name, int quantity, float money) {
        this.id = id;
        this.typeId = typeId;
        this.image = image;
        this.name = name;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return quantity * money;
    }
}
