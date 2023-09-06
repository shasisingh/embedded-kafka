package com.shashi.kafka.embedded;


import com.shashi.kafka.embedded.avro.model.Customer;
import com.shashi.kafka.embedded.model.Student;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;


@Component
public class KafkaProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);


    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaTemplate<String, Student> studentKafkaTemplate;
    private final KafkaTemplate<String, Customer> kafkaTemplateWithAvro;


    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate, KafkaTemplate<String, Student> studentKafkaTemplate, KafkaTemplate<String, Customer> kafkaTemplateWithAvro) {
        this.kafkaTemplate = kafkaTemplate;
        this.studentKafkaTemplate = studentKafkaTemplate;
        this.kafkaTemplateWithAvro = kafkaTemplateWithAvro;
        kafkaTemplate.setDefaultTopic("topic1");
    }

    @Scheduled(cron = "0 * * ? * *")
    public void send(){
        send(UUID.randomUUID().toString().concat(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
    }

    @Scheduled(cron = "0 * * ? * *")
    public void sendToStudent() {
        this.send("topic2", new Student("Hello Student", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE)));
    }

    @Scheduled(cron = "0 * * ? * *")
    public void sendToCustomer() {
        this.sendMessage(new Customer("customerName", UUID.randomUUID().toString(), 111));
    }

    public void send(String payload) {
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.sendDefault(payload);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                LOGGER.info("Sent message=[{}] with offset=[{}]",payload,result.getRecordMetadata().offset());
            } else {
                LOGGER.info("Unable to send message=[{}] due to : ",payload,ex);
            }
        });
    }

    public void sendMessage(Customer message) {
        CompletableFuture<SendResult<String, Customer>> future = kafkaTemplateWithAvro.send("topic3", message);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                LOGGER.info("Sent message=[{}] with offset=[{}]",message,result.getRecordMetadata().offset());
            } else {
                LOGGER.info("Unable to send message=[{}] due to : ",message,ex);
            }
        });
    }

    public void send(String topic ,Student payload) {
        CompletableFuture<SendResult<String, Student>> future = studentKafkaTemplate.send(new ProducerRecord<>(topic,
                UUID.randomUUID().toString(),payload));
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                LOGGER.info("Sent message=[{}] with offset=[{}]",payload,result.getRecordMetadata().offset());
            } else {
                LOGGER.info("Unable to send message=[{}] due to : ",payload,ex);
            }
        });
    }




}
