package com.example.shopping.Model;

public class Category {
    private String typeId;
    private String typeName;
    private String image;

    public Category(String typeId, String typeName, String image) {
        this.typeId = typeId;
        this.typeName = typeName;
        this.image = image;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
