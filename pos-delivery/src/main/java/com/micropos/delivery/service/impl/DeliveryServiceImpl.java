package com.micropos.delivery.service.impl;

import com.micropos.delivery.dto.OrderDto;
import com.micropos.delivery.model.Delivery;
import com.micropos.delivery.repository.DeliveryRepository;
import com.micropos.delivery.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Override
    public Delivery getDeliveryByOrderId(String orderId) {
        return deliveryRepository.queryDeliveryByOrderId(orderId);
    }

    @Override
    public Delivery createDelivery(OrderDto order) {
        Delivery delivery = new Delivery();
        delivery.setOrder(order);
        delivery.setStatus("您的包裹正在运输中...");
        deliveryRepository.saveDelivery(delivery);
        return delivery;
    }
}
