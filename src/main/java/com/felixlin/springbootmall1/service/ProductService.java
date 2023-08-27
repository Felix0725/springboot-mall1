package com.felixlin.springbootmall1.service;

import com.felixlin.springbootmall1.dto.ProductRequest;
import com.felixlin.springbootmall1.model.Product;

public interface ProductService {
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
