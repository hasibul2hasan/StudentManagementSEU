package com.mycompany.studentmanagementseu;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Student {
    private String umsSerial;
    private String studentId;
    private String programName;
    private String batch;
    private String admissionSession;
    private String year;
    private String fullName;

    public Student(String studentId, String fullName, String programName) {
        this.studentId = studentId;
        this.fullName = fullName;
        this.programName = programName;
    }

    // Getters and setters
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
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    @Override
    public String toString() {
        return "ID: " + studentId + ", Name: " + fullName + ", Program: " + programName;
    }
}

class DataStorage {
    private static final String DATA_DIR = "student_data";

    public static void saveStudent(Student student) throws IOException {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, student.getStudentId() + ".txt");
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("--- Student Information ---\n");
            writer.write("UMS Serial: " + student.getUmsSerial() + "\n");
            writer.write("Student ID: " + student.getStudentId() + "\n");
            writer.write("Program Name: " + student.getProgramName() + "\n");
            writer.write("Batch: " + student.getBatch() + "\n");
            writer.write("Admission Session: " + student.getAdmissionSession() + "\n");
            writer.write("Year: " + student.getYear() + "\n\n");
            writer.write("--- Personal Data ---\n");
            writer.write("Full Name: " + student.getFullName() + "\n");
        }
    }

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
                }
            }
            if (!id.isEmpty()) {
                Student student = new Student(id, fullName, programName);
                student.setUmsSerial(umsSerial);
                student.setBatch(batch);
                student.setAdmissionSession(admissionSession);
                student.setYear(year);
                return student;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

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
