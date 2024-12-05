package com.web.repository;

import com.web.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
public interface BlogRepository extends JpaRepository<Blog,Long> {

    @Modifying
    @Transactional
    @Query("update Blog b set b.primaryBlog = false")
    int unSetPrimary();

    @Query("select b from Blog b where b.primaryBlog = true")
    public Optional<Blog> blogPrimary();

    @Query(value = "select * from blog order by id desc", nativeQuery = true)
    public List<Blog> allBlog();

    @Query(value = "select * from blog order by id desc limit 8", nativeQuery = true)
    public List<Blog> top8Blog();
}
