package com.restorapp.backend.service;

import com.restorapp.backend.entity.Product;
import java.util.List;

public interface ProductService {
  List<Product> getAllProducts();

  List<Product> getActiveProducts();

  Product getProductById(Long id);

  Product createProduct(Product product);

  Product updateProduct(Long id, Product product);

  void deleteProduct(Long id); // Soft delete or Hard delete based on req
}
