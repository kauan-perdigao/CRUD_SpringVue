package com.myproject.spring_back.service;

import com.myproject.spring_back.model.Category;
import com.myproject.spring_back.model.Product;
import com.myproject.spring_back.repository.CategoryRepository;
import com.myproject.spring_back.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }
    
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
    
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }
    
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }
    
    public Category save(Category category) {
        return categoryRepository.save(category);
    }
    
    @Transactional
    public void deleteById(Long id) {
        List<Product> productsToUpdate = productRepository.findByCategoryId(id);
        
        // Usando stream API otimizada do Java 21
        var updatedProducts = productsToUpdate.stream()
            .peek(product -> product.setCategory(null))
            .toList();
            
        productRepository.saveAll(updatedProducts);
        categoryRepository.deleteById(id);
    }
    
    public boolean existsByName(String name) {
        return categoryRepository.existsByName(name);
    }

    public boolean existsById(Long id) {
        return categoryRepository.existsById(id);
    }

    public boolean existsByNameAndIdNot(String name, Long id) {
        return categoryRepository.existsByNameAndIdNot(name, id);
    }
    
    public Optional<Category> findByName(String name) {
        return categoryRepository.findByName(name);
    }

    public List<Category> searchByName(String q) {
        return categoryRepository.findByNameContainingIgnoreCase(q);
    }
    
    public Page<Category> searchByName(String q, Pageable pageable) {
        return categoryRepository.findByNameContainingIgnoreCase(q, pageable);
    }

}