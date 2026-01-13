package com.mycompany.studentmanagementseu;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * ============================================================================
 * ALGORITHM: Student Management System
 * ============================================================================
 * 
 * 1. START
 * 2. Display Main Menu with options:
 *    a. Add New Student
 *    b. View Students
 * 3. IF user selects "Add New Student":
 *    a. Display student information form
 *    b. Collect student data (ID, name, program, personal info, family info, address)
 *    c. Validate required fields (Student ID, Full Name)
 *    d. Check if Student ID already exists
 *    e. IF exists, show error message
 *    f. ELSE save student data to file system
 *    g. Display success message
 * 4. IF user selects "View Students":
 *    a. Load all student records from file system
 *    b. Display students in a table format
 *    c. Allow search by Student ID
 *    d. Provide options to Update, Delete, or View each student
 * 5. END
 * 
 * ============================================================================
 * OOP PILLARS DEMONSTRATED:
 * ============================================================================
 * 1. ENCAPSULATION: Private fields with public getters/setters in Person & Student
 * 2. INHERITANCE: Student extends abstract Person class
 * 3. ABSTRACTION: Abstract Person class & Storable interface
 * 4. POLYMORPHISM: Method overloading (constructors), method overriding (toString, getDisplayInfo)
 * ============================================================================
 */

/**
 * Interface demonstrating ABSTRACTION
 * Defines a contract for objects that can be stored and loaded
 */
interface Storable {
    /**
     * Returns a formatted string representation for storage
     * @return String formatted for file storage
     */
    String toStorageFormat();
    
    /**
     * Returns display information for UI
     * @return String formatted for display
     */
    String getDisplayInfo();
}

/**
 * Abstract class demonstrating ABSTRACTION and providing base for INHERITANCE
 * Contains common properties shared by all persons in the system
 */
abstract class Person {
    // ENCAPSULATION: Private fields with controlled access through getters/setters
    private String fullName;
    private String nidOrPassport;
    private String gender;
    private String maritalStatus;
    private String religion;
    private String bloodGroup;
    private String presentAddress;
    private String permanentAddress;

    // Default constructor
    public Person() {
    }

    // Parameterized constructor - POLYMORPHISM through overloading
    public Person(String fullName) {
        this.fullName = fullName;
    }

    // Another overloaded constructor - POLYMORPHISM
    public Person(String fullName, String gender) {
        this.fullName = fullName;
        this.gender = gender;
    }

    // Abstract method - forces subclasses to implement (ABSTRACTION)
    public abstract String getRole();

    // Getters and Setters - ENCAPSULATION
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getNidOrPassport() { return nidOrPassport; }
    public void setNidOrPassport(String nidOrPassport) { this.nidOrPassport = nidOrPassport; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getMaritalStatus() { return maritalStatus; }
    public void setMaritalStatus(String maritalStatus) { this.maritalStatus = maritalStatus; }
    public String getReligion() { return religion; }
    public void setReligion(String religion) { this.religion = religion; }
    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }
    public String getPresentAddress() { return presentAddress; }
    public void setPresentAddress(String presentAddress) { this.presentAddress = presentAddress; }
    public String getPermanentAddress() { return permanentAddress; }
    public void setPermanentAddress(String permanentAddress) { this.permanentAddress = permanentAddress; }
}

/**
 * Student class demonstrating INHERITANCE (extends Person) and 
 * implementing interface (Storable) for ABSTRACTION
 * Also demonstrates POLYMORPHISM through method overloading and overriding
 */
class Student extends Person implements Storable {
    // ENCAPSULATION: Private fields specific to Student
    private String umsSerial;
    private String studentId;
    private String programName;
    private String batch;
    private String admissionSession;
    private String year;
    private String fatherName;
    private String fatherOccupation;
    private String fatherMobile;
    private String motherName;
    private String motherOccupation;
    private String motherMobile;

