package com.micropos.delivery.mapper;

import com.micropos.delivery.dto.DeliveryDto;
import com.micropos.delivery.model.Delivery;
import org.mapstruct.Mapper;

@Mapper
public interface DeliveryMapper {

    Delivery toDelivery(DeliveryDto deliveryDto);

    DeliveryDto toDeliveryDto(Delivery delivery);

}
