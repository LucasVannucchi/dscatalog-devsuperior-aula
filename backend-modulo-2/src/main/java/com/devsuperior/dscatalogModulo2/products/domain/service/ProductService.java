package com.devsuperior.dscatalogModulo2.products.domain.service;


import com.devsuperior.dscatalogModulo2.products.domain.dto.CategoryRequestDTO;
import com.devsuperior.dscatalogModulo2.products.domain.dto.ProductRequestDTO;
import com.devsuperior.dscatalogModulo2.products.domain.dto.ProductResponseDTO;
import com.devsuperior.dscatalogModulo2.products.domain.entity.Category;
import com.devsuperior.dscatalogModulo2.products.domain.entity.Product;
import com.devsuperior.dscatalogModulo2.products.domain.exceptions.DatabaseException;
import com.devsuperior.dscatalogModulo2.products.domain.exceptions.ResourceNotFoundException;
import com.devsuperior.dscatalogModulo2.products.domain.mapper.ProductMapper;
import com.devsuperior.dscatalogModulo2.products.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryService categoryService;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.categoryService = categoryService;
    }

    @Transactional(readOnly = true)
    public Page<ProductResponseDTO> findAllPaged(PageRequest pageRequest) {
        Page<Product> list = productRepository.findAll(pageRequest);
        return list.map(productMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public ProductResponseDTO findById(Long id) {
        Product entity = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with Id: " + id));
        return productMapper.toDTO(entity);
    }

    @Transactional
    public ProductResponseDTO insert(ProductRequestDTO dto) {
        Product product = new Product();
        copyDtoToEntity(dto, product);
        product = productRepository.save(product);
        return productMapper.toDTO(product);
    }

    @Transactional
    public ProductResponseDTO update(Long id, ProductRequestDTO dto) {
        try {
            Product product = productRepository.getReferenceById(id);
            product.setName(dto.name());
            product.setDescription(dto.description());
            product.setPrice(dto.price());
            product.setImgUrl(dto.imgUrl());
            product.setDate(dto.date());
            product = productRepository.save(product);
            return productMapper.toDTO(product);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id " + id + " not found");
        }
    }

    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Id: " + id + " not found");
        }

        try {
            productRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

    private void copyDtoToEntity(ProductRequestDTO dto, Product entity) {

        entity.setName(dto.name());
        entity.setDescription(dto.description());
        entity.setPrice(dto.price());
        entity.setImgUrl(dto.imgUrl());
        entity.setDate(dto.date());

        entity.getCategories().clear();
        for (CategoryRequestDTO catDto : dto.categories()) {
            Category category = categoryService.getReference(catDto.id());
            entity.getCategories().add(category);
        }
    }
}