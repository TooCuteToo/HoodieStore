package com.example.shopping.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigDecimal;

public class Product implements Serializable {

    @SerializedName("Id")
    private int id;

    @SerializedName("typeId")
    private String typeId;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("material")
    private String material;

    @SerializedName("price")
    private BigDecimal price;

    @SerializedName("img")
    private String img;

    public Product(int id, String typeId, String name, String description, String material, BigDecimal price, String img) {
        this.id = id;
        this.typeId = typeId;
        this.name = name;
        this.description = description;
        this.material = material;
        this.price = price;
        this.img = img;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

//    public static ArrayList<Product> initDummyData() {
//        ArrayList<Product> dummyProducts = new ArrayList<>();
//
//        dummyProducts.add(new Product(R.drawable.product_1, "Hoodie Gucci", ""));
//        dummyProducts.add(new Product(R.drawable.product_3, "Hoodie Luccy", ""));
//        dummyProducts.add(new Product(R.drawable.product_2, "Hoodie Fuccy", ""));
//
//        dummyProducts.add(new Product(R.drawable.product_1, "Hoodie Gucci", ""));
//        dummyProducts.add(new Product(R.drawable.product_3, "Hoodie Luccy", ""));
//        dummyProducts.add(new Product(R.drawable.product_2, "Hoodie Fuccy", ""));
//
//        dummyProducts.add(new Product(R.drawable.product_1, "Hoodie Gucci", ""));
//        dummyProducts.add(new Product(R.drawable.product_3, "Hoodie Luccy", ""));
//        dummyProducts.add(new Product(R.drawable.product_2, "Hoodie Fuccy", ""));
//
//        return dummyProducts;
//    }
}
