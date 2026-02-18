package com.devsuperior.dscatalog.products.repository;

import com.devsuperior.dscatalog.products.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
