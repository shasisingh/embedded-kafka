package com.shashi.kafka.embedded;

public class RunKafkaBroker  {

    private static EmbeddedKafkaHolder kafkaHolder;

    public static void main(String[] args) {
        kafkaHolder = new EmbeddedKafkaHolder()
                .topics("topic1", "topic2", "topic3")
                .numberOfBroker(1)
                .numberOfPartitions(2)
                .controlledShutdown(true)
                .ports(30001)
                .start();
        System.out.println("STARTED AND RUNNING ON => " + kafkaHolder.getEmbeddedKafka().getBrokersAsString());
       onExitCall();
    }


    private static void onExitCall(){
        Runtime.getRuntime().addShutdownHook(new Thread( () -> {
            System.out.println("**** Shutting down EmbeddedKafkaBroker apps ******");
            kafkaHolder.stop();
        }));
    }
}
