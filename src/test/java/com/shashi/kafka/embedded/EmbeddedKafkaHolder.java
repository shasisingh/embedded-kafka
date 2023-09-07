package com.shashi.kafka.embedded;

import org.apache.kafka.common.KafkaException;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.kafka.test.EmbeddedKafkaBroker;


public class EmbeddedKafkaHolder implements BeforeAllCallback, AfterAllCallback {

    private EmbeddedKafkaBroker embeddedKafka;

    private boolean controlledShutdown;
    private static boolean started;

    private int numberOfBroker;
    private int numberOfPartitions;
    private int[] kafkaPorts;
    private String[] topics;

    public EmbeddedKafkaBroker INSTANCE() {
        if (!started) {
            startWithDefaultBroker();
        }
        return embeddedKafka;
    }


    public EmbeddedKafkaHolder numberOfBroker(int count) {
        this.numberOfBroker = count;
        return this;
    }

    public EmbeddedKafkaHolder controlledShutdown(boolean controlledShutdown) {
        this.controlledShutdown = controlledShutdown;
        return this;
    }

    public EmbeddedKafkaHolder numberOfPartitions(int numberOfPartitions) {
        this.numberOfPartitions = numberOfPartitions;
        return this;
    }

    public EmbeddedKafkaHolder topics(String... topics) {
        this.topics = topics;
        return this;
    }

    public EmbeddedKafkaHolder ports(int... kafkaPorts) {
        this.kafkaPorts = kafkaPorts;
        return this;
    }

    public EmbeddedKafkaHolder startWithDefaultBroker(String... topic) {
        if (topic == null || topic.length == 0) {
            topic = new String[]{"topic1", "topic2"};
        }
        embeddedKafka = new EmbeddedKafkaBroker(1, false, 2, topic)
                .kafkaPorts(30009)
                .brokerListProperty("spring.kafka.bootstrap-servers");
        init();
        return this;
    }

    public EmbeddedKafkaHolder start() {
        embeddedKafka = new EmbeddedKafkaBroker(numberOfBroker, controlledShutdown, numberOfPartitions, topics)
                .kafkaPorts(kafkaPorts)
                .brokerListProperty("spring.kafka.bootstrap-servers");
        init();
        return this;
    }

    private void init() {
        if (!started) {
            try {
                embeddedKafka.afterPropertiesSet();
            } catch (Exception e) {
                throw new KafkaException("Embedded broker failed to start", e);
            }

            System.out.println("STARTED AND RUNNING ON => " + embeddedKafka.getBrokersAsString());
            System.setProperty("BROKER_SERVER", embeddedKafka.getBrokersAsString());
            started = true;
        }
    }

    public EmbeddedKafkaHolder() {
        super();
    }

    public void stop() {
        embeddedKafka.destroy();
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) {
        this.stop();
    }

    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        this.startWithDefaultBroker();
    }
}
