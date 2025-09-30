package com.myproject.spring_back.controller;

import com.myproject.spring_back.model.Product;
import com.myproject.spring_back.model.Category;
import com.myproject.spring_back.service.ProductService;
import com.myproject.spring_back.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/produtos")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<?> listar(
            @RequestParam(value = "q", required = false) String q,
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "paginated", required = false, defaultValue = "false") boolean paginated,
            @PageableDefault(size = 7, sort = "name") Pageable pageable) {
        
        var filterType = determineFilterType(q, categoryId);
        
        if (paginated) {
            return switch (filterType) {
                case "category" -> ResponseEntity.ok(productService.findByCategoryId(categoryId, pageable));
                case "search" -> ResponseEntity.ok(productService.searchByName(q, pageable));
                case "all" -> ResponseEntity.ok(productService.findAllWithCategory(pageable));
                default -> ResponseEntity.badRequest().build();
            };
        } else {
            return switch (filterType) {
                case "category" -> ResponseEntity.ok(productService.findByCategoryId(categoryId));
                case "search" -> ResponseEntity.ok(productService.searchByName(q));
                case "all" -> ResponseEntity.ok(productService.findAllWithCategory());
                default -> ResponseEntity.badRequest().build();
            };
        }
    }
    
    private String determineFilterType(String q, Long categoryId) {
        if (categoryId != null) return "category";
        if (q != null && !q.isBlank()) return "search";
        return "all";
    }

    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody Product produto) {
        
        var validationResult = validateProduct(produto);
        if (validationResult != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationResult);
        }

        var categoria = categoryService.findById(produto.getCategory().getId())
            .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
        produto.setCategory(categoria);

        var produtoSalvo = productService.save(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @Valid @RequestBody Product produtoDetalhes) {
        
        if (productService.existsByNameAndCategoryIdAndIdNot(produtoDetalhes.getName(), produtoDetalhes.getCategory().getId(), id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Já existe outro produto com este nome nesta categoria.");
        }
        
        if (!productService.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (produtoDetalhes.getPrice() == null || produtoDetalhes.getPrice().doubleValue() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Preço deve ser maior que zero");
        }
        if (produtoDetalhes.getName() == null || produtoDetalhes.getName().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Nome do produto é obrigatório");
        }

        Product produto = productService.findById(id).get();
        produto.setName(produtoDetalhes.getName());
        produto.setPrice(produtoDetalhes.getPrice());

        if (produtoDetalhes.getCategory() != null && produtoDetalhes.getCategory().getId() != null) {
            if (!categoryService.existsById(produtoDetalhes.getCategory().getId())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Categoria não encontrada");
            }
            Category categoria = categoryService.findById(produtoDetalhes.getCategory().getId()).get();
            produto.setCategory(categoria);
        } else {
            produto.setCategory(null);
        }

        if (produtoDetalhes.getCategory() == null || produtoDetalhes.getCategory().getId() == null) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Categoria é obrigatória");
    }

        Product produtoAtualizado = productService.save(produto);
        return ResponseEntity.ok(produtoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!productService.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    private String validateProduct(Product produto) {
        if (produto.getName() == null || produto.getName().trim().isEmpty()) {
            return "Nome do produto é obrigatório";
        }
        if (produto.getPrice() == null || produto.getPrice().doubleValue() <= 0) {
            return "Preço deve ser maior que zero";
        }
        if (produto.getCategory() == null || produto.getCategory().getId() == null) {
            return "Categoria é obrigatória";
        }
        if (!categoryService.existsById(produto.getCategory().getId())) {
            return "Categoria não encontrada";
        }
        if (productService.existsByNameAndCategoryId(produto.getName(), produto.getCategory().getId())) {
            return "Já existe um produto com este nome nesta categoria";
        }
        return null;
    }
}