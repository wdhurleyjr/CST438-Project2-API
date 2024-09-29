package com.project2.Project2;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest(classes = Project2Application.class)
public class Project2ApplicationTests {

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry registry) {
		registry.add("PORT", () -> "8080");
	}

	@Test
	void contextLoads() {
		// This test will simply check if the Spring application context loads successfully.
	}
}



