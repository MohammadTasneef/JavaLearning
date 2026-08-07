package com.api.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;

import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.api.config.ConfigReader;
import com.api.endpoints.JiraEndpoints;

/**
 * Sends the same request with no credentials, to confirm the endpoint is actually protected.
 * Does not extend BaseTest because it must not pick up the auth spec.
 */
public class UnauthenticatedAccessTest {

	private String baseUri;

	@BeforeClass
	public void readBaseUri() {
		baseUri = ConfigReader.get("base.uri");
		if (baseUri.isEmpty() || baseUri.contains("your-site")) {
			throw new SkipException("base.uri not configured - see src/test/resources/config.properties");
		}
	}

	@Test
	public void requestWithoutCredentialsIsRejected() {
		given()
			.baseUri(baseUri)
			.pathParam("issueKey", ConfigReader.get("issue.key"))
		.when()
			.get(JiraEndpoints.ISSUE)
		.then()
			// 401 when anonymous access is switched off site wide, 404 when the project
			// hides its existence from anonymous callers instead. Either one is a pass.
			.statusCode(anyOf(equalTo(401), equalTo(404)));
	}
}
