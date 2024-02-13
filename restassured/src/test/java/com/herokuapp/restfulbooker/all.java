package com.herokuapp.restfulbooker;

import static io.restassured.RestAssured.given;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.response.Response;

public class all {

	private int bookingId;
	private String firstname;
	private String lastname;

	@Test
	public void createBooking() {
		// Create JSON data for booking
		JSONObject bookingData = new JSONObject();
		bookingData.put("firstname", "Chirag");
		bookingData.put("lastname", "Sharda");
		bookingData.put("totalprice", 120);
		bookingData.put("depositpaid", true);
		JSONObject bookingDates = new JSONObject();
		bookingDates.put("checkin", "2024-02-02");
		bookingDates.put("checkout", "2024-03-02");
		bookingData.put("bookingdates", bookingDates);
		bookingData.put("additionalneeds", "Breakfast");

		Response response = given().contentType("application/json").body(bookingData.toString()).when()
				.post("https://restful-booker.herokuapp.com/booking");

		response.prettyPrint();

		bookingId = response.jsonPath().getInt("bookingid");
		System.out.println("bookingID is: " + bookingId);
		firstname = response.jsonPath().getString("booking.firstname");
		lastname = response.jsonPath().getString("booking.lastname");

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

	@Test(dependsOnMethods = { "createBooking" })
	public void getBookingIDs_using_names() {

		Response response = given().queryParam("firstname", firstname).queryParam("lastname", lastname).when()
				.get("https://restful-booker.herokuapp.com/booking");

		response.prettyPrint();

		Assert.assertEquals(response.getStatusCode(), 200, "Failed to retrieve booking by first name and last name");
	}

	@Test(dependsOnMethods = { "createBooking" })
	public void getBookingById() {

		Response response = given().when().get("https://restful-booker.herokuapp.com/booking/" + bookingId);

		response.prettyPrint();

		Assert.assertEquals(response.getStatusCode(), 200, "Failed to retrieve booking by ID");

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

	@Test(dependsOnMethods = { "createBooking" })
	public void updateBooking() {

		JSONObject updateData = new JSONObject();
		updateData.put("firstname", "Pablo");
		updateData.put("lastname", "Gavi");
		updateData.put("totalprice", 120);
		updateData.put("depositpaid", true);
		JSONObject bookingDates = new JSONObject();
		bookingDates.put("checkin", "2024-02-02");
		bookingDates.put("checkout", "2024-03-02");
		updateData.put("bookingdates", bookingDates);
		updateData.put("additionalneeds", "Breakfast");

		Response response = given().contentType("application/json").body(updateData.toString()).when()
				.post("https://restful-booker.herokuapp.com/booking");

		response.prettyPrint();

		bookingId = response.jsonPath().getInt("bookingid");
		System.out.println("bookingID is: " + bookingId);
		firstname = response.jsonPath().getString("booking.firstname");
		lastname = response.jsonPath().getString("booking.lastname");

		Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200, but it's not");
		SoftAssert softAssert = new SoftAssert();
		String actualFirstName = response.jsonPath().getString("booking.firstname");
		softAssert.assertEquals(actualFirstName, "Pablo", "firstname in response does not match");

		String actualLastName = response.jsonPath().getString("booking.lastname");
		softAssert.assertEquals(actualLastName, "Gavi", "lastname in response does not match");

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

	@Test(dependsOnMethods = { "createBooking" })
	public void partiallyUpdateBooking() {

		JSONObject partialUpdateData = new JSONObject();
		partialUpdateData.put("firstname", "Chirag");
		partialUpdateData.put("lastname", "Sharda");

		Response response = given().contentType("application/json").auth().preemptive().basic("admin", "password123")
				.body(partialUpdateData.toString()).when()
				.patch("https://restful-booker.herokuapp.com/booking/" + bookingId);

		response.prettyPrint();

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

	@Test(dependsOnMethods = { "createBooking" })
	public void deleteBooking() {

		Response response = given().auth().preemptive().basic("admin", "password123").when()
				.delete("https://restful-booker.herokuapp.com/booking/" + bookingId);

		response.prettyPrint();

		Assert.assertEquals(response.getStatusCode(), 201, "Failed to delete booking");
		Assert.assertEquals(response.asString(), "Created");

		Response after_del = given().when().get("https://restful-booker.herokuapp.com/booking/" + bookingId);
		Assert.assertEquals(after_del.getStatusCode(), 404, "Found even after del request");

	}
}