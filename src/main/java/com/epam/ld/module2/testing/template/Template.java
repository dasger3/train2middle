package com.epam.ld.module2.testing.template;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Template.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Template {
    private String subject;
    private String text;
    private String sender;
}
