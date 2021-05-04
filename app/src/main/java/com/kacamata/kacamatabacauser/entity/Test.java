package com.kacamata.kacamatabacauser.entity;

import androidx.room.Ignore;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Test {
    private String testId;
    private String imageUrl;
    private String description;

    public Test(String testId, String imageUrl, String description) {
        this.testId = testId;
        this.imageUrl = imageUrl;
        this.description = description;
    }
    @Ignore
    public Test() {
    }

    public String getTestId() {
        return testId;
    }


    public String getImageUrl() {
        return imageUrl;
    }


    public String getDescription() {
        return description;
    }

 }
