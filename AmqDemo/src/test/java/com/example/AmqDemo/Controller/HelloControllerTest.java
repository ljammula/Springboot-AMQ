package com.example.AmqDemo.Controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.AmqDemo.Sender;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class HelloControllerTest {
	private Sender sender;
	private MockMvc mockMvc;

	@Before
	public void setUp() {
		sender = mock(Sender.class);
		HelloController controller = new HelloController();
		controller.sender = sender;
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void rejectsCountAboveMaximumBeforeSendingMessages() throws Exception {
		mockMvc.perform(get("/sendMessages").param("count", "1001"))
				.andExpect(status().isBadRequest());

		verifyZeroInteractions(sender);
	}

	@Test
	public void sendsExactlyTheMaximumCount() throws Exception {
		mockMvc.perform(get("/sendMessages").param("count", "1000"))
				.andExpect(status().isOk())
				.andExpect(content().string("Successfully posted messages"));

		verify(sender, times(1000)).sendToQueue("inbound.queue", "Hello Queue ");
	}

	@Test
	public void usesTheDefaultCountWhenCountIsOmitted() throws Exception {
		mockMvc.perform(get("/sendMessages"))
				.andExpect(status().isOk());

		verify(sender, times(10)).sendToQueue("inbound.queue", "Hello Queue ");
	}

	@Test
	public void rejectsMalformedCount() throws Exception {
		mockMvc.perform(get("/sendMessages").param("count", "abc"))
				.andExpect(status().isBadRequest());

		verifyZeroInteractions(sender);
	}
}
