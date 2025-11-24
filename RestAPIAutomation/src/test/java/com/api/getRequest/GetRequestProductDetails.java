package com.api.getRequest;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GetRequestProductDetails {
  @Test
  public void GetRequest() {
	  
	  RestAssured.baseURI="https://telus-cio.atlassian.net/rest/api/3/issue/OF-2711?fields=summary,status,assignee,reporter";
	  RequestSpecification request=RestAssured.given();
	  
	 Response response= request.request(Method.GET);
	  System.out.println(response.asPrettyString());
	  System.out.println("Status Code="+response.statusCode());
	  
  }
}
