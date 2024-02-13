package com.herokuapp.restfulbooker;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PartialUpdateBooking extends BaseTest {

	@Test
	public void partialUpdateBookingTest() {
		// Create booking
		Response responseCreate = createBooking();
		responseCreate.print();

		// Get bookingId of new booking
		int bookingid = responseCreate.jsonPath().getInt("bookingid");

		// Create JSON body
		JSONObject body = new JSONObject();
		body.put("firstname", "Chirag");
		body.put("lastname", "Sharda");

		JSONObject bookingdates = new JSONObject();
		bookingdates.put("checkin", "2025-02-02");
		bookingdates.put("checkout", "2025-03-02");

		body.put("bookingdates", bookingdates);

		Response responseUpdate = RestAssured.given(spec).auth().preemptive().basic("admin", "password123")
				.contentType(ContentType.JSON).body(body.toString()).patch("/booking/" + bookingid);
		responseUpdate.print();

		Assert.assertEquals(responseUpdate.getStatusCode(), 200, "Status code should be 200, but it's not.");

		SoftAssert softAssert = new SoftAssert();
		String actualFirstName = responseUpdate.jsonPath().getString("booking.firstname");
		softAssert.assertEquals(actualFirstName, "Chirag", "firstname in response is not expected");

		String actualLastName = responseUpdate.jsonPath().getString("booking.lastname");
		softAssert.assertEquals(actualLastName, "Sharda", "lastname in response is not expected");

		int price = responseUpdate.jsonPath().getInt("booking.totalprice");
		softAssert.assertEquals(price, 120, "totalprice in response is not expected");

		boolean depositpaid = responseUpdate.jsonPath().getBoolean("booking.depositpaid");
		softAssert.assertTrue(depositpaid, "depositpaid should be false, but it's not");

		String actualCheckin = responseUpdate.jsonPath().getString("booking.bookingdates.checkin");
		softAssert.assertEquals(actualCheckin, "2024-02-02", "checkin in response is not expected");

		String actualCheckout = responseUpdate.jsonPath().getString("booking.bookingdates.checkout");
		softAssert.assertEquals(actualCheckout, "2024-03-02", "checkout in response is not expected");

		String actualAdditionalneeds = responseUpdate.jsonPath().getString("booking.additionalneeds");
		softAssert.assertEquals(actualAdditionalneeds, "Breakfast", "additionalneeds in response is not expected");

		softAssert.assertAll();

	}
}