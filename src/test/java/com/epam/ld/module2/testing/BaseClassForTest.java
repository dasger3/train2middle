package com.epam.ld.module2.testing;

import com.epam.ld.module2.testing.models.Template;

public class BaseClassForTest {

    public final String CORRECT_INPUT = "Subject: Test subject\nText: Test message text\nSender: voronin@dlit.dp.ua";
    public static String TEST_INPUT = "test input";
    public static String TEST_TEMPLATE = "test template";
    public static String TEST_ADDRESS = "test address";
    public static String WRONG_PATH_TO_TEMPLATE = "wrong path";
    public final Template EXAMPLE_TEMPLATE = new Template("Test subject", "Test message text", "voronin@dlit.dp.ua");
}
