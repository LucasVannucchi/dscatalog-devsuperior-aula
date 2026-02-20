package com.devsuperior.dscatalogModulo2.factory;

import com.devsuperior.dscatalogModulo2.products.domain.dto.CategoryRequestDTO;
import com.devsuperior.dscatalogModulo2.products.domain.dto.ProductRequestDTO;
import com.devsuperior.dscatalogModulo2.products.domain.dto.ProductResponseDTO;
import com.devsuperior.dscatalogModulo2.products.domain.entity.Category;
import com.devsuperior.dscatalogModulo2.products.domain.entity.Product;
import com.devsuperior.dscatalogModulo2.products.domain.mapper.ProductMapper;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

public class ProductFactory {

    public static Product createProduct() {
        Product product = new  Product(
                1L,
                "Smart TV Samsung 55 UHD 4K",
                "Televisão inteligente com suporte a Wi-Fi, HDR e assistente de voz integrado.",
                new BigDecimal("3499.92"),
                "https://example.com/images/tv-samsung-55.jpg",
                Instant.parse("2024-02-03T15:00:00Z")
        );

        product.getCategories().add(new Category(2L, "Eletronics"));

        return product;
    }

    public static ProductRequestDTO createProductRequestDTO() {
        Product product = createProduct();

        return new ProductRequestDTO(
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getImgUrl(),
                product.getDate(),
                product.getCategories()
                        .stream()
                        .map(category -> new CategoryRequestDTO(category.getId(), category.getName())).toList()
        );
    }

    public static ProductResponseDTO createProductResponseDTO(ProductMapper mapper) {
        Product product = createProduct();
        return mapper.toDTO(product);
    }
}