package com.example.AmqDemo.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.AmqDemo.Sender;

import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class HelloController {
	
	@Autowired
	Sender sender;
	
	@RequestMapping(value="/sendMessages", method=RequestMethod.GET)
    public String sendMessages(@RequestParam(value = "count", defaultValue = "10") Integer count) throws JMSException {
		for(int i=0; i<count; i++) {
		sender.sendToQueue("inbound.queue", "Hello Queue ");
		sender.sendToQueue("inbound.queue.2", "Hello Queue ");
		sender.sendToTopic("inbound.topic", "Hello Topic");
		sender.sendToDurableTopic("inbound.durable.topic", "Hello Durable Topic");
		}
        return "Successfully posted messages";
    }

}
