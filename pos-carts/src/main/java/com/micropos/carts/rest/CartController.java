package com.micropos.carts.rest;

import com.micropos.carts.api.CartsApi;
import com.micropos.carts.dto.CartDto;
import com.micropos.carts.dto.OrderDto;
import com.micropos.carts.dto.ProductDto;
import com.micropos.carts.mapper.CartMapper;
import com.micropos.carts.model.Cart;
import com.micropos.carts.model.Item;
import com.micropos.carts.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CartController implements CartsApi {

    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;
    @Autowired
    private CartService cartService;
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private CircuitBreakerFactory circuitBreakerFactory;

    private static final String POS_PRODUCTS = "http://POS-PRODUCTS";
    private static final String POS_ORDER = "http://POS-ORDER";

    @Override
    public ResponseEntity<List<OrderDto>> charge(String username) {
        Cart cart = cartService.getCart(username);
        if(cart == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<OrderDto> orderDtoList = new ArrayList<>();
        for(Item item : cart.getItems()) {
            String url = POS_ORDER + "/api/order/" + username + "/" + item.getProduct().getId() + "/" + item.getQuantity();
            OrderDto orderDto = new OrderDto();
            orderDto = restTemplate.postForObject(url, orderDto, OrderDto.class);
            orderDtoList.add(orderDto);
        }
        cartService.emptyCart(username);
        return new ResponseEntity<>(orderDtoList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CartDto> addProduct(String username, String productId) {
        ProductDto productDto = getProductDto(productId);
        if(productDto == null) {
            return getCart(username);
        }
        if(!cartService.save(productDto, username)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return getCart(username);
    }

    @Override
    public ResponseEntity<CartDto> decreaseProduct(String username, String productId) {
        ProductDto productDto = getProductDto(productId);
        if(productDto == null) {
            return getCart(username);
        }
        if(!cartService.updateDecrease(productDto, username)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return getCart(username);
    }

    @Override
    public ResponseEntity<CartDto> deleteProduct(String username, String productId) {
        ProductDto productDto = getProductDto(productId);
        if(productDto == null) {
            return getCart(username);
        }
        if(!cartService.remove(productDto, username)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return getCart(username);
    }

    @Override
    public ResponseEntity<CartDto> emptyCart(String username) {
        if(!cartService.emptyCart(username)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return getCart(username);
    }

    @Override
    public ResponseEntity<CartDto> increaseProduct(String username, String productId) {
        ProductDto productDto = getProductDto(productId);
        if(productDto == null) {
            return getCart(username);
        }
        if(!cartService.updateIncrement(productDto, username)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return getCart(username);
    }

    @Override
    public ResponseEntity<CartDto> getCart(String username) {
        System.out.println("get cart.");
        Cart cart = cartService.getCart(username);
        if(cart == null) {
            cart = cartService.createCart(username);
        }
        return new ResponseEntity<>(cartMapper.toCartDto(cart), HttpStatus.OK);
    }

    private ProductDto getProductDto(String id) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker");
        String url = POS_PRODUCTS + "/api/products/" + id;
        return circuitBreaker.run(() -> restTemplate.getForObject(url, ProductDto.class), e -> null);
    }
}
