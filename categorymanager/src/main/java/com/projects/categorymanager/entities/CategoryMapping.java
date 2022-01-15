package com.projects.categorymanager.entities;

import java.util.HashSet;
import java.util.Set;

public class CategoryMapping {
    private int id;
    private Set<CategoryEntity> childCategories = new HashSet<>();
    private String categoryName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Set<CategoryEntity> getChildCategories() {
        return childCategories;
    }

    public void setChildCategories(Set<CategoryEntity> childCategories) {
        this.childCategories = childCategories;
    }

    @Override
    public String toString() {
        return "CategoryMapping{" +
                "id=" + id +
                ", categoryName='" + categoryName + '\'' +
                ", childCategories=" + childCategories +
                '}';
    }
}
