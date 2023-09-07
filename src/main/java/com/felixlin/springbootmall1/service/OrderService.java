package com.felixlin.springbootmall1.service;

import com.felixlin.springbootmall1.dto.CreateOrderRequest;
import com.felixlin.springbootmall1.dto.OrderQueryParams;
import com.felixlin.springbootmall1.model.Order;

import java.util.List;

public interface OrderService {

    Integer countOrder(OrderQueryParams orderQueryParams);

    List<Order> getOrders(OrderQueryParams orderQueryParams);

    Order getOrderById(Integer orderId);

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
