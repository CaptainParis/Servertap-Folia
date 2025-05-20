# ServerTap Tests

This directory contains tests for the ServerTap plugin, including tests for Folia compatibility.

## Test Structure

The tests are organized as follows:

1. **Unit Tests**: Tests for individual components
   - `SchedulerUtilsTest.java`: Tests for the `SchedulerUtils` class that handles scheduler operations for both Bukkit and Folia
   - `ServerExecCommandSenderTest.java`: Tests for the `ServerExecCommandSender` class to ensure it correctly uses the appropriate scheduler
   - `WebhookEventListenerTest.java`: Tests for the `WebhookEventListener` class to ensure it correctly uses the appropriate scheduler

2. **Integration Tests**: Tests for the plugin as a whole
   - `FoliaIntegrationTest.java`: Tests that verify the plugin works correctly in a Folia environment
   - `TestPluginClass.java`: Tests that verify the plugin works correctly in a standard Bukkit environment
   - `PluginYmlTest.java`: Tests that verify the plugin.yml file has the correct configuration

## Running the Tests

To run the tests, use the following Maven command:

```bash
mvn test
```

This will run all the tests and report the results.

## Test Environment

The tests use the following libraries:

- **JUnit 5**: For test execution and assertions
- **MockBukkit**: For mocking the Bukkit environment
- **Mockito**: For mocking classes and methods
- **Unirest**: For testing HTTP endpoints

## Folia Testing

Since Folia is not available as a test dependency, we simulate a Folia environment by mocking the necessary classes and methods. This allows us to verify that our code correctly detects Folia and uses the appropriate scheduler methods.

The `FoliaIntegrationTest` class contains tests that verify:

1. The plugin can be loaded in a Folia environment
2. The LagDetector works correctly in a Folia environment
3. The API endpoints work correctly in a Folia environment

The `SchedulerUtilsTest` class contains tests that verify:

1. The `isFolia()` method correctly detects whether Folia is available
2. The scheduler methods use the appropriate scheduler based on the environment

## Adding New Tests

When adding new features or making changes to the plugin, please add appropriate tests to verify that the changes work correctly in both Bukkit and Folia environments.
