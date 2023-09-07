package com.felixlin.springbootmall1.dao;

import com.felixlin.springbootmall1.dto.OrderQueryParams;
import com.felixlin.springbootmall1.model.Order;
import com.felixlin.springbootmall1.model.OrderItem;

import java.util.List;

public interface OrderDao {

    Integer countOrder(OrderQueryParams orderQueryParams);

    List<Order> getOrders(OrderQueryParams orderQueryParams);

    Order getOrderById(Integer orderId);

    List<OrderItem> getOrderItemByOrderId(Integer orderId);

    Integer createOrder(Integer userId, Integer totalAmount);

    void createOrderItems(Integer orderId, List<OrderItem> orderItemList);
}
