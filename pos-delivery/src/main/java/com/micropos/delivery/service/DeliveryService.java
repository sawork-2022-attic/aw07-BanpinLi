package com.micropos.delivery.service;

import com.micropos.delivery.model.Delivery;
import com.micropos.delivery.dto.OrderDto;

public interface DeliveryService {

    Delivery getDeliveryByOrderId(String orderId);

    Delivery createDelivery(OrderDto order);

}
