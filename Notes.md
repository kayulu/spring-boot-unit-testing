
# 1. Running Tests and Generating Reports with Maven

## Maven Surefire Plugin
The Maven Surefire Plugin is used for running unit tests in a Maven project. It is typically bound to the test phase of the build lifecycle. This plugin executes the tests and generates reports about which tests passed or failed. It is highly configurable, allowing developers to specify test classes, configure reporting options, and manage test execution parameters.
> Note: by default no report will be generated if the test fails unless the 'test' goal is configured with the **testFailureIgnore** parameter set to true (NOT RECOMMENDED)

## JaCoCo Plugin
The JaCoCo (Java Code Coverage) Plugin is used to measure code coverage during testing in a Maven project. It integrates with the Maven Surefire Plugin to collect coverage data while tests are being executed. JaCoCo generates detailed reports showing which lines of code were executed by the tests and which were not. This helps developers understand the effectiveness of their tests and identify untested parts of their codebase.
> Note: see configuration example of the plugin here:  [pom.xml](https://github.com/kayulu/spring-boot-unit-testing/blob/main/1.00-starting-project/pom.xml)

**Output:**

- **Maven Surefire Plugin:** Generates test result reports indicating test success or failure.
- **JaCoCo Plugin:** Generates code coverage reports, often including metrics like line coverage and branch coverage.

# 2. Using Mockito
## What is Mockito?

Mockito is a popular open-source Java framework used for creating mock objects in unit tests. It allows developers to create, configure, and manage mock instances of dependencies, enabling isolated and focused testing of the code under test. Mockito simplifies the process of writing tests by providing a clean and readable API for setting up mock behavior, verifying interactions, and stubbing method calls.

## When to Use Mockito

Mockito is particularly useful in the following scenarios:

1. **Isolating Units of Code**:
   - When you want to test a specific class or method in isolation, Mockito allows you to mock its dependencies. This ensures that the test focuses solely on the logic of the class under test, without being affected by the behavior of its dependencies.

2. **Dependency Injection**:
   - For classes that rely on external services, databases, or other complex dependencies, using real instances can make tests slow, brittle, and difficult to set up. Mockito helps by providing mock implementations of these dependencies, making tests faster and more reliable.

3. **Testing Interactions**:
   - When the behavior of a class depends on interactions with its dependencies (e.g., calling methods with specific arguments), Mockito allows you to verify these interactions. This ensures that the class under test communicates correctly with its collaborators.

4. **Simulating Edge Cases**:
   - Mockito can simulate various scenarios and edge cases by configuring the behavior of mock objects. For example, you can make a mock throw an exception or return specific values based on the input, helping to test how the class under test handles different conditions.

5. **Improving Test Readability**:
   - Mockito’s fluent API makes test code more readable and easier to understand. By clearly specifying the setup, action, and verification steps, tests become more maintainable and easier to reason about.

## How to use Mockito?

Here's a brief example to illustrate how Mockito can be used:

```java
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.runner.RunWith;

@RunWith(MockitoJUnitRunner.class)
public class MyServiceTest {

    @Mock
    private MyDependency myDependency;  // This is a dependency of MyService class that we need to mock.
                                        // The Mockito framework will replace this dependeny with a mock (double) during test execution.

    @InjectMocks
    private MyService myService; // This is the class under test; mocks will be injected into this class

    @Test
    public void testSomeMethod() {
        // Arrange
        String expectedValue = "someValue";
        when(myDependency.someMethod()).thenReturn(expectedValue);

        // Act
        String result = myService.someMethod();

        // Assert
        assertEquals(expectedValue, result);

        // Verify
        verify(myDependency).someMethod();
    }
}
```

In this example:
- `MyDependency` is a dependency of `MyService`.
- The `@Mock` annotation creates a mock instance of `MyDependency`.
- The `@InjectMocks` annotation injects the mock into `MyService`.
- The `when(...).thenReturn(...)` syntax sets up the mock behavior.
- The `verify(...)` method checks that `someMethod` was called on the mock.

Mockito is a powerful tool for writing effective unit tests by enabling precise control over dependencies and interactions, ultimately leading to more robust and maintainable code.
