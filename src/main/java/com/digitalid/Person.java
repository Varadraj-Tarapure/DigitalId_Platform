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

    /*
     Function 2:updatePersonalDetails()
     Updates details while respecting:
     -Under 18 address restriction
     -Birthday change rule
     -Even first digit ID rule
    */
    public boolean updatePersonalDetails(String newID,String newFirstName,String newLastName,String newAddress,String newBirthdate){
        if(!personExists(this.personID))return false;

        int age=calculateAge(this.birthdate);

        // Under 18 cannot change address
        if(age<18&&!newAddress.equals(this.address))return false;

        // If birthday changes,no other field can change
        if(!newBirthdate.equals(this.birthdate)){
            if(!newID.equals(this.personID)||!newFirstName.equals(this.firstName)||!newLastName.equals(this.lastName)||!newAddress.equals(this.address))
                return false;
        }

        // If first digit is even,ID cannot change
        int firstDigit=Character.getNumericValue(this.personID.charAt(0));
        if(firstDigit%2==0&&!newID.equals(this.personID))return false;

        // Validate new values
        if(!isValidPersonID(newID))return false;
        if(!isValidAddress(newAddress))return false;
        if(!isValidBirthdate(newBirthdate))return false;

        try{
            List<String> lines=Files.readAllLines(Paths.get(FILE_PATH));
            List<String> updatedLines=new ArrayList<>();

            for(String line:lines){
                String[] parts=line.split(",");
                if(parts[0].equals(this.personID)){
                    updatedLines.add(newID+","+newFirstName+","+newLastName+","+newAddress+","+newBirthdate);
                }else{
                    updatedLines.add(line);
                }
            }
            Files.write(Paths.get(FILE_PATH),updatedLines);
        }catch(IOException e){
            return false;
        }

        // Update object fields after successful file update
        this.personID=newID;
        this.firstName=newFirstName;
        this.lastName=newLastName;
        this.address=newAddress;
        this.birthdate=newBirthdate;

        return true;
    }
