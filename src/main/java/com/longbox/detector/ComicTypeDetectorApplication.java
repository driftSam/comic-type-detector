package com.longbox.detector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.longbox.detector.messaging.DetectorMessageReciever;
import com.longbox.detector.service.ComicTypeDetectorService;

@SpringBootApplication
public class ComicTypeDetectorApplication implements CommandLineRunner {

	@Autowired
	ComicTypeDetectorService service;

	static final String topicExchangeName = "comic-exchange";

	static final String queueName = "comic.detect";

	@Bean
	Queue queue() {
		return new Queue(queueName, false);
	}

	@Bean
	TopicExchange exchange() {
		return new TopicExchange(topicExchangeName);
	}

	@Bean
	Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with("comic.detect.#");
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

	@Override
	public void run(String... args) throws Exception {
		service.detect();
	}

}
