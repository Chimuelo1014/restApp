package com.restorapp.backend.service.impl;

import com.restorapp.backend.entity.Product;
import com.restorapp.backend.repository.ProductRepository;
import com.restorapp.backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  @Override
  public List<Product> getAllProducts() {
    return productRepository.findAll();
  }

  @Override
  public List<Product> getActiveProducts() {
    return productRepository.findByActiveTrue();
  }

  @Override
  public Product getProductById(Long id) {
    return productRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
  }

  @Override
  public Product createProduct(Product product) {
    return productRepository.save(product);
  }

  @Override
  public Product updateProduct(Long id, Product productDetails) {
    Product product = getProductById(id);
    product.setName(productDetails.getName());
    product.setDescription(productDetails.getDescription());
    product.setPrice(productDetails.getPrice());
    product.setStock(productDetails.getStock());
    product.setActive(productDetails.getActive());
    return productRepository.save(product);
  }

  @Override
  public void deleteProduct(Long id) {
    Product product = getProductById(id);
    // Soft delete: desactivar en lugar de borrar
    product.setActive(false);
    productRepository.save(product);
  }
}
