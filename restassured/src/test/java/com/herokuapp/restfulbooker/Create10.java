package com.herokuapp.restfulbooker;

import static io.restassured.RestAssured.given;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;

public class Create10 {

	private int statusCode;
	private int bookingId;

	@Test
	public void testCreateBookingAndRetrieveById() throws FileNotFoundException {
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("DATA.json");
		if (inputStream == null) {
			throw new FileNotFoundException("The file DATA.json was not found in the classpath");
		}

		Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
		String jsonContent = scanner.hasNext() ? scanner.next() : "";
		scanner.close();

		JSONArray data = new JSONArray(jsonContent);

		for (int i = 0; i < data.length(); i++) {
			JSONObject booking = data.getJSONObject(i);

			Response res = given().contentType("application/json").body(booking.toString()).when()
					.post("https://restful-booker.herokuapp.com/booking");

			statusCode = res.getStatusCode();
			Assert.assertEquals(statusCode, 200, "Status code not 200");

			bookingId = res.jsonPath().getInt("bookingid");
			System.out.println("Extracted booking ID: " + bookingId);

			res.prettyPrint();

			Response getRes = given().contentType("application/json").when()
					.get("https://restful-booker.herokuapp.com/booking/" + bookingId);

			statusCode = getRes.getStatusCode();
			Assert.assertEquals(statusCode, 200, "The status code is okay.");

			getRes.prettyPrint();
		}
	}
}