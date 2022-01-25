package com.epam.ld.module2.testing.template;

import com.epam.ld.module2.testing.Client;

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

        String[] inputLines = input.split("\n");

        List<String> filteredLines = Arrays.stream(inputLines)
                .filter(row -> row.startsWith("Subject:") ||
                        row.startsWith("Text:") ||
                        row.startsWith("Sender:"))
                .collect(Collectors.toList());

        if (filteredLines.size() < 3) throw new IllegalArgumentException("Wrong input");

        template.setSubject(filteredLines.get(0).substring(filteredLines.get(0).indexOf(" ") + 1));
        template.setText(filteredLines.get(1).substring(filteredLines.get(1).indexOf(" ") + 1));
        template.setSender(filteredLines.get(2).substring(filteredLines.get(2).indexOf(" ") + 1));

        return template;
    }
}
