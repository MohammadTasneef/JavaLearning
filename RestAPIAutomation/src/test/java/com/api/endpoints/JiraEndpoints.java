package com.api.endpoints;

/**
 * Endpoint paths, kept out of the tests so a Jira API version bump is a one line change here.
 */
public class JiraEndpoints {

	public static final String ISSUE = "/rest/api/3/issue/{issueKey}";

	private JiraEndpoints() {
	}
}
