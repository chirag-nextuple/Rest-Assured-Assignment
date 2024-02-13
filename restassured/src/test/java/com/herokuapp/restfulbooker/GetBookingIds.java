package com.herokuapp.restfulbooker;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class GetBookingIds extends BaseTest {

	@Test
	public void getBookingIds() {
		spec.queryParam("firstname", "Chirag");
		spec.queryParam("lastname", "Sharda");
		
		Response response = RestAssured.given(spec).get("/booking");
		response.print();

		Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200, but it's not");

		List<Integer> bookingIds = response.jsonPath().getList("bookingid");
		Assert.assertFalse(bookingIds.isEmpty(), "No booking in record");
	}

}
