package com.epam.ld.module2.testing;

import com.epam.ld.module2.testing.models.Client;
import com.epam.ld.module2.testing.service.ModeService;
import com.epam.ld.module2.testing.service.TemplateEngine;

public class Main {

    private static final Client clientExample = new Client("oleksii_voronin2@epam.com");

    public static void main(String[] args) {

        TemplateEngine templateEngine = new TemplateEngine();

        ModeService modeService = new ModeService(clientExample, templateEngine);

        try {
            modeService.execute(args);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
