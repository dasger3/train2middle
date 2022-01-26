package com.epam.ld.module2.testing.service;

import com.epam.ld.module2.testing.models.Client;
import com.epam.ld.module2.testing.models.Template;
import lombok.Getter;
import lombok.Setter;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Template engine.
 */
@Getter
@Setter
public class TemplateEngine {

    private String pathTemplate = "template/email_template.html";
    /**
     * Generate message string.
     *
     * @param template the template
     * @param client   the client
     * @return the string
     */
    public String generateMessage(Template template, Client client) throws FileNotFoundException {

        String templateText = FileService.readFromFile(getPathTemplate());

        return templateText
                .replace("#{ADDRESS}", client.getAddresses())
                .replace("#{SUBJECT}", template.getSubject())
                .replace("#{MESSAGE}", template.getText())
                .replace("#{SENDER}", template.getSender());
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
