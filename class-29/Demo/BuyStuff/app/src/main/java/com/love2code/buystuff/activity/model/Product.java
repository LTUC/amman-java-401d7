package com.love2code.buystuff.activity.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

//TODO: step: 2-1: make dara class
@Entity
public class Product {

    @PrimaryKey(autoGenerate = true)
    public Long id;
    String name;
    String description;

    java.util.Date dateCreated;

    ProductCategoryEnum productCategory;

    public Product(String name, String description, Date dateCreated, ProductCategoryEnum productCategory) {
        this.name = name;
        this.description = description;
        this.dateCreated = dateCreated;
        this.productCategory = productCategory;
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

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public ProductCategoryEnum getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategoryEnum productCategory) {
        this.productCategory = productCategory;
    }
}
