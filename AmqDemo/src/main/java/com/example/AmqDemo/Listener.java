package com.example.AmqDemo;

import javax.jms.JMSException;
import javax.jms.Message;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Listener {

	@JmsListener(destination = "inbound.queue", containerFactory = "myQueueFactory")
	public void receiveMessage(final Message jsonMessage) throws JMSException {
		System.out.println("Received message " + jsonMessage);
	}
	
	@JmsListener(destination = "inbound.queue.2", containerFactory = "myQueueFactory")
	public void receiveMessage2(final Message jsonMessage) throws JMSException {
		System.out.println("Received message " + jsonMessage);
	}

	@JmsListener(destination = "inbound.topic", containerFactory = "myTopicFactory")
	public void receiveMessageFromTopic(final Message jsonMessage) throws JMSException {
		System.out.println("Received message " + jsonMessage);
	}

	@JmsListener(destination = "inbound.durable.topic", subscription = "dummy_subscription", containerFactory = "myDurableTopicFactory")
	public void receiveMessageFromDurableTopic(final Message jsonMessage) throws JMSException {
		System.out.println("Received message " + jsonMessage);
	}

}