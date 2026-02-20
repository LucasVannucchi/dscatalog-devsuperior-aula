package com.devsuperior.dscatalogModulo2.products.repository;

import com.devsuperior.dscatalogModulo2.products.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
