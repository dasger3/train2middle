package com.epam.ld.module2.testing.template;

import com.epam.ld.module2.testing.Client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Template engine.
 */
public class TemplateEngine {
    /**
     * Generate message string.
     *
     * @param template the template
     * @param client   the client
     * @return the string
     */
    public String generateMessage(Template template, Client client) {
        return null;
    }

    public Template createTemplate(String input) {
        Template template = new Template();

        List<String> requiredFields = new ArrayList<String>() {
            {
                add("Subject: ");
                add("Text: ");
                add("Sender: ");
            }
        };

        template.setSubject(parseValue(input, requiredFields.get(0)));
        template.setText(parseValue(input, requiredFields.get(1)));
        template.setSender(parseValue(input, requiredFields.get(2)));

        return template;

    }

    private String parseValue(String input, String nameValue) {
        int start, end;
        String result;

        start = input.indexOf(nameValue);
        if (start < 0) throw new IllegalArgumentException("Wrong input");
        end = input.indexOf("\n", start);
        if (end < 0) end = input.length();
        result = input.substring(start + nameValue.length(), end);
        if (result.length() < 1) throw new NullPointerException("One or more required fields are null");

        return result;
    }
}
