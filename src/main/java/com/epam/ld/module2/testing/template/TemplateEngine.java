package com.epam.ld.module2.testing.template;

import com.epam.ld.module2.testing.Client;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

        String templateText = readFromFile(getPathTemplate());

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

    private String readFromFile (String fileName) throws FileNotFoundException {
        try {
            File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource(fileName)).getPath());

            return Files.toString(file, Charsets.UTF_8);
        }
        catch (IOException | NullPointerException e) {
            throw new FileNotFoundException("Can`t open or something wrong with file named: " + fileName);
        }
    }
}
