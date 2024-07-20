# Running Tests and Generating Reports with Maven

## Maven Surefire Plugin
The Maven Surefire Plugin is used for running unit tests in a Maven project. It is typically bound to the test phase of the build lifecycle. This plugin executes the tests and generates reports about which tests passed or failed. It is highly configurable, allowing developers to specify test classes, configure reporting options, and manage test execution parameters.
> Note: by default no report will be generated if the test fails unless the 'test' goal is configured with the **testFailureIgnore** parameter set to true (NOT RECOMMENDED)

## JaCoCo Plugin
The JaCoCo (Java Code Coverage) Plugin is used to measure code coverage during testing in a Maven project. It integrates with the Maven Surefire Plugin to collect coverage data while tests are being executed. JaCoCo generates detailed reports showing which lines of code were executed by the tests and which were not. This helps developers understand the effectiveness of their tests and identify untested parts of their codebase.
> Note: see configuration example of the plugin here:  [pom.xml](https://github.com/kayulu/spring-boot-unit-testing/blob/main/1.00-starting-project/pom.xml)

**Output:**

- **Maven Surefire Plugin:** Generates test result reports indicating test success or failure.
- **JaCoCo Plugin:** Generates code coverage reports, often including metrics like line coverage and branch coverage.
