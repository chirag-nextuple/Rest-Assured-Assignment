package com.herokuapp.restfulbooker;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class GetBooking extends BaseTest {

	@Test
	public void getBookingTest() {


		Response responseCreate = createBooking();
		responseCreate.print();

		spec.pathParam("bookingID", responseCreate.jsonPath().getInt("bookingid"));
		
		Response response = RestAssured.given(spec).get("/booking/{bookingID}");
		response.print();

		Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200, but it's not");

		SoftAssert softAssert = new SoftAssert();

		String actualFirstName = response.jsonPath().getString("firstname");
		softAssert.assertEquals(actualFirstName, "Chirag", "firstname does not match");

		String actualLastName = response.jsonPath().getString("lastname");
		softAssert.assertEquals(actualLastName, "Sharda", "lastname does not match");

		int price = response.jsonPath().getInt("totalprice");
		softAssert.assertEquals(price, 120, "totalprice does not match");

		boolean depositpaid = response.jsonPath().getBoolean("depositpaid");
		softAssert.assertTrue(depositpaid, "depositpaid should be true, but it's not");

		String actualCheckin = response.jsonPath().getString("bookingdates.checkin");
		softAssert.assertEquals(actualCheckin, "2024-02-02", "wrong checkin");

		String actualCheckout = response.jsonPath().getString("bookingdates.checkout");
		softAssert.assertEquals(actualCheckout, "2024-03-02", "wrong checkout");

		String actualAdditionalneeds = response.jsonPath().getString("additionalneeds");
		softAssert.assertEquals(actualAdditionalneeds, "Breakfast", "different additionalneeds");

		softAssert.assertAll();
	}
}