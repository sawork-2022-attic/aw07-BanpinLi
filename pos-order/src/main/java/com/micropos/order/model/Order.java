package com.micropos.order.model;

import com.micropos.order.dto.ProductDto;

import java.util.Objects;

public class Order {

    private String orderId;

    private ProductDto product;

    private int quantity;

    public Order() {
    }

    public Order(String orderId, ProductDto product, int quantity) {
        this.orderId = orderId;
        this.product = product;
        this.quantity = quantity;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(ProductDto product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
