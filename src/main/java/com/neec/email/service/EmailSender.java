package com.neec.email.service;

import java.util.Map;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;;

@Service
public class EmailSender {
	@Value("${spring.mail.username}")
	private String fromEmailAddress;

	@Value("${account.verification.url}")
	private String accountVerificationUrl;

	private JavaMailSender javaMailSender;

	public EmailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	@RabbitListener(queues = {"${rabbitmq.email.queue.name}"})
	public void sendMessage(Map<String, String> message) {
		String recipientEmail = message.get("email");
		String token = message.get("token");
		String completeAccountVerificationUrl = accountVerificationUrl.concat(token);
		String emailContent = new StringBuffer("Thank you for registering for the National Entrance Exam for Commerce.")
				.append("\n\nPlease click the link below to verify your account:\n")
				.append(completeAccountVerificationUrl).toString();

		SimpleMailMessage email = new SimpleMailMessage();
		email.setFrom(fromEmailAddress);
		email.setTo(recipientEmail);
		email.setSubject("NEEC Account Verification");
		email.setText(emailContent);
		javaMailSender.send(email);
    }
}
