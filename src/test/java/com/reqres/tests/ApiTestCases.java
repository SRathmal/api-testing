package com.reqres.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class ApiTestCases {

	@BeforeClass
	public void setup() {
	    RestAssured.baseURI = "https://reqres.in/api";
	}

	@Test
	public void testGetUserList() {
	    Response response = RestAssured.given()
	        .queryParam("page", 2)
	        .when()
	        .get("/users")
	        .then()
	        .contentType(ContentType.JSON)
	        .extract().response();

	    Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
	    int userCount = response.jsonPath().getList("data").size();
	    Assert.assertTrue(userCount > 0, "User count should be greater than 0");
	}


	@Test
	public void testCreateUser() {
	    JSONObject requestParams = new JSONObject();
	    requestParams.put("name", "John");
	    requestParams.put("job", "Developer");

	    Response response = RestAssured.given()
	        .contentType(ContentType.JSON)
	        .body(requestParams.toString())
	        .when()
	        .post("/users")
	        .then()
	        .contentType(ContentType.JSON)
	        .extract().response();

	    Assert.assertEquals(response.getStatusCode(), 201, "Expected status code 201");
	    Assert.assertEquals(response.jsonPath().getString("name"), "John", "Name should be John");
	    Assert.assertEquals(response.jsonPath().getString("job"), "Developer", "Job should be Developer");
	}
	
	@Test
	public void testUpdateUser() {
	    JSONObject requestParams = new JSONObject();
	    requestParams.put("name", "Jane");
	    requestParams.put("job", "Manager");

	    Response response = RestAssured.given()
	        .contentType(ContentType.JSON)
	        .body(requestParams.toString())
	        .when()
	        .put("/users/2")
	        .then()
	        .contentType(ContentType.JSON)
	        .extract().response();

	    Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
	    Assert.assertEquals(response.jsonPath().getString("name"), "Jane", "Name should be Jane");
	    Assert.assertEquals(response.jsonPath().getString("job"), "Manager", "Job should be Manager");
	}



    
}

