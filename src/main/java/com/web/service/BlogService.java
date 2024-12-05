package com.web.service;

import com.web.entity.Blog;
import com.web.exception.MessageException;
import com.web.repository.BlogRepository;
import com.web.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private UserUtils userUtils;

    public Blog saveOrUpdate(Blog blog) {
        if(blog.getPrimaryBlog() == true){
            blogRepository.unSetPrimary();
        }
        blog.setUser(userUtils.getUserWithAuthority());
        blog.setCreatedDate(new Date(System.currentTimeMillis()));
        if(blog.getId() == null){
            blog.setNumView(0);
        }
        else{
            Blog bl = blogRepository.findById(blog.getId()).get();
            if(bl.getNumView() == null){
                blog.setNumView(0);
            }else {
                blog.setNumView(bl.getNumView());
            }
        }
        Blog result = blogRepository.save(blog);
        return result;
    }

    public void delete(Long id) {
        Optional<Blog> blog = blogRepository.findById(id);
        if (blog.isEmpty()){
            throw new MessageException("Blog not found");
        }
        if(blog.get().getPrimaryBlog()){
            throw new MessageException("Blog chính, không thể xóa");
        }
        blogRepository.delete(blog.get());
    }

    public Blog findById(Long id) {
        Optional<Blog> blog = blogRepository.findById(id);
        if (blog.isEmpty()){
            throw new MessageException("Blog not found");
        }
        if( blog.get().getNumView() != null){
            blog.get().setNumView(blog.get().getNumView() + 1);
        }
        else{
            blog.get().setNumView(1);
        }
        Blog bl = blogRepository.save(blog.get());
        return bl;
    }

    public Blog blogPrimary() {
        Optional<Blog> blog = blogRepository.blogPrimary();
        return blog.get();
    }

    public Page<Blog> findAll(Pageable pageable) {
        Page<Blog> page = blogRepository.findAll(pageable);
        return page;
    }

    public List<Blog> findAll() {
        return blogRepository.allBlog();
    }

    public List<Blog> top8Blog() {
        return blogRepository.top8Blog();
    }

}
