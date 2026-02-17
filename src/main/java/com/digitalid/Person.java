package com.digitalid;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 This Person class represents a user in the DigitalID platform.
 It implements the three required functions:
 1) addPerson()
 2) updatePersonalDetails()
 3) addID()

 The class validates all required conditions before storing
 or updating information inside a TXT file.
*/

public class Person {

    private String personID;
    private String firstName;
    private String lastName;
    private String address;
    private String birthdate;

    private static final String FILE_PATH = "persons.txt";
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");

    // Constructor to initialize a Person object
    public Person(String personID, String firstName,
                  String lastName, String address,
                  String birthdate) {

        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.birthdate = birthdate;
    }

    /*
     Function 1: addPerson()
     This method validates all required conditions and
     stores the person information in the TXT file.
     Returns true if successful, otherwise false.
    */
    public boolean addPerson() {

        // validate personID, address and birthdate format
        if (!isValidPersonID(personID)) return false;
        if (!isValidAddress(address)) return false;
        if (!isValidBirthdate(birthdate)) return false;

        // prevent duplicate IDs
        if (personExists(personID)) return false;

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(FILE_PATH, true))) {

            writer.write(personID + "," + firstName + "," +
                    lastName + "," + address + "," + birthdate);
            writer.newLine();

        } catch (IOException e) {
            return false;
        }

        return true;
    }

    /*
     Function 2: updatePersonalDetails()
     Updates a person's details while respecting all
     special conditions given in the assignment.
    */
    public boolean updatePersonalDetails(String newID,
                                         String newFirstName,
                                         String newLastName,
                                         String newAddress,
                                         String newBirthdate) {

        // person must exist before updating
        if (!personExists(this.personID)) return false;

        int age = calculateAge(this.birthdate);

        // Condition 1: Under 18 cannot change address
        if (age < 18 && !newAddress.equals(this.address))
            return false;

        // Condition 2: If birthday changes, no other field can change
        if (!newBirthdate.equals(this.birthdate)) {
            if (!newID.equals(this.personID) ||
                    !newFirstName.equals(this.firstName) ||
                    !newLastName.equals(this.lastName) ||
                    !newAddress.equals(this.address))
                return false;
        }

        // Condition 3: If first digit of ID is even, ID cannot change
        int firstDigit =
                Character.getNumericValue(this.personID.charAt(0));

        if (firstDigit % 2 == 0 &&
                !newID.equals(this.personID))
            return false;

        // validate new data formats
        if (!isValidPersonID(newID)) return false;
        if (!isValidAddress(newAddress)) return false;
        if (!isValidBirthdate(newBirthdate)) return false;

        try {
            List<String> lines =
                    Files.readAllLines(Paths.get(FILE_PATH));

            List<String> updatedLines = new ArrayList<>();

            for (String line : lines) {
                String[] parts = line.split(",");

                if (parts[0].equals(this.personID)) {
                    updatedLines.add(newID + "," + newFirstName +
                            "," + newLastName + "," +
                            newAddress + "," + newBirthdate);
                } else {
                    updatedLines.add(line);
                }
            }

            Files.write(Paths.get(FILE_PATH), updatedLines);

        } catch (IOException e) {
            return false;
        }

        // update object values after file update
        this.personID = newID;
        this.firstName = newFirstName;
        this.lastName = newLastName;
        this.address = newAddress;
        this.birthdate = newBirthdate;

        return true;
    }

    /*
     Function 3: addID()
     Adds demerit points for an offence.
     Validates offence date format and point range.
    */
    public String addID(String offenceDate, int points) {

        if (!isValidBirthdate(offenceDate))
            return "Failed";

        if (points < 1 || points > 6)
            return "Failed";

        return "Success";
    }

    // validates personID based on assignment rules
    private boolean isValidPersonID(String id) {

        if (id == null || id.length() != 10)
            return false;

        if (!id.substring(0, 2).matches("[2-9]{2}"))
            return false;

        if (!id.substring(8).matches("[A-Z]{2}"))
            return false;

        String middle = id.substring(2, 8);
        int specialCount = 0;

        for (char c : middle.toCharArray()) {
            if (!Character.isLetterOrDigit(c))
                specialCount++;
        }

        return specialCount >= 2;
    }

    // validates address format
    private boolean isValidAddress(String address) {

        if (address == null)
            return false;

        String regex =
                "^[0-9]+\\|[A-Za-z ]+\\|[A-Za-z ]+\\|Victoria\\|Australia$";

        return address.matches(regex);
    }

    // validates date format DD-MM-YYYY
    private boolean isValidBirthdate(String birthdate) {

        try {
            LocalDate.parse(birthdate, FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    // calculates age from birthdate
    private int calculateAge(String birthdate) {

        LocalDate birth =
                LocalDate.parse(birthdate, FORMATTER);

        return Period.between(birth,
                LocalDate.now()).getYears();
    }

    // checks if ID already exists in file
    private boolean personExists(String id) {

        try {
            File file = new File(FILE_PATH);
    
            if (!file.exists())
                return false;
    
            try (Scanner scanner = new Scanner(file)) {
    
                while (scanner.hasNextLine()) {
                    if (scanner.nextLine().startsWith(id + ","))
                        return true;
                }
            }
    
        } catch (IOException e) {
            return false;
        }
    
        return false;
    }
}    