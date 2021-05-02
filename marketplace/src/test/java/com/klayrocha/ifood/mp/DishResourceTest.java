package com.klayrocha.ifood.mp;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class DishResourceTest {

	@Test
	public void testDihsesEndpoint() {

		// Aqui dará erro se não utilizar testContainers

		String body = given()
				.when().get("/dishes")
				.then()
				.statusCode(200).extract().asString();
		System.out.println(body);
	}
}
