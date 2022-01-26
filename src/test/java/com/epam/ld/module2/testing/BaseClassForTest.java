package com.epam.ld.module2.testing;

import com.epam.ld.module2.testing.models.Template;

public class BaseClassForTest {

    public static final String CORRECT_INPUT = "Subject: Test subject\nText: Test message text\nSender: voronin@dlit.dp.ua";
    public static final String TEST_INPUT = "test input";
    public static final String TEST_TEMPLATE = "test template";
    public static final String TEST_ADDRESS = "test address";
    public static final String WRONG_PATH_TO_TEMPLATE = "wrong path to template";
    public static final String WRONG_PATH = "wrong path";
    public static final String INPUT_FILE = "input.txt";
    public static final String OUTPUT_FILE = "output.html";
    public static final Template EXAMPLE_TEMPLATE = new Template("Test subject", "Test message text", "voronin@dlit.dp.ua");
    public static final String EOL = System.getProperty("line.separator");

}
