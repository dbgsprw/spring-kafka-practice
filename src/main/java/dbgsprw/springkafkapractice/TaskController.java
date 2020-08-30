package dbgsprw.springkafkapractice;

import dbgsprw.springkafkapractice.kafka.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.UUID;

@RestController
public class TaskController {

    private final KafkaProducer kafkaProducer;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public TaskController(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @GetMapping("/tasks/")
    public String[] listTask() {
        return Objects.requireNonNull(redisTemplate.keys("*")).toArray(new String[0]);
    }

    @GetMapping("/tasks/{id}")
    public Task retrieveTask(@PathVariable String id) {
        String status = redisTemplate.opsForValue().get(id);
        return new Task(id, status);
    }

    @PostMapping("/tasks/")
    public Task createTask() {
        Task task = new Task(UUID.randomUUID().toString(), Task.REGISTERED);
        this.kafkaProducer.sendMessage(task.getId(), "foo");
        redisTemplate.opsForValue().set(task.getId(), task.getStatus());

        return task;
    }
}