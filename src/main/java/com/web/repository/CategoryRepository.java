package com.web.repository;

import com.web.entity.Booking;
import com.web.entity.Category;
import com.web.enums.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {


    @Query("select c from Category c where c.categoryType = ?1")
    public List<Category> findByType(CategoryType categoryType);
}
