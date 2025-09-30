package com.myproject.spring_back.repository;

import com.myproject.spring_back.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryId(Long categoryId);
    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);
    List<Product> findByNameContainingIgnoreCase(String name);
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
    boolean existsByNameAndIdNot(String name, Long id);
    boolean existsByName(String name);
    boolean existsByNameAndCategoryId(String name, Long categoryId);
    boolean existsByNameAndCategoryIdAndIdNot(String name, Long categoryId, Long id);
    @Query("select p from Product p left join fetch p.category")
    List<Product> findAllWithCategory();
    @Query("select p from Product p left join fetch p.category")
    Page<Product> findAllWithCategory(Pageable pageable);
}