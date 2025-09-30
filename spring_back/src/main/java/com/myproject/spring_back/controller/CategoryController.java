package com.myproject.spring_back.controller;

import com.myproject.spring_back.model.Category;
import com.myproject.spring_back.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categorias")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<?> listar(
            @RequestParam(value = "q", required = false) String q,
            @RequestParam(value = "paginated", required = false, defaultValue = "false") boolean paginated,
            @PageableDefault(size = 7, sort = "name") Pageable pageable) {
        
        if (paginated) {
            var page = (q == null || q.isBlank()) 
                ? categoryService.findAll(pageable)
                : categoryService.searchByName(q, pageable);
            return ResponseEntity.ok(page);
        } else {
            var result = (q == null || q.isBlank()) 
                ? categoryService.findAll()
                : categoryService.searchByName(q);
            return ResponseEntity.ok(result);
        }
    }

    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody Category categoria) {
        
        if (categoryService.existsByName(categoria.getName())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Já existe uma categoria com este nome");
        }
        
        Category categoriaSalva = categoryService.save(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @Valid @RequestBody Category categoriaDetalhes) {
        if (!categoryService.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (categoryService.existsByNameAndIdNot(categoriaDetalhes.getName(), id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Já existe outra categoria com este nome");
        }

        Category categoria = categoryService.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada com id: " + id));
        
        categoria.setName(categoriaDetalhes.getName());
        
        Category categoriaAtualizada = categoryService.save(categoria);
        return ResponseEntity.ok(categoriaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!categoryService.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        
        categoryService.deleteById(id); 
        
        return ResponseEntity.noContent().build();
    }
}