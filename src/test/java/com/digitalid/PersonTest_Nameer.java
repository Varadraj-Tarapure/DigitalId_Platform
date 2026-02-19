package com.digitalid;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PersonTest_Nameer {

    private static final String FILE_PATH = "persons.txt";

    @BeforeEach
    void cleanFile() throws IOException {
        Files.deleteIfExists(Paths.get(FILE_PATH));
    }

    // --- Valid Update Path  ---

    @Test
    void updateDetails_validAdultOddID_returnsTrue() {
        // ID starts with '3' (Odd), Age is > 18 (born 1990)
        Person p = new Person("36s_d%&fAB", "Ali", "Tan", "32|Highland Street|Melbourne|Victoria|Australia", "15-11-1990");
        p.addPerson(); // we must call p.addPerson() first to create the record in the text file before we attempt to update it.

        // Update Name and Address (Allowed for adult)
        boolean result = p.updatePersonalDetails(
            "36s_d%&fAB", 
            "Nameer", 
            "NewLastName", 
            "50|New Street|Geelong|Victoria|Australia", 
            "15-11-1990" // Birthday unchanged
        );
        assertTrue(result, "Should successfully update details for a valid adult with odd ID");
    }

    // --- Condition 1: Under 18 Address Check ---

    @Test
    void updateDetails_under18ChangeAddress_returnsFalse() {
        // Born 2015 (Age ~11 in 2026), ID '36...'
        Person p = new Person("36s_d%&fAB", "Kid", "Tan", "32|Highland Street|Melbourne|Victoria|Australia", "15-11-2015");
        p.addPerson();

        // Try to change address
        boolean result = p.updatePersonalDetails(
            "36s_d%&fAB", 
            "Kid", 
            "Tan", 
            "99|Forbidden St|Melbourne|Victoria|Australia", // New Address
            "15-11-2015"
        );
        assertFalse(result, "Should fail because persons under 18 cannot change address");
    }

    @Test
    void updateDetails_under18ChangeNameOnly_returnsTrue() {
        // Born 2015 (Age ~11), ID '36...'
        Person p = new Person("36s_d%&fAB", "Kid", "Tan", "32|Highland Street|Melbourne|Victoria|Australia", "15-11-2015");
        p.addPerson();

        // Try to change Name only (Address remains same)
        boolean result = p.updatePersonalDetails(
            "36s_d%&fAB", 
            "Nameer", // New Name
            "Tan", 
            "32|Highland Street|Melbourne|Victoria|Australia", // Same Address
            "15-11-2015"
        );
        assertTrue(result, "Should pass because address did not change, only name did");
    }

    
}
