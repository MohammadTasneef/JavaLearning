# Jira Test Execution Automation

Selenium framework that runs a release cycle's manual test cases in Zephyr for Jira. It logs
in through SSO, opens the right release cycle, marks each test step, links the order or defect
id, and writes the resulting execution status back to the Google Sheet the test cases came
from.

The manual version of this took most of a day per cycle, largely because every result had to
be copied back into the sheet by hand.

## Layout

```
src/test/java/com/automation/
  pages/
    BasePage.java              driver, wait, and the shared JS click / scroll / wait helpers
    JiraLoginPage.java          SSO login
    TestCycleSummaryPage.java   picks year > cycle > date in the cycle tree
    TestExecutionPage.java      finds a test case, marks steps, sets the overall status
  tests/
    JiraTestExecutionTest.java  the suite, plus the two data providers
  utils/
    ConfigReader.java           config.properties with environment variable override
    BrowserFactory.java         starts Chrome and opens the Zephyr tab
    GoogleSheetClient.java      reads test cases, writes statuses back in one batch
    JsonDataReader.java         reads the release cycle from JsonData/testdata.json
    ExcelUtils.java             Excel data source from the first version of this framework
    ExtentReportManager.java    HTML report
src/test/resources/
  config.properties
JsonData/testdata.json          which release cycle to run
ExcelData/Book1.xlsx            sample data for the Excel source
testng.xml
```

## Data sources

The framework has been through three data sources, one per branch in this repo:

| Version | Source | Branch |
| --- | --- | --- |
| v1 | Excel via Apache POI | `jira-testcase-automation` |
| v2 | JSON file | `jira-testcase-automation-legacy` |
| v3 | Google Sheets API | this branch |

Google Sheets won because the manual testers already worked in a shared sheet, so reading the
cases from there and writing results back removed the copy step entirely. `ExcelUtils` is kept
for local runs without sheet access.

## Setup

Nothing sensitive is committed. Set these in your environment rather than editing the file:

```bash
export JIRA_USERNAME=your-sso-user
export JIRA_PASSWORD=your-sso-password
export SPREADSHEET_ID=the-sheet-id
export GOOGLE_CREDENTIALS_PATH=/path/outside/this/repo/service-account.json
```

Then set your Jira host in `src/test/resources/config.properties`:

```properties
jira.url=https://your-jira-host/projects/PROJECTKEY?selectedItem=com.thed.zephyr.je%3Azephyr-tests-page#test-cycles-tab
```

Any property can be overridden by an environment variable of the same name in upper case with
dots replaced by underscores, so `jira.password` becomes `JIRA_PASSWORD`.

The Google service account needs read and write access to the sheet. Share the sheet with the
service account's client email.

### Sheet columns

`JiraTestExecutionTest` expects these headers in row 1. Change the constants at the top of that
class if yours are named differently.

| Header | Meaning |
| --- | --- |
| `Scenario` | Jira id of the test case, e.g. `PROJ-1553` |
| `Status` | `PASS`, `FAIL` or `BLOCKED` |
| `Order` | order id for a pass, defect id for a fail |
| `StepNumber` | comma separated steps that fail, e.g. `3,7` |
| `ContinueExecution` | `Yes` to keep marking steps after a failure |
| `ExecutionStatus` | written back by the framework |

## Running

```bash
mvn clean test
```

Runs `testng.xml`. Selenium 4 downloads the matching ChromeDriver itself, so there is no
driver binary to install.

The report is written to `target/ExtentReport.html`, with a step level pass/fail entry per test
case. TestNG output lands in `target/surefire-reports/`.

The suite has to stay single threaded - all four steps share one browser session and each
`dependsOnMethods` on the one before it, because Zephyr keeps the selected release cycle in
session state rather than in the URL.

## Notes

Already executed cases are skipped rather than overwritten, so a rerun after a partial failure
picks up where it stopped instead of clobbering results a tester set by hand.

A few places still use a fixed pause. Zephyr animates parts of the execution grid and there is
no element state that reliably signals the animation finished, so those waits are deliberate.
They are all funnelled through `BasePage.pause` to keep them findable.

## Requirements

Java 17, Maven 3.8+, Chrome.
