package com.order.orderservice.common.config;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.order.orderservice.dto.OrderRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaProducer {
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public OrderRequestDto send(String topic, OrderRequestDto orderRequestDto){
        ObjectMapper objectMapper = new ObjectMapper();

        String jsonInString = "";

        try{
            jsonInString = objectMapper.writeValueAsString(orderRequestDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        kafkaTemplate.send(topic, jsonInString);
        log.info("Sent order request: " + orderRequestDto);

        return orderRequestDto;
    }

}
