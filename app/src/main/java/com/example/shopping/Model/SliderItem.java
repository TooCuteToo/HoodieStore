package com.example.shopping.Model;

public class SliderItem {
    private String imageUrl;
    private String TextDesc;

    public SliderItem(String imageUrl, String textDesc) {
        this.imageUrl = imageUrl;
        TextDesc = textDesc;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTextDesc() {
        return TextDesc;
    }

    public void setTextDesc(String textDesc) {
        TextDesc = textDesc;
    }
}
