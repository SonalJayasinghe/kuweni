package com.sonaljmsd.theclothingapp.models;

public class SubCategoryModel {

    private String name;
    private String imageUrl;
    private String categoryKey;
    private String subcategoryKey;

    public SubCategoryModel(String name, String imageUrl, String categoryKey, String subcategoryKey) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.categoryKey = categoryKey;
        this.subcategoryKey = subcategoryKey;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategoryKey() {
        return categoryKey;
    }

    public void setCategoryKey(String categoryKey) {
        this.categoryKey = categoryKey;
    }

    public String getSubcategoryKey() {
        return subcategoryKey;
    }

    public void setSubcategoryKey(String subcategoryKey) {
        this.subcategoryKey = subcategoryKey;
    }

}
