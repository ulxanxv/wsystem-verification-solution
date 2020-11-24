package ru.monetarys;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MonetarysApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	void testIsUnauthorized() throws Exception {
		mockMvc.perform(get("http://localhost:8080/"))
				.andDo(print())
				.andExpect(status().isUnauthorized());
	}

//	@Test
//	void testIsAuthorized() throws Exception {
//		mockMvc.perform(get("http://localhost:8080/").with(httpBasic("Ulxanxv", "1234")))
//				.andDo(print())
//				.andExpect(status().is2xxSuccessful());
//	}

}
