package com.api.base;

import org.testng.SkipException;
import org.testng.annotations.BeforeClass;

import com.api.config.ConfigReader;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

/**
 * Builds the request spec every authenticated test reuses, so base URI, auth and
 * headers are configured in one place instead of in each test.
 */
public class BaseTest {

	protected RequestSpecification request;
	protected String issueKey;

	@BeforeClass
	public void buildRequestSpec() {
		String baseUri = ConfigReader.get("base.uri");
		String email = ConfigReader.get("jira.email");
		String apiToken = ConfigReader.get("jira.api.token");
		issueKey = ConfigReader.get("issue.key");

		// Skip rather than fail, otherwise a fresh clone reports errors that are really
		// just missing local setup.
		if (baseUri.isEmpty() || baseUri.contains("your-site") || email.isEmpty() || apiToken.isEmpty()) {
			throw new SkipException(
					"Jira site or credentials not configured - see src/test/resources/config.properties");
		}

		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

		request = new RequestSpecBuilder()
				.setBaseUri(baseUri)
				.setAuth(RestAssured.preemptive().basic(email, apiToken))
				.setAccept(ContentType.JSON)
				.build();
	}
}
