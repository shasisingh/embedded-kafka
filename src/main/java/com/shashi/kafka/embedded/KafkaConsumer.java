package com.shashi.kafka.embedded;

import com.shashi.kafka.embedded.avro.model.Customer;
import com.shashi.kafka.embedded.model.Student;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class KafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);


    @KafkaListener(topics = "${test.topic}")
    public void receive(ConsumerRecord<?, ?> consumerRecord) {
        LOGGER.info("received value='{}'", consumerRecord.value().toString());
    }

    @KafkaListener(topics = "topic2", containerFactory = "studentKafkaListenerContainerFactory")
    public void greetingListener(Student student) {
        LOGGER.debug("received payload='{}'", student);
    }

    @KafkaListener(topics = "topic3", containerFactory = "kafkaCustomerListenerContainerFactory")
    public void customerListener(Customer customer) {
        LOGGER.debug("received payload='{}'", customer);
    }
}
