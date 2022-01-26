package com.epam.ld.module2.testing;

import com.epam.ld.module2.testing.models.Template;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.junit.jupiter.api.Assertions.fail;

public class BaseClassTest {

    public final String CORRECT_INPUT = "Subject: Test subject\nText: Test message text\nSender: voronin@dlit.dp.ua";
    public final Template EXAMPLE_TEMPLATE = new Template("Test subject", "Test message text", "voronin@dlit.dp.ua");

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
    public void sandbox() {

    }
}
