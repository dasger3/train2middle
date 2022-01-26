package com.epam.ld.module2.testing.service;

import com.epam.ld.module2.testing.BaseClassForTest;
import com.epam.ld.module2.testing.Main;
import com.epam.ld.module2.testing.models.Client;
import com.epam.ld.module2.testing.models.Template;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

public class ModeServiceTest extends BaseClassForTest {

    ByteArrayOutputStream bytesOut;
    PrintStream console;

    @Mock
    Client client;
    @Mock
    TemplateEngine templateEngine;
    @Mock
    Template template;

    @InjectMocks
    ModeService modeService;

    @BeforeEach
    public void setUp() throws FileNotFoundException {
        MockitoAnnotations.initMocks(this);

        bytesOut = new ByteArrayOutputStream();
        console = System.out;
        System.setOut(new PrintStream(bytesOut));

        when(client.getAddresses()).thenReturn(TEST_ADDRESS);
        when(templateEngine.createTemplate(anyString())).thenReturn(template);
        when(templateEngine.generateMessage(template, client)).thenReturn(TEST_TEMPLATE);



    }
    @AfterEach
    public void tearDown() {
        System.setOut(console);
    }

    @Test
    public void checkMailServerWorksInConsoleModeWithCorrectInput() throws IOException {
        modeService.execute(new String[]{});
        assertTrue(bytesOut.toString().contains("Message sent to console" + EOL));
    }

    @Test
    public void checkMailServerWorksCorrectInFileMode(@TempDir Path tempDir) throws IOException {
        modeService.execute(new String[]{INPUT_FILE, tempDir.resolve(OUTPUT_FILE).toAbsolutePath().toString()});
        assertTrue(bytesOut.toString().contains("Message sent to file" + EOL));
    }

    @Test
    public void checkIfFileWithInputNameDoesNotExist(@TempDir Path tempDir) {
        Main.main(new String[]{WRONG_PATH, OUTPUT_FILE});
        assertEquals("Can`t open or something wrong with file named: wrong path" + EOL, bytesOut.toString());
    }

    @Test
    public void checkMailServerDoesNotWorkWithWrongInputConsoleParams() throws IOException {
        modeService.execute(new String[]{"1", "2", "3"});
        assertEquals("Incorrect number of arguments" + EOL, bytesOut.toString());
    }
}
