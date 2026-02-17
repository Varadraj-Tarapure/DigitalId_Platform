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
 It implements:
 1)addPerson()
 2)updatePersonalDetails()
 3)addID()

 All required validation rules are checked before storing
 or updating data in the TXT file.
*/

public class Person{

    private String personID;
    private String firstName;
    private String lastName;
    private String address;
    private String birthdate;

    private static final String FILE_PATH="persons.txt";
    private static final DateTimeFormatter FORMATTER=DateTimeFormatter.ofPattern("dd-MM-yyyy");

    // Constructor to initialize a Person object
    public Person(String personID,String firstName,String lastName,String address,String birthdate){
        this.personID=personID;
        this.firstName=firstName;
        this.lastName=lastName;
        this.address=address;
        this.birthdate=birthdate;
    }

    /*
     Function 1:addPerson()
     Validates ID,address and birthdate.
     Stores the record in TXT file if valid.
     Returns true if successful,otherwise false.
    */
    public boolean addPerson(){
        if(!isValidPersonID(personID))return false;
        if(!isValidAddress(address))return false;
        if(!isValidBirthdate(birthdate))return false;

        // Prevent duplicate IDs
        if(personExists(personID))return false;

        try(BufferedWriter writer=new BufferedWriter(new FileWriter(FILE_PATH,true))){
            writer.write(personID+","+firstName+","+lastName+","+address+","+birthdate);
            writer.newLine();
        }catch(IOException e){
            return false;
        }
        return true;
    }

