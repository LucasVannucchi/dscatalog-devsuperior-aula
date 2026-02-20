package com.devsuperior.dscatalog.modulo01.products.repository;

import com.devsuperior.dscatalog.modulo01.products.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
