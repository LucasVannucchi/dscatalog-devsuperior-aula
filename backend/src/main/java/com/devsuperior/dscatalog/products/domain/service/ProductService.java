package com.devsuperior.dscatalog.products.domain.service;

import com.devsuperior.dscatalog.products.domain.dto.ProductRequestDTO;
import com.devsuperior.dscatalog.products.domain.dto.ProductResponseDTO;
import com.devsuperior.dscatalog.products.domain.entity.Product;
import com.devsuperior.dscatalog.products.domain.exceptions.DatabaseException;
import com.devsuperior.dscatalog.products.domain.exceptions.ResourceNotFoundException;
import com.devsuperior.dscatalog.products.domain.mapper.ProductMapper;
import com.devsuperior.dscatalog.products.repository.ProductRepository;
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

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
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
        Product product = productMapper.toEntity(dto);
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
}