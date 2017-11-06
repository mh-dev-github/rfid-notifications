package com.mh.rfid.notifications.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.mh.rfid.Application;

import lombok.val;

@Configuration
@PropertySource("classpath:" + Application.SPRING_CONFIG_NAME_APPLICATION + ".properties")
public class AmqpConfiguration {

	public AmqpConfiguration() {
		super();
	}

	@Bean
	@ConfigurationProperties(prefix = "rfid.amqp")
	public AmqpProperties amqpProperties() {
		val result = new AmqpProperties();
		return result;
	}
	
	@Bean
	public Queue queueNotification(AmqpProperties properties) {
		val queue = QueueBuilder.durable(properties.getQueueNotification()).build();
		return queue;
	}
}