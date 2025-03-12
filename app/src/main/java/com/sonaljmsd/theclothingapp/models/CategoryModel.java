package com.sonaljmsd.theclothingapp.models;

public class CategoryModel {
    private String name;
    private String imageUrl;
    private String categoryKey;

    public CategoryModel(String name, String imageUrl, String key) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.categoryKey = key;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCategoryKey() {
        return categoryKey;
    }

}

