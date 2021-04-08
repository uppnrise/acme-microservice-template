package com.upp.microservices.core.review;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReviewServiceApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void contextLoads() {
	}

	@Test
	public void getReviewsByProductId() {
		int productId = 1;

		webTestClient.get()
				.uri("/review?productId=" + productId)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.length()").isEqualTo(3)
				.jsonPath("$[0].productId").isEqualTo(productId);
	}

	@Test
	public void getReviewsMissingParameter() {
		webTestClient.get()
				.uri("/review")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/review")
				.jsonPath("$.message").isEqualTo("Required int parameter 'productId' is not present");
	}

	@Test
	public void getReviewsInvalidParameter() {
		webTestClient.get()
				.uri("/review?productId=no-integer")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/review")
				.jsonPath("$.message").isEqualTo("Type mismatch.");
	}

	@Test
	public void getReviewsNotFound() {
		int productIdNotFound = 213;

		webTestClient.get()
				.uri("/review?productId=" + productIdNotFound)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.length()").isEqualTo(0);
	}

	@Test
	public void getReviewsInvalidParameterNegativeValue() {
		int productIdInvalid = -1;

		webTestClient.get()
				.uri("/review?productId=" + productIdInvalid)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/review")
				.jsonPath("$.message").isEqualTo("Invalid productId: " + productIdInvalid);
	}

}
