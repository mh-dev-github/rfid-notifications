package com.mh.rfid.notifications.service.impl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.mh.rfid.dto.ReportDto;
import com.mh.rfid.notifications.configuration.MailProperties;
import com.mh.rfid.notifications.service.api.NotificationService;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	private MailProperties properties;

	@Autowired
	private JavaMailSender mailSender;

	@Override
	public void notify(ReportDto report) {
		try {
			MimeMessage message = this.mailSender.createMimeMessage();

			// @formatter:off
			message = crearMimeMessage(
					message, 
					properties.getFrom(), 
					properties.getFromPersonal(), 
					properties.getTo(), 
					properties.getCc(), 
					report.getAsunto(), 
					report.getContenido(),
					true, 
					report.getAttachments());
			// @formatter:on

			this.mailSender.send(message);
		} catch (RuntimeException e) {
			log.error("Ocurrio una excepción al intentar enviar la siguiente notificación", e);
			try {
				Thread.sleep(10 * 60 * 1000);
			} catch (InterruptedException e1) {
			}
			throw e;
		}
	}

	static private MimeMessage crearMimeMessage(MimeMessage message, String from, String fromPersonal, String to[],
			String cc[], String subject, String content, boolean html, File attachments[]) {

		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			helper.setFrom(from, fromPersonal);
			helper.setTo(Arrays.stream(to).filter(s -> !s.isEmpty()).toArray(String[]::new));
			helper.setCc(Arrays.stream(cc).filter(s -> !s.isEmpty()).toArray(String[]::new));
			helper.setSubject(subject);
			helper.setText(content, html);

			for (File attachment : attachments) {
//				FileSystemResource file = new FileSystemResource(attachment);
				val name = attachment.getName();
				helper.addAttachment(name, attachment);
			}

			return message;
		} catch (MessagingException | UnsupportedEncodingException e) {
			throw new RuntimeException("Ocurrio una excepción al crear el mensaje de notificación", e);
		}
	}
}
