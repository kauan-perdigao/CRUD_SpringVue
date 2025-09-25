package com.myproject.spring_back.service;

import com.myproject.spring_back.model.Category;
import com.myproject.spring_back.model.Product;
import com.myproject.spring_back.repository.CategoryRepository;
import com.myproject.spring_back.repository.ProductRepository;
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
    
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }
    
    public Category save(Category category) {
        return categoryRepository.save(category);
    }
    
    @Transactional
    public void deleteById(Long id) {
        
        List<Product> productsToUpdate = productRepository.findByCategoryId(id);
        for (Product product : productsToUpdate) {
            product.setCategory(null);
        }
        productRepository.saveAll(productsToUpdate);

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
}