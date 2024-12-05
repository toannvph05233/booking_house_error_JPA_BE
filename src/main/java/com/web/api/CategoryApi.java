package com.web.api;

import com.web.entity.Category;
import com.web.enums.CategoryType;
import com.web.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/category")
@CrossOrigin
public class CategoryApi {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/admin/create")
    public ResponseEntity<?> save(@RequestBody Category category){
        Category result = categoryService.save(category);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/delete")
    public ResponseEntity<?> delete(@RequestParam("id") Long id){
        categoryService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/public/findAll")
    public ResponseEntity<?> findAll(@RequestParam(value = "type", required = false) String type){
        List<Category> categories = categoryService.findAll(type);
        return new ResponseEntity<>(categories,HttpStatus.OK);
    }

    @GetMapping("/admin/findById")
    public ResponseEntity<?> findById(@RequestParam("id") Long id){
        Category result = categoryService.findById(id);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping("/public/get-all-type-category")
    public ResponseEntity<?> getAllStatus(){
        List<String> result = Stream.of(CategoryType.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
