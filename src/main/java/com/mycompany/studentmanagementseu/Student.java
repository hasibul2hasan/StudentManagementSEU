package com.mycompany.studentmanagementseu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Student {

    // System Info
    private String umsSerial;
    private String studentId;
    private String programName;
    private String batch;
    private String admissionSession; // Spring/Summer/Fall
    private int year;

    // Personal Data
    private String fullName;
    private String dateOfBirth;
    private String placeOfBirth;
    private String nationality;
    private String nidOrPassport;
    private String gender;
    private String maritalStatus;
    private String religion;
    private String bloodGroup;

    // Family/Guardian
    private String fatherName;
    private String fatherOccupation;
    private String fatherDesignation;
    private String fatherMobile;
    private String fatherEmail;
    private String motherName;
    private String motherOccupation;
    private String motherDesignation;
    private String motherMobile;
    private String motherEmail;
    private String localGuardianName;
    private String localGuardianRelationship;
    private String localGuardianContact;

    // Addresses
    private String presentAddressHouse;
    private String presentAddressRoad;
    private String presentAddressPS;
    private String presentAddressDistrict;
    private String permanentAddressHouse;
    private String permanentAddressRoad;
    private String permanentAddressPS;
    private String permanentAddressDistrict;

    // Financial/Emergency
    private double monthlyFamilyIncome;
    private String emergencyContactName;
    private String emergencyContactRelation;
    private String emergencyContactAddress;
    private String emergencyContactMobile;

    // Education History
    private Education ssc;
    private Education hsc;

    public Student(String umsSerial, String studentId, String programName, String batch, String admissionSession, int year, String fullName, String dateOfBirth, String placeOfBirth, String nationality, String nidOrPassport, String gender, String maritalStatus, String religion, String bloodGroup, String fatherName, String fatherOccupation, String fatherDesignation, String fatherMobile, String fatherEmail, String motherName, String motherOccupation, String motherDesignation, String motherMobile, String motherEmail, String localGuardianName, String localGuardianRelationship, String localGuardianContact, String presentAddressHouse, String presentAddressRoad, String presentAddressPS, String presentAddressDistrict, String permanentAddressHouse, String permanentAddressRoad, String permanentAddressPS, String permanentAddressDistrict, double monthlyFamilyIncome, String emergencyContactName, String emergencyContactRelation, String emergencyContactAddress, String emergencyContactMobile, Education ssc, Education hsc) {
        this.umsSerial = umsSerial;
        this.studentId = studentId;
        this.programName = programName;
        this.batch = batch;
        this.admissionSession = admissionSession;
        this.year = year;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.placeOfBirth = placeOfBirth;
        this.nationality = nationality;
        this.nidOrPassport = nidOrPassport;
        this.gender = gender;
        this.maritalStatus = maritalStatus;
        this.religion = religion;
        this.bloodGroup = bloodGroup;
        this.fatherName = fatherName;
        this.fatherOccupation = fatherOccupation;
        this.fatherDesignation = fatherDesignation;
        this.fatherMobile = fatherMobile;
        this.fatherEmail = fatherEmail;
        this.motherName = motherName;
        this.motherOccupation = motherOccupation;
        this.motherDesignation = motherDesignation;
        this.motherMobile = motherMobile;
        this.motherEmail = motherEmail;
        this.localGuardianName = localGuardianName;
        this.localGuardianRelationship = localGuardianRelationship;
        this.localGuardianContact = localGuardianContact;
        this.presentAddressHouse = presentAddressHouse;
        this.presentAddressRoad = presentAddressRoad;
        this.presentAddressPS = presentAddressPS;
        this.presentAddressDistrict = presentAddressDistrict;
        this.permanentAddressHouse = permanentAddressHouse;
        this.permanentAddressRoad = permanentAddressRoad;
        this.permanentAddressPS = permanentAddressPS;
        this.permanentAddressDistrict = permanentAddressDistrict;
        this.monthlyFamilyIncome = monthlyFamilyIncome;
        this.emergencyContactName = emergencyContactName;
        this.emergencyContactRelation = emergencyContactRelation;
        this.emergencyContactAddress = emergencyContactAddress;
        this.emergencyContactMobile = emergencyContactMobile;
        this.ssc = ssc;
        this.hsc = hsc;
    }

    // Getters and Setters for all fields...

    @Override
    public String toString() {
        return umsSerial + "|" + studentId + "|" + programName + "|" + batch + "|" + admissionSession + "|" + year + "|" +
               fullName + "|" + dateOfBirth + "|" + placeOfBirth + "|" + nationality + "|" + nidOrPassport + "|" + gender + "|" + maritalStatus + "|" + religion + "|" + bloodGroup + "|" +
               fatherName + "|" + fatherOccupation + "|" + fatherDesignation + "|" + fatherMobile + "|" + fatherEmail + "|" +
               motherName + "|" + motherOccupation + "|" + motherDesignation + "|" + motherMobile + "|" + motherEmail + "|" +
               localGuardianName + "|" + localGuardianRelationship + "|" + localGuardianContact + "|" +
               presentAddressHouse + "|" + presentAddressRoad + "|" + presentAddressPS + "|" + presentAddressDistrict + "|" +
               permanentAddressHouse + "|" + permanentAddressRoad + "|" + permanentAddressPS + "|" + permanentAddressDistrict + "|" +
               monthlyFamilyIncome + "|" + emergencyContactName + "|" + emergencyContactRelation + "|" + emergencyContactAddress + "|" + emergencyContactMobile + "|" +
               (ssc != null ? ssc.toString() : "") + "|" +
               (hsc != null ? hsc.toString() : "");
    }

    public static Student fromString(String data) {
        String[] parts = data.split("\\|");
        Education ssc = Education.fromString(parts.length > 42 ? parts[42] : "");
        Education hsc = Education.fromString(parts.length > 43 ? parts[43] : "");

        return new Student(parts[0], parts[1], parts[2], parts[3], parts[4], Integer.parseInt(parts[5]), parts[6], parts[7], parts[8], parts[9], parts[10], parts[11], parts[12], parts[13], parts[14], parts[15], parts[16], parts[17], parts[18], parts[19], parts[20], parts[21], parts[22], parts[23], parts[24], parts[25], parts[26], parts[27], parts[28], parts[29], parts[30], parts[31], parts[32], parts[33], parts[34], parts[35], Double.parseDouble(parts[36]), parts[37], parts[38], parts[39], parts[40], ssc, hsc);
    }

    public void saveToFile(String directoryPath) throws IOException {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(directoryPath + "/" + this.studentId + ".txt"))) {
            writer.write(this.toString());
        }
    }

    public static Student loadFromFile(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String data = reader.readLine();
            return fromString(data);
        }
    }
}
