package dbgsprw.springkafkapractice.kafka.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class KafkaProducer {

    private final KafkaTemplate<Integer, String> template;
    private final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

    @Value(value = "${kafka.topic.name}")
    private String topicName;

    public KafkaProducer(KafkaTemplate<Integer, String> template) {
        this.template = template;
    }

    public ListenableFuture<SendResult<Integer, String>> sendMessage(String message) {
        ListenableFuture<SendResult<Integer, String>> future = template.send(topicName, message);
        future.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                logger.warn("Send message failure.");
            }

            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                logger.warn("Send message success.");
            }
        });
        return future;
    }


}
