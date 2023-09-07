package com.shashi.kafka.embedded;

import com.shashi.kafka.embedded.avro.model.Customer;
import com.shashi.kafka.embedded.model.Student;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;


@Component
public class KafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    private CountDownLatch latch = new CountDownLatch(1);
    private String payload;

    @KafkaListener(topics = "${test.topic}")
    public void receive(ConsumerRecord<?, ?> consumerRecord) {
        LOGGER.info("received value='{}'", consumerRecord.value());
        this.payload =consumerRecord.value().toString();
        latch.countDown();
    }

    @KafkaListener(topics = "topic2", containerFactory = "studentKafkaListenerContainerFactory")
    public void greetingListener(Student student) {
        LOGGER.info("received payload='{}'", student);
    }

    @KafkaListener(topics = "topic3", containerFactory = "kafkaCustomerListenerContainerFactory")
    public void customerListener(Customer customer) {
        LOGGER.info("received payload='{}'", customer);
    }

    @KafkaListener(topics = "topic1", containerFactory = "filterKafkaListenerContainerFactory")
    public void listenWithFilter(String message) {
        LOGGER.info("Received Message in filtered listener: " + message);
        this.payload=message;
        latch.countDown();
    }

    public void resetLatch() {
        latch = new CountDownLatch(1);
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public String getPayload() {
        return payload;
    }
}
