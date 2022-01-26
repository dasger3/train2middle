package com.epam.ld.module2.testing;

import com.epam.ld.module2.testing.service.FileService;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TestResultExtension implements TestWatcher, AfterAllCallback, AfterEachCallback {
    private List<TestResultStatus> testResultsStatus = new ArrayList<>();
    StringBuilder result = new StringBuilder();

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        result.append("\n");
    }

    private enum TestResultStatus {
        SUCCESSFUL, ABORTED, FAILED, DISABLED;
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        result.append("Test Disabled for test ")
                .append(context.getDisplayName())
                .append(" with reason :- ")
                .append(reason.orElse("No reason"));

        testResultsStatus.add(TestResultStatus.DISABLED);
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        result.append("Test Successful for test : ").append(context.getDisplayName());

        testResultsStatus.add(TestResultStatus.SUCCESSFUL);
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        result.append("Test Aborted for test: ").append(context.getDisplayName());

        testResultsStatus.add(TestResultStatus.ABORTED);
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        result.append("Test Failed for test ").append(context.getDisplayName());

        testResultsStatus.add(TestResultStatus.FAILED);
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        Map<TestResultStatus, Long> summary = testResultsStatus.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        result.append("\n").append("Test result summary for ").append(context.getDisplayName()).append(summary.toString());

        File file = new File("tests_output.txt");
        FileService.OUTPUT_FILE = file.getAbsolutePath();
        FileService.writeToFile(result.toString());
    }
}

