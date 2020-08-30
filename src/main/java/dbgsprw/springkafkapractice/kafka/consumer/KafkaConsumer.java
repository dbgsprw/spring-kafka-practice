package dbgsprw.springkafkapractice.kafka.consumer;

import dbgsprw.springkafkapractice.kafka.producer.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    private final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

    @KafkaListener(topics = "${kafka.topic.name}", groupId = "${kafka.groupId}")
    public void postingListener(String value) {
        logger.info("Received message.");
    }
}