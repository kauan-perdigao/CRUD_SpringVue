package com.myproject.spring_back.service;

import com.myproject.spring_back.model.Product;
import com.myproject.spring_back.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAllWithCategory() {
        return productRepository.findAllWithCategory();
    }
    
    public Page<Product> findAllWithCategory(Pageable pageable) {
        return productRepository.findAllWithCategory(pageable);
    }
    
    public List<Product> findAll() {
        return productRepository.findAll();
    }
    
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
    
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public boolean existsById(Long id) {
        return productRepository.existsById(id);
    }
    
    public Product save(Product product) {
        return productRepository.save(product);
    }
    
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public boolean existsByNameAndIdNot(String name, Long id) {
        return productRepository.existsByNameAndIdNot(name, id);
    }

    public List<Product> findByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }
    
    public Page<Product> findByCategoryId(Long categoryId, Pageable pageable) {
        return productRepository.findByCategoryId(categoryId, pageable);
    }

    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

     public boolean existsByNameAndCategoryId(String name, Long categoryId) {
        return productRepository.existsByNameAndCategoryId(name, categoryId);
    }

    public boolean existsByNameAndCategoryIdAndIdNot(String name, Long categoryId, Long id) {
        return productRepository.existsByNameAndCategoryIdAndIdNot(name, categoryId, id);
    }

    public List<Product> searchByName(String q) {
        return productRepository.findByNameContainingIgnoreCase(q);
    }
    
    public Page<Product> searchByName(String q, Pageable pageable) {
        return productRepository.findByNameContainingIgnoreCase(q, pageable);
    }
    
}