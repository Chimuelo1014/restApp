package com.restorapp.backend.controller;

import com.restorapp.backend.entity.Product;
import com.restorapp.backend.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProductController {

  private final ProductService productService;

  @GetMapping
  public ResponseEntity<List<Product>> getAllProducts() {
    // En un caso real, esto podría filtrar solo activos para USER
    return ResponseEntity.ok(productService.getAllProducts());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Product> getProductById(@PathVariable Long id) {
    return ResponseEntity.ok(productService.getProductById(id));
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
    return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(product));
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Product> updateProduct(@PathVariable Long id, @Valid @RequestBody Product product) {
    return ResponseEntity.ok(productService.updateProduct(id, product));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
    productService.deleteProduct(id);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/{id}/status")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Product> updateProductStatus(@PathVariable Long id, @RequestBody Map<String, Boolean> request) {
    Boolean isActive = request.get("is_active");
    if (isActive == null) {
      throw new RuntimeException("El campo is_active es requerido");
    }
    return ResponseEntity.ok(productService.updateProductStatus(id, isActive));
  }
}
