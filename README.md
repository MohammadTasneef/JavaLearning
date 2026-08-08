# Java Test Automation Framework

Test automation work built around a real problem: executing a release cycle's manual test
cases in **Zephyr for Jira** and getting the results back into the sheet the testers work in.

Three projects live here.

| Project | What it is | Stack |
| --- | --- | --- |
| [`jira-test-execution`](jira-test-execution) | UI automation that drives a Zephyr release cycle end to end | Selenium 4, TestNG, Google Sheets API, ExtentReports, Apache POI |
| [`rest-api-automation`](rest-api-automation) | API tests against the Jira Cloud REST API | REST Assured, TestNG |
| [`core-java`](core-java) | Core Java exercises the frameworks build on | Java 17 |

Everything targets **Java 17** and builds with Maven.

---

## jira-test-execution

The main piece of work.

A release cycle in Zephyr can hold a few hundred manual test cases. Executing one meant a
tester opening each case, marking every step pass or fail, attaching the order or defect id,
setting an overall status, and then copying all of it back into a shared Google Sheet by hand.

This automates that loop:

1. Logs in through SSO and opens the Zephyr test cycles tab
2. Walks the cycle tree - year, then cycle, then date - to reach the right execution
3. Reads the test cases to run straight from the Google Sheet
4. For each case: finds it across the paged grid, marks each step, links the order or defect id,
   sets the overall execution status
5. Writes every result back to the sheet in a single batch call

Cases already marked by a tester are skipped rather than overwritten, so a rerun after a
partial failure picks up where it stopped.

### How it is put together

```
src/test/java/com/automation/
  pages/     BasePage plus one page object per screen, using PageFactory
  tests/     the suite and its data providers
  utils/     config, browser, Google Sheets, JSON and Excel readers, reporting
```

Page objects hold the locators and the interactions; the test class holds the flow. `BasePage`
carries the driver, the wait, and the JavaScript click and scroll helpers that every page needs.

Nothing sensitive is committed. `ConfigReader` reads `config.properties` and lets an
environment variable of the same name override any value, so credentials and the service
account path stay out of the repo.

### The data layer, three times over

The data source changed twice as the problem got better understood. Each version is still on
its own branch:

| Version | Source | Why it changed | Branch |
| --- | --- | --- | --- |
| v1 | Excel via Apache POI | Only worked on one machine, and the file went stale | [`archive/v1-excel-datasource`](../../tree/archive/v1-excel-datasource) |
| v2 | JSON file | Easier to edit, still needed manual copying | [`archive/v2-json-datasource`](../../tree/archive/v2-json-datasource) |
| v3 | Google Sheets API | The testers already worked in a shared sheet | `main` |

Reading from and writing back to the sheet is what removed the manual copy step, which was the
slowest part of the whole job.

---

## rest-api-automation

REST Assured tests against the Jira Cloud REST API v3 issue endpoint. A shared request spec
holds the base URI, preemptive basic auth and headers; endpoint paths sit in one place; tests
cover the fields returned, that unrequested fields are omitted, a 404, a response time budget,
and that an unauthenticated request is refused.

Tests skip with a message when credentials are not configured, so a fresh clone does not
report false failures.

---

## core-java

Arrays, strings and object orientation - the groundwork the frameworks rely on. Each class has
its own `main` and runs standalone.

---

## Running

Each project builds on its own:

```bash
cd jira-test-execution && mvn clean test      # needs Chrome, Jira access and a Google service account
cd rest-api-automation && mvn clean test      # needs a Jira API token
cd core-java && mvn clean compile
```

Setup for each is in that project's own README.

## Requirements

Java 17, Maven 3.8+, Chrome for the UI tests.
