package com.stackroute.keepnote.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stackroute.keepnote.model.Category;
@Component
public class RabbitMQSender {
	@Autowired
	private AmqpTemplate rabbitTemplate;
	static final String topicExchangeName = "category.delete";

    static final String queueName = "delete.queue";

	
	private String routingkey="category.routingkey";	
	
	public void send(Category category) {
		rabbitTemplate.convertAndSend(topicExchangeName, routingkey, category);
		System.out.println("Send msg = " + category);
	    
	}
}
