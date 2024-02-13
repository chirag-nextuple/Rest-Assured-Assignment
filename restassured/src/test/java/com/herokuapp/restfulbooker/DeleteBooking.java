package com.herokuapp.restfulbooker;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class DeleteBooking extends BaseTest {

	@Test
	public void deleteBookingTest() {

		Response responseCreate = createBooking();
		responseCreate.print();

		int bookingid = responseCreate.jsonPath().getInt("bookingid");

		Response responseDelete = RestAssured.given(spec).auth().preemptive().basic("admin", "password123")
				.delete("/booking/" + bookingid);
		responseDelete.print();

		Assert.assertEquals(responseDelete.getStatusCode(), 201, "Status code should be 201, but it isn't.");

		Response responseGet = RestAssured.get("https://restful-booker.herokuapp.com/booking/" + bookingid);
		responseGet.print();

		Assert.assertEquals(responseGet.getBody().asString(), "Not Found", "Body should be 'Not Found', but it's not.");

	}
}
