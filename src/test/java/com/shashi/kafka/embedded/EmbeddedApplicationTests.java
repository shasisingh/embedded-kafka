package com.shashi.kafka.embedded;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.test.EmbeddedKafkaBroker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class EmbeddedApplicationTests {

	private final EmbeddedKafkaBroker embeddedKafkaBroker = new EmbeddedKafkaHolder().INSTANCE();

	@Autowired
	ApplicationContext applicationContext;

	@Test
	void testContextLoads() {
		embeddedKafkaBroker.getBrokersAsString();
		assertNotNull(applicationContext,"must not be null");
	}

}
