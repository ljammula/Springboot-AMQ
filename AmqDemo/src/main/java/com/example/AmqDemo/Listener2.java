package com.example.AmqDemo;

import javax.jms.JMSException;
import javax.jms.Message;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Listener2 {

	@JmsListener(destination = "inbound.topic", containerFactory = "myTopicFactory", subscription="topic-listener-2")
	public void receiveMessage(final Message jsonMessage) throws JMSException {
		System.out.println("Received message in Listener-2" + jsonMessage);
	}
	
	@JmsListener(destination = "inbound.queue.2", containerFactory = "myQueueFactory")
	public void receiveMessage2(final Message jsonMessage) throws JMSException {
		System.out.println("Received message " + jsonMessage);
	}

}