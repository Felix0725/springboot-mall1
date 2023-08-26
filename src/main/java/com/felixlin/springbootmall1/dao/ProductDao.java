package com.felixlin.springbootmall1.dao;

import com.felixlin.springbootmall1.model.Product;

public interface ProductDao {

    Product getProductById(Integer productId);
}
