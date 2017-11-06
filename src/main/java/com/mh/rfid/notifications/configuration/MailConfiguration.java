package com.mh.rfid.notifications.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.mh.rfid.Application;

import lombok.val;


@Configuration
@PropertySource("classpath:" + Application.SPRING_CONFIG_NAME_APPLICATION + ".properties")
public class MailConfiguration {

	public MailConfiguration() {
		super();
	}

	@Bean
	@ConfigurationProperties(prefix = "rfid.mail")
	public MailProperties mailProperties() {
		val result = new MailProperties();
		return result;
	}

}