package com.web.service;

import com.web.entity.Category;
import com.web.enums.CategoryType;
import com.web.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category save(Category category){
        Category result = categoryRepository.save(category);
        return result;
    }

    public List<Category> findAll (String type){
        if(type != null){
            CategoryType categoryType = null;
            for (CategoryType o : CategoryType.values()) {
                if(o.name().equals(type)){
                    categoryType = o;
                }
            }
            List<Category> result = categoryRepository.findByType(categoryType);
            return result;
        }
        List<Category> result = categoryRepository.findAll();
        return result;
    }

    public Category findById(Long id){
        return categoryRepository.findById(id).get();
    }

    public void delete(Long id){
        categoryRepository.deleteById(id);
    }


}
