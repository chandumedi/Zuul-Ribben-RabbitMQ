package com.stackroute.keepnote.config;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;
@Configuration
public class CategoryDeleteListener implements RabbitListenerConfigurer{
	

	  @RabbitListener(queues="delete.queue")
	  public void receive(Object category) {
	   System.out.println("ACTIVEMQ CALLED+++++++++++++++++++++++++++++++>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+category.toString());
	  }
	  
	  
	  
	  @Override
	    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
	        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
	    }
	 
	    @Bean
	    MessageHandlerMethodFactory messageHandlerMethodFactory() {
	        DefaultMessageHandlerMethodFactory messageHandlerMethodFactory = new DefaultMessageHandlerMethodFactory();
	        messageHandlerMethodFactory.setMessageConverter(consumerJackson2MessageConverter());
	        return messageHandlerMethodFactory;
	    }
	 
	    @Bean
	    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
	        return new MappingJackson2MessageConverter();
	    }

}
