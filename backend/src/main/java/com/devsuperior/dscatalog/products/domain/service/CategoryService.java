package com.devsuperior.dscatalog.products.domain.service;

import com.devsuperior.dscatalog.products.domain.dto.CategoryResponseDTO;
import com.devsuperior.dscatalog.products.domain.entity.Category;
import com.devsuperior.dscatalog.products.domain.exceptions.EntityNotFoundException;
import com.devsuperior.dscatalog.products.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> findAll() {
        List<Category> list = categoryRepository.findAll();

        return list
                .stream()
                .map(CategoryResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public CategoryResponseDTO findById(Long id) {
        Optional<Category> cat = categoryRepository.findById(id);
        Category entity = cat.orElseThrow(() ->
                new EntityNotFoundException("Category not found with ID " + id));
        return new CategoryResponseDTO(entity);
    }

}
