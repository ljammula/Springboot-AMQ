package com.example.AmqDemo;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.AmqDemo.Controller.HelloController;

@RunWith(MockitoJUnitRunner.class)
public class HelloControllerTest {

	@Mock
	private Sender sender;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		HelloController controller = new HelloController();
		ReflectionTestUtils.setField(controller, "sender", sender);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void negativeCountReturnsBadRequestWithoutSendingMessages() throws Exception {
		mockMvc.perform(get("/sendMessages").param("count", "-1"))
				.andExpect(status().isBadRequest());

		verify(sender, never()).sendToQueue(anyString(), anyString());
		verify(sender, never()).sendToTopic(anyString(), anyString());
		verify(sender, never()).sendToDurableTopic(anyString(), anyString());
		verifyNoMoreInteractions(sender);
	}

	@Test
	public void zeroCountReturnsSuccessWithoutSendingMessages() throws Exception {
		mockMvc.perform(get("/sendMessages").param("count", "0"))
				.andExpect(status().isOk())
				.andExpect(content().string("Successfully posted messages"));

		verify(sender, never()).sendToQueue(anyString(), anyString());
		verify(sender, never()).sendToTopic(anyString(), anyString());
		verify(sender, never()).sendToDurableTopic(anyString(), anyString());
		verifyNoMoreInteractions(sender);
	}

	@Test
	public void positiveCountSendsEachMessageForEveryIteration() throws Exception {
		int count = 2;

		mockMvc.perform(get("/sendMessages").param("count", String.valueOf(count)))
				.andExpect(status().isOk())
				.andExpect(content().string("Successfully posted messages"));

		verify(sender, times(count)).sendToQueue("inbound.queue", "Hello Queue ");
		verify(sender, times(count)).sendToQueue("inbound.queue.2", "Hello Queue ");
		verify(sender, times(count)).sendToTopic("inbound.topic", "Hello Topic");
		verify(sender, times(count)).sendToDurableTopic("inbound.durable.topic", "Hello Durable Topic");
		verifyNoMoreInteractions(sender);
	}
}
