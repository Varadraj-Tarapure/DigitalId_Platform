package com.digitalid;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {
    
    private static final Path FILE_PATH = Path.of("persons.txt");

    @BeforeEach
    void cleanFile() throws IOException {
        Files.deleteIfExists(FILE_PATH);
    }
}
