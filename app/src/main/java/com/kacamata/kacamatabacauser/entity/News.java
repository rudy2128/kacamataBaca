package com.kacamata.kacamatabacauser.entity;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class News {
    private String proId;
    private String title;
    private String description;
    private String imageUrl;

    public News(String proId, String title, String description, String imageUrl) {
        this.proId = proId;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public News() {
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }


    public String getImageUrl() {
        return imageUrl;
    }

}
