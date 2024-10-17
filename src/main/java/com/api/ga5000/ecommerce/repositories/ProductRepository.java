package com.api.ga5000.ecommerce.repositories;

import com.api.ga5000.ecommerce.dtos.SearchFilter;
import com.api.ga5000.ecommerce.entities.Category;
import com.api.ga5000.ecommerce.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(Category category);

    List<Product> findByProductNameIgnoreCase(String name);

    List<Product> findByProductPriceBetween(BigDecimal min, BigDecimal max);

    List<Product> findByInventoryGreaterThan(int i);

    @Query("SELECT p FROM Product p WHERE " +
            "(:#{#filter.category} IS NULL OR p.category = :#{#filter.category}) AND " +
            "(:#{#filter.name} IS NULL OR LOWER(p.productName) LIKE LOWER(CONCAT('%', :#{#filter.name}, '%'))) AND " +
            "(:#{#filter.minPrice} IS NULL OR p.productPrice >= :#{#filter.minPrice}) AND " +
            "(:#{#filter.maxPrice} IS NULL OR p.productPrice <= :#{#filter.maxPrice}) AND " +
            "p.inventory > 0")
    Page<Product> findByFilters(@Param("filter") SearchFilter filter, Pageable pageable);
}
