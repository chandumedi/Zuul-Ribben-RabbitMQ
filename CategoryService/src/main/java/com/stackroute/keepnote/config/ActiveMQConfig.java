package com.stackroute.keepnote.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class ActiveMQConfig {
	 static final String topicExchangeName = "category.delete";

	    static final String queueName = "delete.queue";

		private  static final String routingkey="category.routingkey";

		@Bean
		Queue queue() {
			return new Queue(queueName, false);
		}

		@Bean
		DirectExchange exchange() {
			return new DirectExchange(topicExchangeName);
		}

		@Bean
		Binding binding(Queue queue, DirectExchange exchange) {
			return BindingBuilder.bind(queue).to(exchange).with(routingkey);
		}

		@Bean
		public MessageConverter jsonMessageConverter() {
			return new Jackson2JsonMessageConverter();
		}

		
		@Bean
		public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
			final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
			rabbitTemplate.setMessageConverter(jsonMessageConverter());
			return rabbitTemplate;
		}
}
