package com.mycompany.studentmanagementseu;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class AddNewStudentForm extends JFrame {

    // Student Information
    private JLabel umsSerial;
    private JTextField studentId, programName, batch;
    private JComboBox<String> year;
    private JRadioButton sprint, summer, fall;
    private ButtonGroup admissionSessionGroup;

    // Personal Data
    private JTextField fullName, nidOrPassport;
    private JComboBox<String> gender, maritalStatus, religion, bloodGroup;

    // Family/Guardian
    private JTextField fatherName, fatherOccupation, fatherMobile;
    private JTextField motherName, motherOccupation, motherMobile;

    // Addresses
    private JTextArea presentAddress;
    private JTextArea permanentAddress;
    private String studentIdToEdit;

    public AddNewStudentForm() {
        this(null);
    }

    public AddNewStudentForm(String studentIdToEdit) {
        this.studentIdToEdit = studentIdToEdit;
        if (studentIdToEdit != null) {
            setTitle("Edit Student");
        } else {
            setTitle("Add New Student");
        }
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        mainPanel.add(createSystemInfoPanel());
        mainPanel.add(createPersonalInfoPanel());
        mainPanel.add(createFamilyGuardianPanel());
        mainPanel.add(createAddressPanel());

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addStudent = new JButton(studentIdToEdit != null ? "Update Student" : "Add Student");
        JButton backButton = new JButton("Go Back");

        buttonPanel.add(addStudent);
        buttonPanel.add(backButton);

        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                dispose();
            }
        });

        // Add save functionality here
        addStudent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                saveStudentData();
            }
        });

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        if (studentIdToEdit != null) {
            loadStudentData(studentIdToEdit);
            studentId.setEditable(false);
        }
    }

    private JPanel createSystemInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Student Information"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 5, 2, 5);
        gbc.gridy = 0;

        umsSerial = new JLabel();
        File dataDir = new File("student_data");
        int fileCount = 0;
        if (dataDir.exists() && dataDir.isDirectory()) {
            File[] files = dataDir.listFiles();
            if (files != null) {
                fileCount = files.length;
            }
        }
        umsSerial.setText(String.valueOf(fileCount + 1));
        
        studentId = new JTextField(20);
        programName = new JTextField(20);
        batch = new JTextField(20);
        String[] years = {"2020", "2021", "2022", "2023", "2024", "2025", "2026"};
        year = new JComboBox<>(years);

        sprint = new JRadioButton("Sprint");
        summer = new JRadioButton("Summer");
        fall = new JRadioButton("Fall");
        admissionSessionGroup = new ButtonGroup();
        admissionSessionGroup.add(sprint);
        admissionSessionGroup.add(summer);
        admissionSessionGroup.add(fall);

        JPanel admissionSessionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        admissionSessionPanel.add(sprint);
        admissionSessionPanel.add(summer);
        admissionSessionPanel.add(fall);

        addLabelAndField(panel, "UMS Serial:", umsSerial, gbc);
        addLabelAndField(panel, "Student ID:", studentId, gbc);
        addLabelAndField(panel, "Program Name:", programName, gbc);
        addLabelAndField(panel, "Batch:", batch, gbc);
        addLabelAndField(panel, "Admission Session:", admissionSessionPanel, gbc);
        addLabelAndField(panel, "Year:", year, gbc);

        return panel;
    }

    private JPanel createPersonalInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Personal Data"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 5, 2, 5);
        gbc.gridy = 0;

        fullName = new JTextField(20);
        nidOrPassport = new JTextField(20);

        String[] genders = {"Male", "Female", "Other"};
        gender = new JComboBox<>(genders);

        String[] maritalStatuses = {"Single", "Married", "Divorced", "Widowed"};
        maritalStatus = new JComboBox<>(maritalStatuses);

        String[] religions = {"Islam", "Hinduism", "Christianity", "Buddhism", "Other"};
        religion = new JComboBox<>(religions);

        String[] bloodGroups = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
        bloodGroup = new JComboBox<>(bloodGroups);

        addLabelAndField(panel, "Full Name:", fullName, gbc);
        addLabelAndField(panel, "NID/Passport:", nidOrPassport, gbc);
        addLabelAndField(panel, "Gender:", gender, gbc);
        addLabelAndField(panel, "Marital Status:", maritalStatus, gbc);
        addLabelAndField(panel, "Religion:", religion, gbc);
        addLabelAndField(panel, "Blood Group:", bloodGroup, gbc);

        return panel;
    }

    private JPanel createFamilyGuardianPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Family/Guardian Information"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 5, 2, 5);
        gbc.gridy = 0;

        fatherName = new JTextField(20);
        fatherOccupation = new JTextField(20);
        fatherMobile = new JTextField(20);
        motherName = new JTextField(20);
        motherOccupation = new JTextField(20);
        motherMobile = new JTextField(20);

        addLabelAndField(panel, "Father's Name:", fatherName, gbc);
        addLabelAndField(panel, "Occupation:", fatherOccupation, gbc);
        addLabelAndField(panel, "Mobile:", fatherMobile, gbc);

        gbc.gridy++; // Add a separator
        panel.add(new JSeparator(), gbc);
        gbc.gridy++;

        addLabelAndField(panel, "Mother's Name:", motherName, gbc);
        addLabelAndField(panel, "Occupation:", motherOccupation, gbc);
        addLabelAndField(panel, "Mobile:", motherMobile, gbc);

        return panel;
    }

    private JPanel createAddressPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Address Information"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 5, 2, 5);
        gbc.gridy = 0;

        presentAddress = new JTextArea(3, 20);
        permanentAddress = new JTextArea(3, 20);

        addLabelAndField(panel, "Present Address:", new JScrollPane(presentAddress), gbc);
        addLabelAndField(panel, "Permanent Address:", new JScrollPane(permanentAddress), gbc);

        return panel;
    }

    private void addLabelAndField(JPanel panel, String labelText, JComponent component, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel(labelText), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panel.add(component, gbc);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
    }

    private String getSelectedAdmissionSession() {
        if (sprint.isSelected()) {
            return "Sprint";
        } else if (summer.isSelected()) {
            return "Summer";
        } else if (fall.isSelected()) {
            return "Fall";
        }
        return "";
    }

    private void saveStudentData() {
        String admissionSession = getSelectedAdmissionSession();

        if (studentId.getText().trim().isEmpty() ||
            programName.getText().trim().isEmpty() ||
            batch.getText().trim().isEmpty() ||
            admissionSession.isEmpty() ||
            year.getSelectedItem().toString().isEmpty()) {
            JOptionPane.showMessageDialog(this, "All Student Information fields must be filled out.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String id = studentId.getText().trim();

        if (!id.matches("\\d{13}")) {
            JOptionPane.showMessageDialog(this, "Student ID must be 13 digits and contain only numbers.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String batchNumber = batch.getText().trim();
        if (!batchNumber.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Batch must contain only numbers.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String fullNameText = fullName.getText().trim();
        if (fullNameText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Full Name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (fullNameText.matches(".*\\d.*")) {
            JOptionPane.showMessageDialog(this, "Full Name cannot contain numbers.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String fatherNameText = fatherName.getText().trim();
        if (fatherNameText.matches(".*\\d.*")) {
            JOptionPane.showMessageDialog(this, "Father's Name cannot contain numbers.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String motherNameText = motherName.getText().trim();
        if (motherNameText.matches(".*\\d.*")) {
            JOptionPane.showMessageDialog(this, "Mother's Name cannot contain numbers.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String fatherMobileText = fatherMobile.getText().trim();
        if (!fatherMobileText.matches("\\d*")) {
            JOptionPane.showMessageDialog(this, "Father's Mobile can only contain numbers.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String motherMobileText = motherMobile.getText().trim();
        if (!motherMobileText.matches("\\d*")) {
            JOptionPane.showMessageDialog(this, "Mother's Mobile can only contain numbers.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        File dataDir = new File("student_data");
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }

        if (studentIdToEdit == null) { // Only check for new students
            File studentFileCheck = new File(dataDir, id + ".txt");
            if (studentFileCheck.exists()) {
                JOptionPane.showMessageDialog(this, "Student with ID " + id + " already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        File studentFile = new File(dataDir, id + ".txt");

        try (FileWriter writer = new FileWriter(studentFile)) {
            writer.write("--- Student Information ---\n");
            writer.write("UMS Serial: " + umsSerial.getText() + "\n");
            writer.write("Student ID: " + studentId.getText() + "\n");
            writer.write("Program Name: " + programName.getText() + "\n");
            writer.write("Batch: " + batch.getText() + "\n");
            writer.write("Admission Session: " + getSelectedAdmissionSession() + "\n");
            writer.write("Year: " + year.getSelectedItem().toString() + "\n\n");

            writer.write("--- Personal Data ---\n");
            writer.write("Full Name: " + fullName.getText() + "\n");
            
            writer.write("Gender: " + gender.getSelectedItem().toString() + "\n");
            writer.write("Marital Status: " + maritalStatus.getSelectedItem().toString() + "\n");
            writer.write("Religion: " + religion.getSelectedItem().toString() + "\n");
            writer.write("Blood Group: " + bloodGroup.getSelectedItem().toString() + "\n\n");

            writer.write("--- Family/Guardian Information ---\n");
            writer.write("Father's Name: " + fatherName.getText() + "\n");
            writer.write("Father's Occupation: " + fatherOccupation.getText() + "\n");
            writer.write("Father's Mobile: " + fatherMobile.getText() + "\n");
            writer.write("Mother's Name: " + motherName.getText() + "\n");
            writer.write("Mother's Occupation: " + motherOccupation.getText() + "\n");
            writer.write("Mother's Mobile: " + motherMobile.getText() + "\n\n");

            writer.write("--- Address Information ---\n");
            writer.write("Present Address: " + presentAddress.getText() + "\n");
            writer.write("Permanent Address: " + permanentAddress.getText() + "\n\n");

            if (studentIdToEdit != null) {
                JOptionPane.showMessageDialog(this, "Student data updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Student data saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
            dispose();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving student data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadStudentData(String studentIdToLoad) {
        File studentFile = new File("student_data", studentIdToLoad + ".txt");
        if (!studentFile.exists()) {
            JOptionPane.showMessageDialog(this, "Student data file not found!", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        try {
            List<String> lines = Files.readAllLines(studentFile.toPath());
            for (String line : lines) {
                String[] parts = line.split(": ", 2);
                if (parts.length < 2) continue;
                String key = parts[0].trim();
                String value = parts[1].trim();

                switch (key) {
                    case "UMS Serial": umsSerial.setText(value); break;
                    case "Student ID": studentId.setText(value); break;
                    case "Program Name": programName.setText(value); break;
                    case "Batch": batch.setText(value); break;
                    case "Admission Session":
                        if (value.equals("Sprint")) sprint.setSelected(true);
                        else if (value.equals("Summer")) summer.setSelected(true);
                        else if (value.equals("Fall")) fall.setSelected(true);
                        break;
                    case "Year": year.setSelectedItem(value); break;
                    case "Full Name": fullName.setText(value); break;
                    case "Gender": gender.setSelectedItem(value); break;
                    case "Marital Status": maritalStatus.setSelectedItem(value); break;
                    case "Religion": religion.setSelectedItem(value); break;
                    case "Blood Group": bloodGroup.setSelectedItem(value); break;
                    case "Father's Name": fatherName.setText(value); break;
                    case "Father's Occupation": fatherOccupation.setText(value); break;
                    case "Father's Mobile": fatherMobile.setText(value); break;
                    case "Mother's Name": motherName.setText(value); break;
                    case "Mother's Occupation": motherOccupation.setText(value); break;
                    case "Mother's Mobile": motherMobile.setText(value); break;
                    case "Present Address": presentAddress.setText(value); break;
                    case "Permanent Address": permanentAddress.setText(value); break;
                }
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading student data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
