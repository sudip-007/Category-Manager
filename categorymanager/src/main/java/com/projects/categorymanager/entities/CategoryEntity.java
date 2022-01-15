package com.projects.categorymanager.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "CATEGORY")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private int id;

    @Column(name = "category_name")
    private String categoryName;

    @OneToOne
    @JoinColumn(name = "parent_id")
    private CategoryEntity parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<CategoryEntity> children = new HashSet<>();

    public CategoryEntity(String name, CategoryEntity parent) {
        this.categoryName = name;
        this.parent = parent;
    }

    public CategoryEntity(String name) {
        this.categoryName = name;
    }

    public CategoryEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return categoryName;
    }

    public void setName(String name) {
        this.categoryName = name;
    }

    public CategoryEntity getParent() {
        return parent;
    }

    public void setParent(CategoryEntity parent) {
        this.parent = parent;
    }

    public Set<CategoryEntity> getChildren() {
        return children;
    }

    public void setChildren(Set<CategoryEntity> children) {
        this.children = children;
    }

    public void addChild(CategoryEntity children) {
        this.children.add(children);
    }

    @Override
    public String toString() {
        return "CategoryEntity{" +
                "id=" + id +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
