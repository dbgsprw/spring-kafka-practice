package dbgsprw.springkafkapractice.kafka;

import dbgsprw.springkafkapractice.kafka.producer.KafkaProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class KafkaProducerTests {
    @Autowired
    private KafkaProducer kafkaProducer;

    @Value(value = "${kafka.topic.name}")
    private String topicName;

    @Test
    public void produce() throws ExecutionException, InterruptedException {
        ListenableFuture<SendResult<String, String>> future = kafkaProducer.sendMessage("test key", "test service");
        SendResult<String, String> sendResult = future.get();
        assertEquals(sendResult.getRecordMetadata().topic(), topicName);
        assertEquals(sendResult.getProducerRecord().value(), "test service");
    }
}
