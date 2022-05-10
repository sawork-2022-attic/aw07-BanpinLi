package com.micropos.delivery.model;

import com.micropos.delivery.dto.OrderDto;

public class Delivery {

    private OrderDto order;

    private String status;

    public Delivery(OrderDto order, String status) {
        this.order = order;
        this.status = status;
    }

    public Delivery() {
    }

    public OrderDto getOrder() {
        return order;
    }

    public void setOrder(OrderDto order) {
        this.order = order;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
