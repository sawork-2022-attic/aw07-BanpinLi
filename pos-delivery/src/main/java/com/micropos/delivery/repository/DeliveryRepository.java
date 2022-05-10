package com.micropos.delivery.repository;

import com.micropos.delivery.model.Delivery;

public interface DeliveryRepository {

    Delivery queryDeliveryByOrderId(String orderId);

    void saveDelivery(Delivery delivery);

}
