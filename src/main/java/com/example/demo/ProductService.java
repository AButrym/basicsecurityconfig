package com.example.demo;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    ProductRepository productRepository;
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Long create(CreateProductReq request) {
        var product = new Product();
        product.setName(request.name());
        product = productRepository.save(product);
        return product.getId();
    }

    public Product getById(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException()
        );
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }
}
