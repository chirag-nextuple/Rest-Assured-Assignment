package com.herokuapp.restfulbooker;

import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseTest {
	protected RequestSpecification spec;

	@BeforeMethod
	public void setUp() {
		spec = new RequestSpecBuilder().setBaseUri("https://restful-booker.herokuapp.com").build();
	}

	protected Response createBooking() {
		JSONObject body = new JSONObject();
		body.put("firstname", "Chirag");
		body.put("lastname", "Sharda");
		body.put("totalprice", 120);
		body.put("depositpaid", true);

		JSONObject bookingdates = new JSONObject();
		bookingdates.put("checkin", "2024-02-02");
		bookingdates.put("checkout", "2024-03-02");
		body.put("bookingdates", bookingdates);
		body.put("additionalneeds", "Breakfast");

		Response response = RestAssured.given(spec).contentType(ContentType.JSON).body(body.toString())
				.post("/booking");
		return response;
	}

}
