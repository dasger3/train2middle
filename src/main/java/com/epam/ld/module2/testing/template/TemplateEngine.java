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

        int start = input.indexOf("Subject: ");
        if (start < 0) throw new IllegalArgumentException("Wrong input");
        int end = input.indexOf("\n", start);
        if (end < 0) end = input.length();
        String value = input.substring(start+9, end);
        if (value.length() < 1) throw new NullPointerException("One or more required fields are null");
        template.setSubject(value);


        start = input.indexOf("Text: ");
        if (start < 0) throw new IllegalArgumentException("Wrong input");
        end = input.indexOf("\n", start);
        if (end < 0) end = input.length();
        value = input.substring(start+6, end);
        if (value.length() < 1) throw new NullPointerException("One or more required fields are null");
        template.setText(value);


        start = input.indexOf("Sender: ");
        if (start < 0) throw new IllegalArgumentException("Wrong input");
        end = input.indexOf("\n", start);
        if (end < 0) end = input.length();
        value = input.substring(start+8, end);
        if (value.length() < 1) throw new NullPointerException("One or more required fields are null");
        template.setSender(value);
        return template;



    }
}
