package com.felixlin.springbootmall1.service.impl;

import com.felixlin.springbootmall1.dao.OrderDao;
import com.felixlin.springbootmall1.dao.ProductDao;
import com.felixlin.springbootmall1.dao.UserDao;
import com.felixlin.springbootmall1.dto.BuyItem;
import com.felixlin.springbootmall1.dto.CreateOrderRequest;
import com.felixlin.springbootmall1.dto.OrderQueryParams;
import com.felixlin.springbootmall1.model.Order;
import com.felixlin.springbootmall1.model.OrderItem;
import com.felixlin.springbootmall1.model.Product;
import com.felixlin.springbootmall1.model.User;
import com.felixlin.springbootmall1.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    @Override
    public Integer countOrder(OrderQueryParams orderQueryParams) {
        return orderDao.countOrder(orderQueryParams);
    }

    @Override
    public List<Order> getOrders(OrderQueryParams orderQueryParams) {
        List<Order> orderList = orderDao.getOrders(orderQueryParams);

        for(Order order : orderList) {
            List<OrderItem> orderItemList = orderDao.getOrderItemByOrderId(order.getOrderId());

            order.setOrderItemList(orderItemList);
        }
        return  orderList;
    }

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
        // 檢查 user 是否存在
        User user  = userDao.getUserById(userId);

        if (user == null) {
            log.warn("該 userId {} 不存在", userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

        // for loop 前端所傳過來的 buyItemList，使用 ProductDao 的 getProductById，
        // 根據前端所傳過來的 ProductId 的值，去資料庫查詢出這個 product 的數據出來
        for(BuyItem buyItem : createOrderRequest.getBuyItemList()) {
            Product product = productDao.getProductById(buyItem.getProductId());

            // 檢查 product 是否存在、庫存是否足夠
            if (product == null) {
                log.warn("商品 {} 不存在", buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            } else if (product.getStock() < buyItem.getQuantity()) {
                log.warn("商品 {} 庫存數量不足，無法購買，剩餘庫存 {}，欲購買數量 {}",
                        buyItem.getProductId(), product.getStock(), buyItem.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            // 扣除商品庫存
            productDao.updateStock(product.getProductId(), product.getStock() - buyItem.getQuantity());

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