    // Default constructor - POLYMORPHISM (constructor overloading)
    public Student() {
        super();
    }

    // Constructor with basic info - POLYMORPHISM (constructor overloading)
    public Student(String studentId, String fullName, String programName) {
        super(fullName); // Calling parent constructor - INHERITANCE
        this.studentId = studentId;
        this.programName = programName;
    }

    // Constructor with more details - POLYMORPHISM (constructor overloading)
    public Student(String studentId, String fullName, String programName, String batch) {
        super(fullName);
        this.studentId = studentId;
        this.programName = programName;
        this.batch = batch;
    }

    // Full constructor - POLYMORPHISM (constructor overloading)
    public Student(String studentId, String fullName, String programName, String batch, String gender) {
        super(fullName, gender); // Using overloaded parent constructor
        this.studentId = studentId;
        this.programName = programName;
        this.batch = batch;
    }

    // Implementation of abstract method from Person - POLYMORPHISM (method overriding)
    @Override
    public String getRole() {
        return "Student";
    }

    // Implementation of Storable interface - ABSTRACTION
    @Override
    public String toStorageFormat() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- Student Information ---\n");
        sb.append("UMS Serial: ").append(umsSerial).append("\n");
        sb.append("Student ID: ").append(studentId).append("\n");
        sb.append("Program Name: ").append(programName).append("\n");
        sb.append("Batch: ").append(batch).append("\n");
        sb.append("Admission Session: ").append(admissionSession).append("\n");
        sb.append("Year: ").append(year).append("\n\n");
        sb.append("--- Personal Data ---\n");
        sb.append("Full Name: ").append(getFullName()).append("\n");
        sb.append("NID/Passport: ").append(getNidOrPassport()).append("\n");
        sb.append("Gender: ").append(getGender()).append("\n");
        sb.append("Marital Status: ").append(getMaritalStatus()).append("\n");
        sb.append("Religion: ").append(getReligion()).append("\n");
        sb.append("Blood Group: ").append(getBloodGroup()).append("\n\n");
        sb.append("--- Family/Guardian Information ---\n");
        sb.append("Father's Name: ").append(fatherName).append("\n");
        sb.append("Occupation: ").append(fatherOccupation).append("\n");
        sb.append("Mobile: ").append(fatherMobile).append("\n");
        sb.append("Mother's Name: ").append(motherName).append("\n");
        sb.append("Occupation: ").append(motherOccupation).append("\n");
        sb.append("Mobile: ").append(motherMobile).append("\n\n");
        sb.append("--- Address Information ---\n");
        sb.append("Present Address: ").append(getPresentAddress()).append("\n");
        sb.append("Permanent Address: ").append(getPermanentAddress()).append("\n");
        return sb.toString();
    }

    // Implementation of Storable interface - ABSTRACTION
    @Override
    public String getDisplayInfo() {
        return "ID: " + studentId + ", Name: " + getFullName() + ", Program: " + programName;
    }

    // POLYMORPHISM: Overriding toString() from Object class
    @Override
    public String toString() {
        return getDisplayInfo();
    }

    // Overloaded display method - POLYMORPHISM (method overloading)
    public String getDisplayInfo(boolean detailed) {
        if (detailed) {
            return "ID: " + studentId + ", Name: " + getFullName() + 
                   ", Program: " + programName + ", Batch: " + batch + 
                   ", Year: " + year;
        }
        return getDisplayInfo();
    }

    // Another overloaded method - POLYMORPHISM (method overloading)
    public String getDisplayInfo(String format) {
        if ("short".equalsIgnoreCase(format)) {
            return studentId + " - " + getFullName();
        } else if ("medium".equalsIgnoreCase(format)) {
            return getDisplayInfo();
        } else {
            return getDisplayInfo(true);
        }
    }

    // Getters and setters for Student-specific fields - ENCAPSULATION
    public String getUmsSerial() { return umsSerial; }
    public void setUmsSerial(String umsSerial) { this.umsSerial = umsSerial; }
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public String getProgramName() { return programName; }
    public void setProgramName(String programName) { this.programName = programName; }
    public String getBatch() { return batch; }
    public void setBatch(String batch) { this.batch = batch; }
    public String getAdmissionSession() { return admissionSession; }
    public void setAdmissionSession(String admissionSession) { this.admissionSession = admissionSession; }
    public String getYear() { return year; }
    public void setYear(String year) { this.year = year; }
    public String getFatherName() { return fatherName; }
    public void setFatherName(String fatherName) { this.fatherName = fatherName; }
    public String getFatherOccupation() { return fatherOccupation; }
    public void setFatherOccupation(String fatherOccupation) { this.fatherOccupation = fatherOccupation; }
    public String getFatherMobile() { return fatherMobile; }
    public void setFatherMobile(String fatherMobile) { this.fatherMobile = fatherMobile; }
    public String getMotherName() { return motherName; }
    public void setMotherName(String motherName) { this.motherName = motherName; }
    public String getMotherOccupation() { return motherOccupation; }
    public void setMotherOccupation(String motherOccupation) { this.motherOccupation = motherOccupation; }
    public String getMotherMobile() { return motherMobile; }
    public void setMotherMobile(String motherMobile) { this.motherMobile = motherMobile; }
}

