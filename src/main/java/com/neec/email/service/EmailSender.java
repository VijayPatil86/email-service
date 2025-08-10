package com.neec.email.service;

import java.util.Map;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;;

@Service
public class EmailSender {

	@RabbitListener(queues = {"${rabbitmq.email.queue.name}"})
	public void sendMessage(Map<String, String> message) {
		System.out.println("📩 Received message: " + message);
    }
}
