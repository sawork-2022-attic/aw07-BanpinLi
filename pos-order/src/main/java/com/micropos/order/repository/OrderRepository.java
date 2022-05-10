package com.micropos.order.repository;

import com.micropos.order.model.Order;

import java.util.List;

public interface OrderRepository {

    List<Order> listOrder(String username);

    Order queryOrderById(String username, String orderId);

    Order saveOrder(String username, Order order);

}
