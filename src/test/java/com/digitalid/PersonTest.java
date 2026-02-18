package com.digitalid;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PersonTest {
    
    private static final Path FILE_PATH = Path.of("persons.txt");

    @BeforeEach
    void cleanFile() throws IOException {
        Files.deleteIfExists(FILE_PATH);
    }

    @Test
    void addID_validPassport_returnsTrue() {
        Person p = new Person("56s_d%&fAB", "Ali", "Tan", "32| Highland Street|Melbourne|Victoria|Australia", "15-11-1990");
        assertTrue(p.addID("AB123456", "passport"));
    }

    @Test
    void addID_invalidIDPassport_returnsFalse() {
        Person p = new Person("56s_d%&fAB", "Ali", "Tan", "32| Highland Street|Melbourne|Victoria|Australia", "15-11-1990");
        assertFalse(p.addID("Ab123456", "passport")); //the lowercase b will make it fail
    }

    @Test 
    void addID_validLicence_returnsTrue() {
        Person p = new Person("56s_d%&fAB", "Ali", "Tan", "32| Highland Street|Melbourne|Victoria|Australia", "15-11-1990");
        assertTrue(p.addID("CD12345678", "licence")); //must use "licence"
    }

    @Test
    void addID_invalidMedicareNotNineDigits_returnsFalse() {
        Person p = new Person("56s_d%&fAB", "Ali", "Tan", "32| Highland Street|Melbourne|Victoria|Australia", "15-11-1990");
        assertFalse(p.addID("12345A789", "medicare")); //contains letter, so will fail
    }

    @Test
    void addID_studentCare_whenOver18_returnsFalse() {
        Person p = new Person("56s_d%&fAB", "Ali", "Tan", "32| Highland Street|Melbourne|Victoria|Australia", "15-11-1990");
        assertFalse(p.addID("123456789012", "student")); //over 18, so will fail
    }
}