/**
 * DataStorage class demonstrating ABSTRACTION
 * Provides static utility methods for data persistence operations
 * Hides the complexity of file I/O from other classes
 */
class DataStorage {
    private static final String DATA_DIR = "student_data";

    /**
     * Algorithm for saving student:
     * 1. Create data directory if it doesn't exist
     * 2. Create file with student ID as filename
     * 3. Write student data using Storable interface method
     */
    public static void saveStudent(Student student) throws IOException {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, student.getStudentId() + ".txt");
        try (FileWriter writer = new FileWriter(file)) {
            // Using Storable interface method - ABSTRACTION & POLYMORPHISM
            writer.write(student.toStorageFormat());
        }
    }

    /**
     * Algorithm for getting all students:
     * 1. Read all .txt files from data directory
     * 2. Parse each file to extract student ID, name, program
     * 3. Create Student objects and add to list
     * 4. Return the list
     */
    public static List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        File dir = new File(DATA_DIR);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".txt"));
        if (files != null) {
            for (File file : files) {
                try (Scanner scanner = new Scanner(file)) {
                    String studentId = "";
                    String fullName = "";
                    String programName = "";
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        if (line.startsWith("Student ID: ")) {
                            studentId = line.substring("Student ID: ".length());
                        } else if (line.startsWith("Full Name: ")) {
                            fullName = line.substring("Full Name: ".length());
                        } else if (line.startsWith("Program Name: ")) {
                            programName = line.substring("Program Name: ".length());
                        }
                    }
                    if (!studentId.isEmpty()) {
                        students.add(new Student(studentId, fullName, programName));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return students;
    }

    /**
     * Algorithm for loading a specific student:
     * 1. Check if file exists for given student ID
     * 2. If not found, return null
     * 3. Parse file line by line to extract all fields
     * 4. Create and populate Student object
     * 5. Return the Student object
     */
    public static Student loadStudent(String studentId) {
        File file = new File(DATA_DIR, studentId + ".txt");
        if (!file.exists()) {
            return null;
        }
        try (Scanner scanner = new Scanner(file)) {
            String id = "";
            String fullName = "";
            String programName = "";
            String umsSerial = "";
            String batch = "";
            String admissionSession = "";
            String year = "";
            String nidOrPassport = "";
            String gender = "";
            String maritalStatus = "";
            String religion = "";
            String bloodGroup = "";
            String fatherName = "";
            String fatherOccupation = "";
            String fatherMobile = "";
            String motherName = "";
            String motherOccupation = "";
            String motherMobile = "";
            String presentAddress = "";
            String permanentAddress = "";

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith("Student ID: ")) {
                    id = line.substring("Student ID: ".length());
                } else if (line.startsWith("Full Name: ")) {
                    fullName = line.substring("Full Name: ".length());
                } else if (line.startsWith("Program Name: ")) {
                    programName = line.substring("Program Name: ".length());
                } else if (line.startsWith("UMS Serial: ")) {
                    umsSerial = line.substring("UMS Serial: ".length());
                } else if (line.startsWith("Batch: ")) {
                    batch = line.substring("Batch: ".length());
                } else if (line.startsWith("Admission Session: ")) {
                    admissionSession = line.substring("Admission Session: ".length());
                } else if (line.startsWith("Year: ")) {
                    year = line.substring("Year: ".length());
                } else if (line.startsWith("NID/Passport: ")) {
                    nidOrPassport = line.substring("NID/Passport: ".length());
                } else if (line.startsWith("Gender: ")) {
                    gender = line.substring("Gender: ".length());
                } else if (line.startsWith("Marital Status: ")) {
                    maritalStatus = line.substring("Marital Status: ".length());
                } else if (line.startsWith("Religion: ")) {
                    religion = line.substring("Religion: ".length());
                } else if (line.startsWith("Blood Group: ")) {
                    bloodGroup = line.substring("Blood Group: ".length());
                } else if (line.startsWith("Father's Name: ")) {
                    fatherName = line.substring("Father's Name: ".length());
                } else if (line.startsWith("Occupation: ") && scanner.hasNextLine()) { // Assuming occupation follows father's name
                    fatherOccupation = line.substring("Occupation: ".length());
                } else if (line.startsWith("Mobile: ") && scanner.hasNextLine()) { // Assuming mobile follows father's occupation
                    fatherMobile = line.substring("Mobile: ".length());
                } else if (line.startsWith("Mother's Name: ")) {
                    motherName = line.substring("Mother's Name: ".length());
                } else if (line.startsWith("Occupation: ")) { // This will be mother's occupation
                    motherOccupation = line.substring("Occupation: ".length());
                } else if (line.startsWith("Mobile: ")) { // This will be mother's mobile
                    motherMobile = line.substring("Mobile: ".length());
                } else if (line.startsWith("Present Address: ")) {
                    presentAddress = line.substring("Present Address: ".length());
                } else if (line.startsWith("Permanent Address: ")) {
                    permanentAddress = line.substring("Permanent Address: ".length());
                }
            }
            if (!id.isEmpty()) {
                Student student = new Student(id, fullName, programName);
                student.setUmsSerial(umsSerial);
                student.setBatch(batch);
                student.setAdmissionSession(admissionSession);
                student.setYear(year);
                student.setNidOrPassport(nidOrPassport);
                student.setGender(gender);
                student.setMaritalStatus(maritalStatus);
                student.setReligion(religion);
                student.setBloodGroup(bloodGroup);
                student.setFatherName(fatherName);
                student.setFatherOccupation(fatherOccupation);
                student.setFatherMobile(fatherMobile);
                student.setMotherName(motherName);
                student.setMotherOccupation(motherOccupation);
                student.setMotherMobile(motherMobile);
                student.setPresentAddress(presentAddress);
                student.setPermanentAddress(permanentAddress);
                return student;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

/**
 * Main application class demonstrating INHERITANCE (extends JFrame)
 * Entry point for the Student Management System
 */
public class StudentManagementSEU extends JFrame {

    public StudentManagementSEU() {
        setTitle("Student Management System");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton addStudentButton = new JButton("Add New Student");
        JButton viewStudentsButton = new JButton("View Students");

        panel.add(addStudentButton);
        panel.add(viewStudentsButton);

        add(panel);

        addStudentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                new AddNewStudentForm().setVisible(true);
            }
        });

        viewStudentsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                new ViewStudentsForm().setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new StudentManagementSEU().setVisible(true);
            }
        });
    }
}
