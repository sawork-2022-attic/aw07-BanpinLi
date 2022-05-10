package com.micropos.delivery.rest;

import com.micropos.delivery.api.DeliveryApi;
import com.micropos.delivery.dto.DeliveryDto;
import com.micropos.delivery.mapper.DeliveryMapper;
import com.micropos.delivery.model.Delivery;
import com.micropos.delivery.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DeliveryController implements DeliveryApi {

    @Autowired
    private DeliveryService deliveryService;
    @Autowired
    private DeliveryMapper deliveryMapper;

    @Override
    public ResponseEntity<DeliveryDto> getDelivery(String orderId) {
        Delivery delivery = deliveryService.getDeliveryByOrderId(orderId);
        if(delivery == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(deliveryMapper.toDeliveryDto(delivery), HttpStatus.OK);
    }
}
