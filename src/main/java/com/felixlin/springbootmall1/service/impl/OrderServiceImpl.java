package com.felixlin.springbootmall1.service.impl;

import com.felixlin.springbootmall1.dao.OrderDao;
import com.felixlin.springbootmall1.dao.ProductDao;
import com.felixlin.springbootmall1.dto.BuyItem;
import com.felixlin.springbootmall1.dto.CreateOrderRequest;
import com.felixlin.springbootmall1.model.Order;
import com.felixlin.springbootmall1.model.OrderItem;
import com.felixlin.springbootmall1.model.Product;
import com.felixlin.springbootmall1.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Override
    public Order getOrderById(Integer orderId) {
       Order order = orderDao.getOrderById(orderId);

       List<OrderItem> orderItemList = orderDao.getOrderItemByOrderId(orderId);

       order.setOrderItemList(orderItemList);

       return order;
    }

    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

        // for loop 前端所傳過來的 buyItemList，使用 ProductDao 的 getProductById，
        // 根據前端所傳過來的 ProductId 的值，去資料庫查詢出這個 product 的數據出來
        for(BuyItem buyItem : createOrderRequest.getBuyItemList()) {
            Product product = productDao.getProductById(buyItem.getProductId());

            // 計算總價錢
            int amount = buyItem.getQuantity() * product.getPrice();
            totalAmount = totalAmount + amount;

            // 轉換 BuyItem to OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);

            orderItemList.add(orderItem);
        }

        // 創建訂單
        Integer orderId = orderDao.createOrder(userId, totalAmount);

        orderDao.createOrderItems(orderId, orderItemList);

        return orderId;
    }
}
