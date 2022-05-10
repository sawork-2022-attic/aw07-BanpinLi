package com.micropos.order.repository.impl;

import com.micropos.order.model.Order;
import com.micropos.order.repository.OrderRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class OrderRepositoryImpl implements OrderRepository {
    private ConcurrentHashMap<String, List<Order>> map = new ConcurrentHashMap<>();
    private long id = Long.MAX_VALUE >> 22;

    @Override
    public List<Order> listOrder(String username) {
        return map.get(username);
    }

    @Override
    public Order queryOrderById(String username, String orderId) {
        List<Order> orderList = map.get(username);
        if(orderList == null) {
            return null;
        }
        for(Order o : orderList) {
            if(o.getOrderId().equals(orderId)) {
                return o;
            }
        }
        return null;
    }

    @Override
    public Order saveOrder(String username, Order order) {
        List<Order> orderList = map.computeIfAbsent(username, k -> new ArrayList<>());
        orderList.add(order);
        order.setOrderId(orderIdIncrement());
        return order;
    }

    private String orderIdIncrement() {
        synchronized (OrderRepositoryImpl.class) {
            id++;
        }
        return Long.toString(id);
    }
}
