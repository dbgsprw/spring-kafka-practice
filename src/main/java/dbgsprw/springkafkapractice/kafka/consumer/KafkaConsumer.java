package dbgsprw.springkafkapractice.kafka.consumer;

import dbgsprw.springkafkapractice.Task;
import dbgsprw.springkafkapractice.kafka.producer.KafkaProducer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    private final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @KafkaListener(topics = "${kafka.topic.name}", groupId = "${kafka.groupId}", concurrency = "10")
    public void doSomethingLongTimeTask(ConsumerRecord<String, String> consumerRecord) {
        String key = consumerRecord.key();
        String value = consumerRecord.value();
        logger.info("Received message. {}:{}", key, value);
        try {
            redisTemplate.opsForValue().set(key, Task.RUNNING);
            Thread.sleep(10000);
            redisTemplate.opsForValue().set(key, Task.SUCCESS);
        } catch (InterruptedException e) {
            redisTemplate.opsForValue().set(key, Task.FAILURE);
            e.printStackTrace();
        }
        logger.info("Task Done. {}:{}", key, value);
    }
}