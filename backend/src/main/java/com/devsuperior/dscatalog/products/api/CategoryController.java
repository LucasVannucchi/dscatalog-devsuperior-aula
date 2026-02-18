package com.devsuperior.dscatalog.products.api;

import com.devsuperior.dscatalog.products.domain.entity.Category;
import com.devsuperior.dscatalog.products.domain.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<Category>> findAll(){
        List<Category> list = categoryService.findAll();
        return ResponseEntity.ok().body(list);
    }
}
