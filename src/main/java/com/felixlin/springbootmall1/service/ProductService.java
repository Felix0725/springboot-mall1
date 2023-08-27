package com.felixlin.springbootmall1.service;

import com.felixlin.springbootmall1.dto.ProductRequest;
import com.felixlin.springbootmall1.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProducts();
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
