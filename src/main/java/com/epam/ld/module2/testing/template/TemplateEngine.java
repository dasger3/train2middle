package com.epam.ld.module2.testing.template;

import com.epam.ld.module2.testing.Client;

import java.util.Arrays;

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

        String[] inputLines = input.split("\n");

        template.setSubject(inputLines[0].substring(inputLines[0].indexOf(" ") + 1));
        template.setText(inputLines[1].substring(inputLines[1].indexOf(" ") + 1));
        template.setSender(inputLines[2].substring(inputLines[2].indexOf(" ") + 1));

        return template;
    }
}
