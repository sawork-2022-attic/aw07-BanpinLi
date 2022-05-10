package com.micropos.delivery;

import com.micropos.delivery.bean.DeliveryConsumer;
import com.micropos.delivery.dto.OrderDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Consumer;

@SpringBootApplication
public class DeliveryApplication {
    public static void main(String[] args) {
        SpringApplication.run(DeliveryApplication.class, args);
    }

    @Bean
    public Consumer<OrderDto> deliveryConsumer() {
        return new DeliveryConsumer();
    }
}
