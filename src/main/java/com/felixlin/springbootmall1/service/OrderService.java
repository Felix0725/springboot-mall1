package com.felixlin.springbootmall1.service;

import com.felixlin.springbootmall1.dto.CreateOrderRequest;

public interface OrderService {

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
