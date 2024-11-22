package com.order.productservice.common.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.order.productservice.entity.Products;
import com.order.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {


    private final ProductRepository productRepository;

    @KafkaListener(topics = "example-product-topic")
    public void updateQty(String kafkaMessage) {
        log.info("kafka message:" + kafkaMessage);

        Map<Object, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try{
            map = mapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>() {
            });

        }catch (JsonProcessingException ex){
            ex.printStackTrace();
        }

        Products product = productRepository.findByProductId((long)map.get("productId")).orElseThrow(()->
                new IllegalArgumentException("product not found Kafka Consumer"));


    }
}
