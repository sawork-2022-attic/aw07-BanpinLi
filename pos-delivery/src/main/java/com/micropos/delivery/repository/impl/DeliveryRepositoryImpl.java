package com.micropos.delivery.repository.impl;

import com.micropos.delivery.model.Delivery;
import com.micropos.delivery.repository.DeliveryRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class DeliveryRepositoryImpl implements DeliveryRepository {
    private Map<String, Delivery> rep = new ConcurrentHashMap<>();

    @Override
    public Delivery queryDeliveryByOrderId(String orderId) {
        return rep.get(orderId);
    }

    @Override
    public void saveDelivery(Delivery delivery) {
        rep.put(delivery.getOrder().getOrderId(), delivery);
    }
}
