package com.kacamata.kacamatabacauser.entity;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Product {
    private String proId;
    private String imageUrl;
    private String title;
    private String price;
    private String description;

    public Product(String proId, String imageUrl, String title, String price, String description) {
        this.proId = proId;
        this.imageUrl = imageUrl;
        this.title = title;
        this.price = price;
        this.description = description;
    }

    public Product() {
    }

    public String getProId() {
        return proId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}
