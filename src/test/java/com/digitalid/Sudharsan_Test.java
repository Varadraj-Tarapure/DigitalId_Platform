package com.digitalid;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/*
 JUnit test class for addPerson method
 Contains 5 test cases to check different scenarios for person validation
*/
public class Sudharsan_Test {

    //  Test Case 1: Valid Person Data
    // Checks if a person with correct ID, address, and birthdate is accepted
    @Test
    void testValidPerson() {
        Person p = new Person("56ab!!CDAB", "John", "Doe",
                "32|Highland Street|Melbourne|Victoria|Australia",
                "15-11-1990");

        assertTrue(p.addPerson()); // Should succeed
    }

    //  Test Case 2: Invalid ID (wrong format)
    // Tests when personID does not match required pattern
    @Test
    void testInvalidID() {
        Person p = new Person("11wrongID", "Sam", "Lee",
                "32|Highland Street|Melbourne|Victoria|Australia",
                "15-11-1990");

        assertFalse(p.addPerson()); // Should fail due to ID
    }

    //  Test Case 3: Invalid Address format
    // Tests an address that does not follow the number|street|city|Victoria|Australia format
    @Test
    void testInvalidAddress() {
        Person p = new Person("56ab!!CDAB", "Anna", "Kim",
                "Melbourne City Australia",
                "15-11-1990");

        assertFalse(p.addPerson()); // Should fail due to address
    }

    //  Test Case 4: Invalid Birthdate format
    // Checks birthdate format, should be DD-MM-YYYY
    @Test
    void testInvalidBirthdate() {
        Person p = new Person("56ab!!CDAB", "Mike", "Ross",
                "32|Highland Street|Melbourne|Victoria|Australia",
                "1990-11-15");

        assertFalse(p.addPerson()); // Should fail due to wrong date format
    }

    //  Test Case 5: Wrong State (not Victoria)
    // Tests if the state part of the address is something other than Victoria
    @Test
    void testWrongState() {
        Person p = new Person("56ab!!CDAB", "Sara", "Ali",
                "32|Highland Street|Melbourne|NSW|Australia",
                "15-11-1990");

        assertFalse(p.addPerson()); // Should fail due to incorrect state
    }
}
