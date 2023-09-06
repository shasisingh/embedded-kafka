package com.shashi.kafka.embedded;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.test.EmbeddedKafkaBroker;

public class RunKafkaBroker {

    final static EmbeddedKafkaBroker broker = EmbeddedKafkaHolder.getEmbeddedKafka();

    static {
        EmbeddedKafkaHolder.getEmbeddedKafka()
                .addTopics(
                        new NewTopic("topic1",1,(short) 1),
                        new NewTopic("topic2",1,(short) 1),
                        new NewTopic("topic3",1,(short) 1)
                );
    }

    public static void main(String[] args) {
       System.out.println("STARTED AND RUNNING ON => " + broker.getBrokersAsString());
       onExitCall();
    }


    public static void onExitCall(){
        Runtime.getRuntime().addShutdownHook(new Thread( () -> {
            System.out.println("**** Shutting down EmbeddedKafkaBroker apps ******");
            broker.destroy();
        }));
    }

}
