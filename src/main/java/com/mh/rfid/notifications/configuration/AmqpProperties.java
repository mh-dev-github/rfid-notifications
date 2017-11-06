package com.mh.rfid.notifications.configuration;

import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Validated
public class AmqpProperties {

	private String queueNotification;
	
}