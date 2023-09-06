package com.felixlin.springbootmall1.service;

import com.felixlin.springbootmall1.dto.CreateOrderRequest;
import com.felixlin.springbootmall1.model.Order;

public interface OrderService {

    Order getOrderById(Integer orderId);

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
