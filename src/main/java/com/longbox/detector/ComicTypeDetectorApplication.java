package com.longbox.detector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.longbox.detector.messaging.DetectorMessageReciever;
import com.longbox.detector.service.ComicTypeDetectorService;

@SpringBootApplication
public class ComicTypeDetectorApplication {

	@Autowired
	ComicTypeDetectorService service;

	static final String directExchangeName = "comic-exchange";

	static final String queueName = "comic.found";
	static final String rarQueueName = "comic.rar";
	static final String zipQueueName = "comic.zip";
	static final String otherQueueName = "comic.other";

	@Bean
	Queue queue() {
		return new Queue(queueName, false);
	}

	@Bean
	Queue rarQueue() {
		return new Queue(rarQueueName, false);
	}

	@Bean
	Queue zipQueue() {
		return new Queue(zipQueueName, false);
	}

	@Bean
	Queue otherQueue() {
		return new Queue(otherQueueName, false);
	}

	@Bean
	DirectExchange exchange() {
		return new DirectExchange(directExchangeName);
	}

	@Bean
	Binding binding(Queue queue, DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with("found");
	}

	@Bean
	Binding rarBinding(Queue rarQueue, DirectExchange exchange) {
		return BindingBuilder.bind(rarQueue).to(exchange).with("rar");
	}

	@Bean
	Binding zipBinding(Queue zipQueue, DirectExchange exchange) {
		return BindingBuilder.bind(zipQueue).to(exchange).with("zip");
	}

	@Bean
	Binding otherBinding(Queue otherQueue, DirectExchange exchange) {
		return BindingBuilder.bind(otherQueue).to(exchange).with("other");
	}

	@Bean
	MessageListenerAdapter listenerAdapter(DetectorMessageReciever receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}

	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
			MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(queueName);
		container.setMessageListener(listenerAdapter);
		return container;
	}

	private static Logger LOG = LoggerFactory.getLogger(ComicTypeDetectorApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ComicTypeDetectorApplication.class, args);
	}
	/*
	 * @Override public void run(String... args) throws Exception {
	 * service.detect(); }
	 */

}
