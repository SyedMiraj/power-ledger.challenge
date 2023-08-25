package com.powerledger.challenge.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.powerledger.challenge.domains.CapacityInfoMessage;
import com.powerledger.challenge.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CapacityUpdateListener {

    private static final Logger logger = LoggerFactory.getLogger(CapacityUpdateListener.class);

    @KafkaListener(
            topics = Constants.TOPIC_NAME,
            groupId = Constants.GROUP_ID)
    void listeningCapacityUpdate(String message) {
        logger.info("Capacity update listening.");
        ObjectMapper mapper = new ObjectMapper();
        CapacityInfoMessage capacityInfoMessage;
        try {
            capacityInfoMessage = mapper.readValue(message, CapacityInfoMessage.class);
            logger.info(capacityInfoMessage.getMessage());
        } catch (JsonProcessingException e) {
            logger.error("Error in kafka listening.");
        }
    }
}
