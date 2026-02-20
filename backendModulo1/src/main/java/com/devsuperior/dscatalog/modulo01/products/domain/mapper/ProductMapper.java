package com.devsuperior.dscatalog.modulo01.products.domain.mapper;

import com.devsuperior.dscatalog.modulo01.products.domain.dto.CategoryResponseDTO;
import com.devsuperior.dscatalog.modulo01.products.domain.dto.ProductRequestDTO;
import com.devsuperior.dscatalog.modulo01.products.domain.dto.ProductResponseDTO;
import com.devsuperior.dscatalog.modulo01.products.domain.entity.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductMapper {

    private final CategoryMapper categoryMapper;

    public ProductMapper(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    public Product toEntity(ProductRequestDTO dto) {
        if (dto == null) return null;

        Product product = new Product();
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        product.setImgUrl(dto.imgUrl());
        product.setDate(dto.date());

        if (dto.categories() != null) {
            dto.categories().forEach(catDto ->
                    product.getCategories().add(categoryMapper.toEntity(catDto))
            );
        }

        return product;
    }

    public ProductResponseDTO toDTO(Product entity) {
        if (entity == null) return null;

        List<CategoryResponseDTO> categories = entity.getCategories().stream()
                .map(categoryMapper::toDTO)
                .toList();

        return new ProductResponseDTO(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getImgUrl(),
                entity.getDate(),
                categories
        );
    }
}