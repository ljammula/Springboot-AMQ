package com.example.AmqDemo;

import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class Sender {
	
	@Autowired
	@Qualifier("JmsTemplateQueue")
	JmsTemplate jmsTemplate1;
	
	@Autowired
	@Qualifier("JmsTemplateTopic")
	JmsTemplate jmsTemplate2;
	
	@Autowired
	@Qualifier("JmsDurableTemplateTopic")
	JmsTemplate jmsTemplate3;
	
	public void sendToQueue(String queue, String message) throws JMSException {
		// Send a message
        System.out.println("Sending a message = " + message);
        jmsTemplate1.convertAndSend(queue, message);
	}
	
	public void sendToTopic(String topic, String message) throws JMSException {
		// Send a message
        System.out.println("Sending a message = " + message);
        jmsTemplate2.convertAndSend(topic, message);
	}
	
	public void sendToDurableTopic(String topic, String message) throws JMSException {
		// Send a message
        System.out.println("Sending a message = " + message);
        jmsTemplate3.convertAndSend(topic, message);
	}

}
