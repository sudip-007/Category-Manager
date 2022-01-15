package com.projects.categorymanager.controller;

import com.projects.categorymanager.entities.CategoryEntity;
import com.projects.categorymanager.entities.CategoryMapping;
import com.projects.categorymanager.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping(value = "/category")
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    @RequestMapping(value = "/create",
            method=RequestMethod.GET)
    public ResponseEntity<String> createCategory(@RequestParam("categoryName") String categoryName,
                                                 @RequestParam("parentCategoryName") String parentCategoryName) {
        try{
            System.out.println(categoryName);
            CategoryEntity parentCategory = null;
            CategoryEntity category = null;

            if(parentCategoryName != "") {
                parentCategory = categoryRepository.findCategoryByName(parentCategoryName);
                category = new CategoryEntity(categoryName, parentCategory);
            } else {
                category = new CategoryEntity(categoryName);
            }

            categoryRepository.save(category);
            return ResponseEntity.status(HttpStatus.CREATED).location(URI.create("http://localhost:8080/")).build();
        } catch (Exception e){
            return new ResponseEntity<>(e.toString(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping(value = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CategoryMapping>  getAllCategories(){
        List<CategoryMapping> mappedCategories = null;
        try {
            mappedCategories = createCategoryMapping();
            System.out.println(mappedCategories);
            return mappedCategories;
        } catch (Exception e) {
            while (mappedCategories == null) {
                mappedCategories = createCategoryMapping();
            }
            return mappedCategories;
        }
    }

    public List<CategoryMapping> createCategoryMapping() {
        List<CategoryMapping> mappedCategory = new ArrayList<>();
        List<CategoryEntity> topLevelCategory = categoryRepository.findTopLevelCategory();

        for(CategoryEntity category: topLevelCategory) {
            Set<CategoryEntity> children = category.getChildren();
            CategoryMapping cmap = new CategoryMapping();
            cmap.setId(category.getId());
            cmap.setCategoryName(category.getName());
            cmap.setChildCategories(children);

            for (CategoryEntity child : children) {
                cmap.getChildCategories().add(child);
                getChildrens(child, 1, cmap);
            }
            mappedCategory.add(cmap);
        }
        return mappedCategory;
    }

    private void getChildrens(CategoryEntity parent, int subLevel, CategoryMapping cmap) {
        Set<CategoryEntity> children = parent.getChildren();

        for (CategoryEntity child : children) {
            System.out.println(child.getName());
            cmap.getChildCategories().add(child);
            getChildrens(child, subLevel + 1, cmap);
        }
    }

}