package com.projects.categorymanager.repositories;

import com.projects.categorymanager.entities.CategoryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<CategoryEntity, Integer> {
    public static final String FIND_PARENT_CATEGORY_BY_CATEGORY_NAME =
            "SELECT * FROM category where category_name = :name";
    @Query(value = FIND_PARENT_CATEGORY_BY_CATEGORY_NAME,
            nativeQuery = true)
    public CategoryEntity findCategoryByName(@Param("name") String categoryName);

    @Query(value = "SELECT * FROM category where parent_id is null",
            nativeQuery = true)
    public List<CategoryEntity> findTopLevelCategory();
}
