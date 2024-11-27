package com.order.productservice.common.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.order.productservice.repository.ProductRepository;
import com.order.productservice.service.ProductService;
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

    private final ProductService productService;

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

        Long productId = ((Number) map.get("productId")).longValue(); // 메시지에서 productId 추출

        try {
            // Redis를 이용한 재고 감소
            productService.decreaseWithRedis(productId);
        } catch (IllegalStateException e) {
            log.error("Failed to decrease stock for productId {}: {}", productId, e.getMessage());
        }
    }

    }

