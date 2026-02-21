package com.digitalid;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class Nameer_Test {

    private static final String FILE_PATH = "persons.txt";

    // --- Test 1 everthing is Valid  ---

    @Test
    void updateDetails_validAdultOddID_returnsTrue() {
        // ID starts with '3' (Odd), Age is > 18 (born 1990)
        Person p = new Person("36s_d%&fAB", "Ali", "Tan", "32|Highland Street|Melbourne|Victoria|Australia", "15-11-1990");
        p.addPerson(); // we must call p.addPerson() first to create the record in the text file before we attempt to update it.

        // Update Name and Address (Allowed for adult)
        boolean result = p.updatePersonalDetails(
            "76s_d%&fAB", 
            "Nameer", 
            "NewLastName", 
            "50|New Street|Geelong|Victoria|Australia", 
            "15-11-1990" // Birthday unchanged
        );
        assertTrue(result, "Should successfully update details for a valid adult with odd ID");
    }


    // --- Test 2 Condition 1: Under 18 Address Check ---

    @Test
    void updateDetails_under18ChangeAddress_returnsFalse() {
        // Born 2015 (Age ~11 in 2026), ID '96...'
        Person p = new Person("96s_d%&fAB", "Kid", "Tan", "32|Highland Street|Melbourne|Victoria|Australia", "15-11-2015");
        p.addPerson();

        // Try to change address
        boolean result = p.updatePersonalDetails(
            "96s_d%&fAB", 
            "Kid", 
            "Tan", 
            "99|Forbidden St|Melbourne|Victoria|Australia", // New Address
            "15-11-2015"
        );
        assertFalse(result, "Should fail because persons under 18 cannot change address");
    }


        // --- Test 3 Condition 2: Birthday Change Check ---

    @Test
    void updateDetails_changeBirthdayAndName_returnsFalse() {
        Person p = new Person("66s_d%&fAB", "Ali", "Tan", "32|Highland Street|Melbourne|Victoria|Australia", "15-11-1990");
        p.addPerson();

        // Try to change Birthday AND Name
        boolean result = p.updatePersonalDetails(
            "66s_d%&fAB", 
            "Nameer", // Name changed
            "Tan", 
            "32|Highland Street|Melbourne|Victoria|Australia", 
            "20-11-1990" // Birthday changed
        );
        assertFalse(result, "Should fail because if birthday changes, no other details can change");
    }


       // --- Test 4 Condition 3: Even/Odd ID Check ---

    @Test
    void updateDetails_evenStartID_changeID_returnsFalse() {
        // ID starts with '4' (Even)
        Person p = new Person("46s_d%&fAB", "Ali", "Tan", "32|Highland Street|Melbourne|Victoria|Australia", "15-11-1990");
        p.addPerson();

        // Try to change ID
        boolean result = p.updatePersonalDetails(
            "06s_d%&fAB", // Changing ID to something else
            "Ali", 
            "Tan", 
            "32|Highland Street|Melbourne|Victoria|Australia", 
            "15-11-1990"
        );
        assertFalse(result, "Should fail because ID starts with an even number (2)");
    }



    // --- Test 5 Invalid Format Checks on Updates ---

    @Test
    void updateDetails_invalidNewAddressFormat_returnsFalse() {
        Person p = new Person("56s_d%&fAB", "Ali", "Tan", "32|Highland Street|Melbourne|Victoria|Australia", "15-11-1990");
        p.addPerson();

        // Try to update with invalid address format (Wrong State)
        boolean result = p.updatePersonalDetails(
            "56s_d%&fAB", 
            "Ali", 
            "Tan", 
            "32|Highland Street|Sydney|NSW|Australia", // Invalid: State must be Victoria
            "15-11-1990"
        );
        assertFalse(result, "Should fail because the new address format is invalid (State not Victoria)");
    }

}
