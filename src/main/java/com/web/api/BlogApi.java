package com.web.api;

import com.web.entity.Blog;
import com.web.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/blog")
@CrossOrigin
public class BlogApi {

    @Autowired
    private BlogService blogService;

    @PostMapping("/admin/create-or-update")
    public ResponseEntity<?> save(@RequestBody Blog blog){
        Blog result = blogService.saveOrUpdate(blog);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/delete")
    public ResponseEntity<?> delete(@RequestParam("id") Long id){
        blogService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/public/findAll-page")
    public ResponseEntity<?> findAll(Pageable pageable){
        Page<Blog> result = blogService.findAll(pageable);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping("/public/findAll-list")
    public ResponseEntity<?> findAllList(Pageable pageable){
        List<Blog> result = blogService.findAll();
        return new ResponseEntity<>(result,HttpStatus.OK);
    }


    @GetMapping("/public/findById")
    public ResponseEntity<?> findById(@RequestParam("id") Long id){
        Blog result = blogService.findById(id);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping("/public/findPrimaryBlog")
    public ResponseEntity<?> findPrimaryCategory(){
        Blog result = blogService.blogPrimary();
        return new ResponseEntity<>(result,HttpStatus.OK);
    }


    @GetMapping("/public/top-8-blog")
    public ResponseEntity<?> top6Blog(){
        List<Blog> result = blogService.top8Blog();
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
