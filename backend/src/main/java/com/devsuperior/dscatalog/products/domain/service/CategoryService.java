package com.devsuperior.dscatalog.products.domain.service;

import com.devsuperior.dscatalog.products.domain.entity.Category;
import com.devsuperior.dscatalog.products.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

}
