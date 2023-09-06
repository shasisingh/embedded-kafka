package com.shashi.kafka.embedded;

import org.apache.kafka.common.KafkaException;
import org.springframework.kafka.test.EmbeddedKafkaBroker;


public class EmbeddedKafkaHolder {

    private static final EmbeddedKafkaBroker embeddedKafka = new EmbeddedKafkaBroker(1, false)
            .kafkaPorts(30001)
            .brokerProperty("ssl.client.auth","required")
            .brokerProperty("ssl.key.password","nosecret")
            .brokerProperty("ssl.keystore.location","standalone.keystore.jks")
            .brokerProperty("ssl.keystore.key","standalone.keystore.jks")
            .brokerProperty("ssl.keystore.password","nosecret")
            .brokerProperty("ssl.keystore.type","JKS")
            .brokerProperty("ssl.truststore.location","standalone.truststore.jks")
            .brokerProperty("ssl.truststore.password","nosecret")
            .brokerProperty("ssl.truststore.type","JKS")
            .brokerListProperty("spring.kafka.bootstrap-servers");



    private static boolean started;

    public static EmbeddedKafkaBroker getEmbeddedKafka() {
        if (!started) {
            try {
                embeddedKafka.afterPropertiesSet();
            }
            catch (Exception e) {
                throw new KafkaException("Embedded broker failed to start", e);
            }
            started = true;
        }
        return embeddedKafka;
    }

    private EmbeddedKafkaHolder() {
        super();
    }

    public void stop(){
        getEmbeddedKafka().destroy();
    }
}
