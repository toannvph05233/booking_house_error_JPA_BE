package com.web.repository;

import com.web.entity.Category;
import com.web.entity.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ServicesRepository extends JpaRepository<Services,Long> {

    @Query("select s from Services s where s.category.id = ?1")
    public List<Services> findByCategory(Long id);
}
