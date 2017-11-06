package com.mh.rfid.notifications.components.amqp;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mh.rfid.dto.ReportDto;
import com.mh.rfid.notifications.service.api.NotificationService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Consumer {

	@Autowired
	private NotificationService notificationService;

	protected NotificationService getNotificationService() {
		return notificationService;
	}

	@RabbitListener(queues = "${rfid.amqp.queue-notification}")
	public void recievedMessage(ReportDto report) {
		log.debug("Mensaje NOTIFICATION");

		getNotificationService().notify(report);
	}
}