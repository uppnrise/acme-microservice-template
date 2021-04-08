package com.upp.microservices.composite.product;

import com.upp.api.core.product.Product;
import com.upp.api.core.recommendation.Recommendation;
import com.upp.api.core.review.Review;
import com.upp.microservices.composite.product.services.ProductCompositeIntegration;
import com.upp.util.exceptions.InvalidInputException;
import com.upp.util.exceptions.NotFoundException;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Collections;

@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductCompositeServiceApplicationTests {
	private static final int PRODUCT_ID_OK = 1;
	private static final int PRODUCT_ID_NOT_FOUND = 2;
	private static final int PRODUCT_ID_INVALID = 3;

	@Autowired
	private WebTestClient webTestClient;

	@Mock
	private ProductCompositeIntegration integration;

	@Before
	public void setUp() {

		Mockito.when(integration.getProduct(PRODUCT_ID_OK)).
				thenReturn(new Product(PRODUCT_ID_OK, "name", 1, "mock-address"));
		Mockito.when(integration.getRecommendations(PRODUCT_ID_OK)).
				thenReturn(Collections.singletonList(new Recommendation(PRODUCT_ID_OK, 1, "author", 1, "content", "mock address")));
		Mockito.when(integration.getReviews(PRODUCT_ID_OK)).
				thenReturn(Collections.singletonList(new Review(PRODUCT_ID_OK, 1, "author", "subject", "content", "mock address")));

		Mockito.when(integration.getProduct(PRODUCT_ID_NOT_FOUND)).thenThrow(new NotFoundException("NOT FOUND: " + PRODUCT_ID_NOT_FOUND));

		Mockito.when(integration.getProduct(PRODUCT_ID_INVALID)).thenThrow(new InvalidInputException("INVALID: " + PRODUCT_ID_INVALID));
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void getProductById() {

		webTestClient.get()
				.uri("/product-composite/" + PRODUCT_ID_OK)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.productId").isEqualTo(PRODUCT_ID_OK)
				.jsonPath("$.recommendations.length()").isEqualTo(1)
				.jsonPath("$.reviews.length()").isEqualTo(1);
	}

	@Test
	public void getProductNotFound() {

		webTestClient.get()
				.uri("/product-composite/" + PRODUCT_ID_NOT_FOUND)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isNotFound()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/product-composite/" + PRODUCT_ID_NOT_FOUND)
				.jsonPath("$.message").isEqualTo("NOT FOUND: " + PRODUCT_ID_NOT_FOUND);
	}

	@Test
	public void getProductInvalidInput() {

		webTestClient.get()
				.uri("/product-composite/" + PRODUCT_ID_INVALID)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/product-composite/" + PRODUCT_ID_INVALID)
				.jsonPath("$.message").isEqualTo("INVALID: " + PRODUCT_ID_INVALID);
	}

}
