package com.micropos.delivery.bean;

import com.micropos.delivery.dto.OrderDto;
import com.micropos.delivery.service.DeliveryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.Consumer;

public class DeliveryConsumer implements Consumer<OrderDto> {

    @Autowired
    private DeliveryService deliveryService;

    private final Logger log = LoggerFactory.getLogger(DeliveryConsumer.class);

    @Override
    public void accept(OrderDto orderDto) {
        log.info("对订单号为：" + orderDto.getOrderId() + " 的订单进行处理...");
        deliveryService.createDelivery(orderDto);
    }
}
