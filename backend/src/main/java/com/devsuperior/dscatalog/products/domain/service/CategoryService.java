package com.devsuperior.dscatalog.products.domain.service;

import com.devsuperior.dscatalog.products.domain.dto.CategoryRequestDTO;
import com.devsuperior.dscatalog.products.domain.dto.CategoryResponseDTO;
import com.devsuperior.dscatalog.products.domain.entity.Category;
import com.devsuperior.dscatalog.products.domain.exceptions.EntityNotFoundException;
import com.devsuperior.dscatalog.products.domain.mapper.CategoryMapper;
import com.devsuperior.dscatalog.products.repository.CategoryRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
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

    @Transactional
    public CategoryResponseDTO insert(@Valid CategoryRequestDTO dto) {
        Category category = categoryMapper.toEntity(dto);
        category = categoryRepository.save(category);
        return categoryMapper.toDTO(category);
    }
}
