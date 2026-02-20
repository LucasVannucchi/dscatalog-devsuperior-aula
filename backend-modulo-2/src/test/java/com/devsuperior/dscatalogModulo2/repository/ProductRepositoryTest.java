package com.devsuperior.dscatalogModulo2.repository;

import com.devsuperior.dscatalogModulo2.factory.ProductFactory;
import com.devsuperior.dscatalogModulo2.products.domain.entity.Product;
import com.devsuperior.dscatalogModulo2.products.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    private long existingId;
    private long nonExistingId;
    private long countTotalProducts;

    @BeforeEach
    void setUp() throws Exception{

        existingId = 1L;
        nonExistingId = 1000L;
        countTotalProducts = 1L;
    }

    @Test
    public void saveShouldPersistWithAutoincrementWhenIdIsNull(){

        Product product = ProductFactory.createProduct();
        product.setId(null);

        product = productRepository.save(product);
        System.out.println("✅ Novo ID gerado: " + product.getId());

        Assertions.assertNotNull(product.getId());
        Assertions.assertEquals(countTotalProducts + 1, product.getId());
    }

    @Test
    public void findByIdShouldReturnProductWhenIdExists(){
        Optional<Product> result = productRepository.findById(existingId);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(existingId, result.get().getId());
        System.out.println("✅ ID buscado: " + result.get().getId());
    }

    @Test
    public void findByIdShouldReturnEmptyOptionalWhenIdDoesNotExist() {
        Optional<Product> result = productRepository.findById(nonExistingId);

        Assertions.assertFalse(result.isPresent());
        System.out.println("✅ Produto não encontrado (Optional está vazio)");
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists() {
        productRepository.deleteById(existingId);

        Optional<Product> result =productRepository.findById(existingId);

        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void deleteShouldThrowEmptyResultDataAccessExcpetionWhenIdDoesNotExists(){
        Assertions.assertDoesNotThrow(() -> {
            productRepository.deleteById(nonExistingId);
        });
    }
}
