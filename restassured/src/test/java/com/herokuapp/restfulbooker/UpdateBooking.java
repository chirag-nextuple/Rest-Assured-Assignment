package com.herokuapp.restfulbooker;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UpdateBooking extends BaseTest {

	@Test
	public void updateBookingTest() {
		Response responseCreate = createBooking();
		responseCreate.print();
		int bookingid = responseCreate.jsonPath().getInt("bookingid");

		JSONObject body = new JSONObject();
		body.put("firstname", "Lamine");
		body.put("lastname", "Yamal");
		body.put("totalprice", 120);
		body.put("depositpaid", true);

		JSONObject bookingdates = new JSONObject();
		bookingdates.put("checkin", "2024-03-02");
		bookingdates.put("checkout", "2024-04-02");
		body.put("bookingdates", bookingdates);
		body.put("additionalneeds", "Breakfast");

		Response responseUpdate = RestAssured.given(spec).auth().preemptive().basic("admin", "password123")
				.contentType(ContentType.JSON).body(body.toString()).put("/booking/" + bookingid);
		responseUpdate.print();

		Assert.assertEquals(responseUpdate.getStatusCode(), 200, "Status code should be 200, but it's not.");

		// Verify All fields
		Assert.assertEquals(responseUpdate.getStatusCode(), 200, "Status code should be 200, but it's not");

		SoftAssert softAssert = new SoftAssert();
		String actualFirstName = responseUpdate.jsonPath().getString("booking.firstname");
		softAssert.assertEquals(actualFirstName, "Lamine", "firstname in response does not match");

		String actualLastName = responseUpdate.jsonPath().getString("booking.lastname");
		softAssert.assertEquals(actualLastName, "Yamal", "lastname in response does not match");

		int price = responseUpdate.jsonPath().getInt("booking.totalprice");
		softAssert.assertEquals(price, 120, "totalprice in response does not match");

		boolean depositpaid = responseUpdate.jsonPath().getBoolean("booking.depositpaid");
		softAssert.assertTrue(depositpaid, "depositpaid is true, but shows false");

		String actualCheckin = responseUpdate.jsonPath().getString("booking.bookingdates.checkin");
		softAssert.assertEquals(actualCheckin, "2024-03-02", "checkin in response does not match");

		String actualCheckout = responseUpdate.jsonPath().getString("booking.bookingdates.checkout");
		softAssert.assertEquals(actualCheckout, "2024-04-02", "checkout in response does not match");

		String actualAdditionalneeds = responseUpdate.jsonPath().getString("booking.additionalneeds");
		softAssert.assertEquals(actualAdditionalneeds, "Breakfast", "additionalneeds in response does not match");

		softAssert.assertAll();
	}
}
