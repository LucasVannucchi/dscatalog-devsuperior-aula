package com.devsuperior.dscatalog.modulo01.products.domain.mapper;

import com.devsuperior.dscatalog.modulo01.products.domain.dto.CategoryRequestDTO;
import com.devsuperior.dscatalog.modulo01.products.domain.dto.CategoryResponseDTO;
import com.devsuperior.dscatalog.modulo01.products.domain.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryRequestDTO dto){
        if (dto == null){
            return null;
        }

        Category category = new Category();
        category.setName(dto.name());
        return category;
    }

    public CategoryResponseDTO toDTO(Category category){
        if (category == null){
            return null;
        }

        return new CategoryResponseDTO(
                category.getId(),
                category.getName(),
                category.getCreatedAt(),
                category.getUpdatedAt()
        );
    }
}
