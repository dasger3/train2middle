package com.epam.ld.module2.testing;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.condition.JRE;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.junit.jupiter.api.Assertions.fail;

public class TaskTest {
    @SkippedTests
    public void mustSkipThisTestByAnnotation() {
        fail();
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Tag("slow") //disabled in build.gradle
    @Test
    public @interface SkippedTests {
    }

    @Test
    @EnabledForJreRange(min = JRE.JAVA_8, max = JRE.JAVA_13)
    public void shouldOnlyRunOnJava8UntilJava13() {
        // this test will only run on Java 8, 9, 10, 11, 12, and 13.
    }
}
