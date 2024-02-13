package com.herokuapp.restfulbooker;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.response.Response;

public class CreateBooking extends BaseTest {

	@Test
	public void createBookingTest() {
		Response response = createBooking();
		response.print();

		// Verifications
		Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200, but it's not");
		SoftAssert softAssert = new SoftAssert();
		String actualFirstName = response.jsonPath().getString("booking.firstname");
		softAssert.assertEquals(actualFirstName, "Chirag", "firstname in response does not match");

		String actualLastName = response.jsonPath().getString("booking.lastname");
		softAssert.assertEquals(actualLastName, "Sharda", "lastname in response does not match");

		int price = response.jsonPath().getInt("booking.totalprice");
		softAssert.assertEquals(price, 120, "totalprice in response does not match");

		boolean depositpaid = response.jsonPath().getBoolean("booking.depositpaid");
		softAssert.assertTrue(depositpaid, "depositpaid is true, but shows false");

		String actualCheckin = response.jsonPath().getString("booking.bookingdates.checkin");
		softAssert.assertEquals(actualCheckin, "2024-02-02", "checkin in response does not match");

		String actualCheckout = response.jsonPath().getString("booking.bookingdates.checkout");
		softAssert.assertEquals(actualCheckout, "2024-03-02", "checkout in response does not match");

		String actualAdditionalneeds = response.jsonPath().getString("booking.additionalneeds");
		softAssert.assertEquals(actualAdditionalneeds, "Breakfast", "additionalneeds in response does not match");

		softAssert.assertAll();
	}
	 
	
}
