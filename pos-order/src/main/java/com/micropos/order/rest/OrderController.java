package com.micropos.order.rest;

import com.micropos.order.api.OrderApi;
import com.micropos.order.dto.OrderDto;
import com.micropos.order.dto.OrdersDto;
import com.micropos.order.dto.ProductDto;
import com.micropos.order.mapper.OrderMapper;
import com.micropos.order.model.Order;
import com.micropos.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController implements OrderApi {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderMapper orderMapper;

    private static final String POS_PRODUCTS = "http://POS-PRODUCTS/";

    @Override
    public ResponseEntity<OrderDto> addOrder(String username, String productId, Integer quantity) {
        ProductDto productDto = restTemplate.getForObject(POS_PRODUCTS + "api/products/" + productId, ProductDto.class);
        if(productDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Order order = new Order();
        order.setProduct(productDto);
        order.setQuantity(quantity);
        order = orderService.saveOrder(username, order);
        // TODO: 使用一个消息队列将购物消息发送给delivery
        // -------------------------------------------
        return new ResponseEntity<>(orderMapper.toOrderDto(order), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<OrderDto> getOrder(String username, String orderId) {
        Order order = orderService.getOrder(username, orderId);
        if(order == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(orderMapper.toOrderDto(order), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<OrderDto>> getOrders(String username) {
        List<Order> orderList = orderService.getOrderList(username);
        if(orderList == null || orderList.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<OrderDto> orderDtoList = new ArrayList<>();
        orderList.forEach(o -> orderDtoList.add(orderMapper.toOrderDto(o)));
        return new ResponseEntity<>(orderDtoList, HttpStatus.OK);
    }
}
