package com.micropos.order.service;

import com.micropos.order.model.Order;

import java.util.List;

public interface OrderService {

    List<Order> getOrderList(String username);

    Order getOrder(String username, String orderId);

    Order saveOrder(String username, Order order);

}
