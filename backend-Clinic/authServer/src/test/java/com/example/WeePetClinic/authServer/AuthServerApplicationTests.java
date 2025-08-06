package com.example.WeePetClinic.authServer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
    "eureka.client.register-with-eureka=false",
    "eureka.client.fetch-registry=false"
})
class AuthServerApplicationTests {

	@Test
	void contextLoads() {
		// Test that the Eureka Server context loads successfully
	}

	@Test
	void eurekaServerShouldStart() {
		// Test that Eureka Server starts without errors
	}
}
