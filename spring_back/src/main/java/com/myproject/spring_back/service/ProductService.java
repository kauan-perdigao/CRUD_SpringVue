package com.myproject.spring_back.service;

import com.myproject.spring_back.model.Product;
import com.myproject.spring_back.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    
    public List<Product> findAll() {
        return productRepository.findAll();
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

    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

     public boolean existsByNameAndCategoryId(String name, Long categoryId) {
        return productRepository.existsByNameAndCategoryId(name, categoryId);
    }

    public boolean existsByNameAndCategoryIdAndIdNot(String name, Long categoryId, Long id) {
        return productRepository.existsByNameAndCategoryIdAndIdNot(name, categoryId, id);
    }
    
}