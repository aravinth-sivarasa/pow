package io.pow.kafka;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class KafkaControllerTests {
    
    @Autowired
	private MockMvc mockMvc;
    @Test
    void testKafkaApi() throws Exception {
		this.mockMvc.perform(
                post("/pow-res1").content("test value"))
            .andDo(print())
            .andExpect(status().isOk())
			.andExpect(content().string(containsString("test value")));
    }
}
