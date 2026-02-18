package com.devsuperior.dscatalog.products.domain.service;

import com.devsuperior.dscatalog.products.domain.dto.CategoryRequestDTO;
import com.devsuperior.dscatalog.products.domain.dto.CategoryResponseDTO;
import com.devsuperior.dscatalog.products.domain.entity.Category;
import com.devsuperior.dscatalog.products.domain.exceptions.DatabaseException;
import com.devsuperior.dscatalog.products.domain.exceptions.ResourceNotFoundException;
import com.devsuperior.dscatalog.products.domain.mapper.CategoryMapper;
import com.devsuperior.dscatalog.products.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Page<CategoryResponseDTO> findAllPaged(PageRequest pageRequest) {
        Page<Category> list = categoryRepository.findAll(pageRequest);

        return list
                .map(CategoryResponseDTO::new);
    }

    @Transactional(readOnly = true)
    public CategoryResponseDTO findById(Long id) {
        Optional<Category> cat = categoryRepository.findById(id);
        Category entity = cat.orElseThrow(() ->
                new ResourceNotFoundException("Category not found with Id: " + id));
        return new CategoryResponseDTO(entity);
    }

    @Transactional
    public CategoryResponseDTO insert(CategoryRequestDTO dto) {
        Category category = categoryMapper.toEntity(dto);
        category = categoryRepository.save(category);
        return categoryMapper.toDTO(category);
    }

    @Transactional
    public CategoryResponseDTO update(Long id, CategoryRequestDTO dto) {
        try {
            Category category = categoryRepository.getReferenceById(id);
            category.setName(dto.name());
            category = categoryRepository.save(category);
            return categoryMapper.toDTO(category);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id: " + id + " not found");
        }
    }

    @Transactional
    public void delete(Long id) {

        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Id: " + id + " not found");
        }

        try {
            categoryRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }
}
