# REST API Automation

API tests for the Jira Cloud REST API v3 issue endpoint, written with REST Assured and TestNG.

## Layout

```
src/test/java/com/api/
  base/BaseTest.java          shared request spec - base URI, preemptive basic auth, JSON accept header
  config/ConfigReader.java    reads config.properties, environment variables override it
  endpoints/JiraEndpoints.java endpoint paths
  tests/                      the tests
src/test/resources/
  config.properties           site, issue key, credentials
testng.xml                    suite definition
```

## Setup

Get an API token from https://id.atlassian.com/manage-profile/security/api-tokens

Then either edit `src/test/resources/config.properties`:

```properties
base.uri=https://your-site.atlassian.net
issue.key=OF-2711
jira.email=you@example.com
jira.api.token=your-token
```

Or leave the file alone and export environment variables instead, which is preferable
because the token never touches the working tree:

```bash
export BASE_URI=https://your-site.atlassian.net
export JIRA_EMAIL=you@example.com
export JIRA_API_TOKEN=your-token
```

Environment variables take priority over the properties file. Any property key maps to an
environment variable by upper-casing it and replacing dots with underscores.

## Running

```bash
mvn clean test
```

Runs `testng.xml`. If the site or credentials are not configured the tests skip with a message
rather than failing, so a fresh clone does not report false errors.

A single class or a single method, which bypasses the suite file:

```bash
mvn clean test -Dtest=GetIssueTest
mvn clean test -Dtest=GetIssueTest#unknownIssueReturnsNotFound
```

Reports land in `target/surefire-reports/`.

## Tests

| Test | Checks |
| --- | --- |
| `issueIsReturnedWithRequestedFields` | 200, issue key matches, summary / status / reporter populated |
| `fieldsNotRequestedAreLeftOut` | fields absent from the `fields` query param are not returned |
| `unknownIssueReturnsNotFound` | 404 for an issue key that does not exist |
| `issueIsReturnedWithinTimeLimit` | response under 5s |
| `requestWithoutCredentialsIsRejected` | anonymous request is refused |

Request and response are logged only when an assertion fails, so passing runs stay quiet.

## Requirements

Java 17 and Maven 3.8+.
