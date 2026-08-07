package com.api.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import org.testng.annotations.Test;

import com.api.base.BaseTest;
import com.api.endpoints.JiraEndpoints;

public class GetIssueTest extends BaseTest {

	private static final String REQUESTED_FIELDS = "summary,status,assignee,reporter";

	@Test
	public void issueIsReturnedWithRequestedFields() {
		given()
			.spec(request)
			.pathParam("issueKey", issueKey)
			.queryParam("fields", REQUESTED_FIELDS)
		.when()
			.get(JiraEndpoints.ISSUE)
		.then()
			.statusCode(200)
			.body("key", equalTo(issueKey))
			.body("fields.summary", notNullValue())
			.body("fields.status.name", notNullValue())
			// reporter is always set, assignee is not - an unassigned issue returns null there
			.body("fields.reporter.displayName", notNullValue());
	}

	// Jira only serialises the fields named in the query string, so anything else
	// should be missing from the response rather than returned empty.
	@Test
	public void fieldsNotRequestedAreLeftOut() {
		given()
			.spec(request)
			.pathParam("issueKey", issueKey)
			.queryParam("fields", REQUESTED_FIELDS)
		.when()
			.get(JiraEndpoints.ISSUE)
		.then()
			.statusCode(200)
			.body("fields.description", nullValue())
			.body("fields.priority", nullValue());
	}

	@Test
	public void unknownIssueReturnsNotFound() {
		given()
			.spec(request)
			.pathParam("issueKey", "OF-999999")
		.when()
			.get(JiraEndpoints.ISSUE)
		.then()
			.statusCode(404);
	}

	@Test
	public void issueIsReturnedWithinTimeLimit() {
		given()
			.spec(request)
			.pathParam("issueKey", issueKey)
			.queryParam("fields", REQUESTED_FIELDS)
		.when()
			.get(JiraEndpoints.ISSUE)
		.then()
			.statusCode(200)
			.time(lessThan(5000L));
	}
}
