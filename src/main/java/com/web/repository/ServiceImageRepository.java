package com.web.repository;

import com.web.entity.Category;
import com.web.entity.ServiceImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceImageRepository extends JpaRepository<ServiceImage,Long> {
}
