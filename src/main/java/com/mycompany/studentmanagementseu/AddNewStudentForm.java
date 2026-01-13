package com.mycompany.studentmanagementseu;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

public class AddNewStudentForm extends JFrame {

    // Student Information
    private JLabel umsSerial;
    private JTextField studentId, programName, batch;
    private JComboBox<String> year;
    private JRadioButton sprint, summer, fall;
    private ButtonGroup admissionSessionGroup;

    // Personal Data
    private JTextField fullName, dateOfBirth, placeOfBirth, nationality, nidOrPassport;
    private JComboBox<String> gender, maritalStatus, religion, bloodGroup;

    // Family/Guardian
    private JTextField fatherName, fatherOccupation, fatherDesignation, fatherMobile, fatherEmail;
    private JTextField motherName, motherOccupation, motherDesignation, motherMobile, motherEmail;
    private JTextField localGuardianName, localGuardianRelationship, localGuardianContact;

    // Addresses
    private JTextField presentAddressHouse, presentAddressRoad, presentAddressPS, presentAddressDistrict;
    private JTextField permanentAddressHouse, permanentAddressRoad, permanentAddressPS, permanentAddressDistrict;

    // Financial/Emergency
    private JTextField monthlyFamilyIncome, emergencyContactName, emergencyContactRelation, emergencyContactAddress, emergencyContactMobile;

    // Education History
    private JTextField sscInstitution, sscRoll, sscGpa, sscScale, sscGroup, sscBoard, sscPassingYear;
    private JTextField hscInstitution, hscRoll, hscGpa, hscScale, hscGroup, hscBoard, hscPassingYear;

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
        mainPanel.add(createFinancialEmergencyPanel());
        mainPanel.add(createEducationPanel("SSC Details", sscInstitution = new JTextField(20), sscRoll = new JTextField(20), sscGpa = new JTextField(20), sscScale = new JTextField(20), sscGroup = new JTextField(20), sscBoard = new JTextField(20), sscPassingYear = new JTextField(20)));
        mainPanel.add(createEducationPanel("HSC Details", hscInstitution = new JTextField(20), hscRoll = new JTextField(20), hscGpa = new JTextField(20), hscScale = new JTextField(20), hscGroup = new JTextField(20), hscBoard = new JTextField(20), hscPassingYear = new JTextField(20)));

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addStudent = new JButton("Add Student");
        JButton backButton = new JButton("Go Back");

        buttonPanel.add(addStudent);
        buttonPanel.add(backButton);

        backButton.addActionListener(e -> dispose());

        // Add save functionality here
        addStudent.addActionListener(e -> {
            saveStudentData();
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
        dateOfBirth = new JTextField(20);
        placeOfBirth = new JTextField(20);
        nationality = new JTextField(20);
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
        addLabelAndField(panel, "Date of Birth:", dateOfBirth, gbc);
        addLabelAndField(panel, "Place of Birth:", placeOfBirth, gbc);
        addLabelAndField(panel, "Nationality:", nationality, gbc);
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
        fatherDesignation = new JTextField(20);
        fatherMobile = new JTextField(20);
        fatherEmail = new JTextField(20);
        motherName = new JTextField(20);
        motherOccupation = new JTextField(20);
        motherDesignation = new JTextField(20);
        motherMobile = new JTextField(20);
        motherEmail = new JTextField(20);
        localGuardianName = new JTextField(20);
        localGuardianRelationship = new JTextField(20);
        localGuardianContact = new JTextField(20);

        addLabelAndField(panel, "Father's Name:", fatherName, gbc);
        addLabelAndField(panel, "Occupation:", fatherOccupation, gbc);
        addLabelAndField(panel, "Designation:", fatherDesignation, gbc);
        addLabelAndField(panel, "Mobile:", fatherMobile, gbc);
        addLabelAndField(panel, "Email:", fatherEmail, gbc);

        gbc.gridy++; // Add a separator
        panel.add(new JSeparator(), gbc);
        gbc.gridy++;

        addLabelAndField(panel, "Mother's Name:", motherName, gbc);
        addLabelAndField(panel, "Occupation:", motherOccupation, gbc);
        addLabelAndField(panel, "Designation:", motherDesignation, gbc);
        addLabelAndField(panel, "Mobile:", motherMobile, gbc);
        addLabelAndField(panel, "Email:", motherEmail, gbc);

        gbc.gridy++; // Add a separator
        panel.add(new JSeparator(), gbc);
        gbc.gridy++;

        addLabelAndField(panel, "Local Guardian Name:", localGuardianName, gbc);
        addLabelAndField(panel, "Relationship:", localGuardianRelationship, gbc);
        addLabelAndField(panel, "Contact:", localGuardianContact, gbc);

        return panel;
    }

    private JPanel createAddressPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Address Information"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 5, 2, 5);
        gbc.gridy = 0;

        presentAddressHouse = new JTextField(20);
        presentAddressRoad = new JTextField(20);
        presentAddressPS = new JTextField(20);
        presentAddressDistrict = new JTextField(20);
        permanentAddressHouse = new JTextField(20);
        permanentAddressRoad = new JTextField(20);
        permanentAddressPS = new JTextField(20);
        permanentAddressDistrict = new JTextField(20);

        addLabelAndField(panel, "Present Address (House):", presentAddressHouse, gbc);
        addLabelAndField(panel, "Road:", presentAddressRoad, gbc);
        addLabelAndField(panel, "Police Station:", presentAddressPS, gbc);
        addLabelAndField(panel, "District:", presentAddressDistrict, gbc);

        gbc.gridy++; // Add a separator
        panel.add(new JSeparator(), gbc);
        gbc.gridy++;

        addLabelAndField(panel, "Permanent Address (House):", permanentAddressHouse, gbc);
        addLabelAndField(panel, "Road:", permanentAddressRoad, gbc);
        addLabelAndField(panel, "Police Station:", permanentAddressPS, gbc);
        addLabelAndField(panel, "District:", permanentAddressDistrict, gbc);

        return panel;
    }

    private JPanel createFinancialEmergencyPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Financial & Emergency Information"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 5, 2, 5);
        gbc.gridy = 0;

        monthlyFamilyIncome = new JTextField(20);
        emergencyContactName = new JTextField(20);
        emergencyContactRelation = new JTextField(20);
        emergencyContactAddress = new JTextField(20);
        emergencyContactMobile = new JTextField(20);

        addLabelAndField(panel, "Monthly Family Income:", monthlyFamilyIncome, gbc);
        addLabelAndField(panel, "Emergency Contact Name:", emergencyContactName, gbc);
        addLabelAndField(panel, "Relation:", emergencyContactRelation, gbc);
        addLabelAndField(panel, "Address:", emergencyContactAddress, gbc);
        addLabelAndField(panel, "Mobile:", emergencyContactMobile, gbc);

        return panel;
    }

    private JPanel createEducationPanel(String title, JTextField institution, JTextField roll, JTextField gpa, JTextField scale, JTextField group, JTextField board, JTextField passingYear) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder(title));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 5, 2, 5);
        gbc.gridy = 0;

        addLabelAndField(panel, "Institution:", institution, gbc);
        addLabelAndField(panel, "Roll:", roll, gbc);
        addLabelAndField(panel, "GPA:", gpa, gbc);
        addLabelAndField(panel, "Scale:", scale, gbc);
        addLabelAndField(panel, "Group:", group, gbc);
        addLabelAndField(panel, "Board:", board, gbc);
        addLabelAndField(panel, "Passing Year:", passingYear, gbc);

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
            writer.write("Date of Birth: " + dateOfBirth.getText() + "\n");
            writer.write("Place of Birth: " + placeOfBirth.getText() + "\n");
            writer.write("Nationality: " + nationality.getText() + "\n");
            writer.write("NID/Passport: " + nidOrPassport.getText() + "\n");
            writer.write("Gender: " + gender.getSelectedItem().toString() + "\n");
            writer.write("Marital Status: " + maritalStatus.getSelectedItem().toString() + "\n");
            writer.write("Religion: " + religion.getSelectedItem().toString() + "\n");
            writer.write("Blood Group: " + bloodGroup.getSelectedItem().toString() + "\n\n");

            writer.write("--- Family/Guardian Information ---\n");
            writer.write("Father's Name: " + fatherName.getText() + "\n");
            writer.write("Father's Occupation: " + fatherOccupation.getText() + "\n");
            writer.write("Father's Designation: " + fatherDesignation.getText() + "\n");
            writer.write("Father's Mobile: " + fatherMobile.getText() + "\n");
            writer.write("Father's Email: " + fatherEmail.getText() + "\n");
            writer.write("Mother's Name: " + motherName.getText() + "\n");
            writer.write("Mother's Occupation: " + motherOccupation.getText() + "\n");
            writer.write("Mother's Designation: " + motherDesignation.getText() + "\n");
            writer.write("Mother's Mobile: " + motherMobile.getText() + "\n");
            writer.write("Mother's Email: " + motherEmail.getText() + "\n");
            writer.write("Local Guardian Name: " + localGuardianName.getText() + "\n");
            writer.write("Local Guardian Relationship: " + localGuardianRelationship.getText() + "\n");
            writer.write("Local Guardian Contact: " + localGuardianContact.getText() + "\n\n");

            writer.write("--- Address Information ---\n");
            writer.write("Present Address (House): " + presentAddressHouse.getText() + "\n");
            writer.write("Present Address (Road): " + presentAddressRoad.getText() + "\n");
            writer.write("Present Address (Police Station): " + presentAddressPS.getText() + "\n");
            writer.write("Present Address (District): " + presentAddressDistrict.getText() + "\n");
            writer.write("Permanent Address (House): " + permanentAddressHouse.getText() + "\n");
            writer.write("Permanent Address (Road): " + permanentAddressRoad.getText() + "\n");
            writer.write("Permanent Address (Police Station): " + permanentAddressPS.getText() + "\n");
            writer.write("Permanent Address (District): " + permanentAddressDistrict.getText() + "\n\n");

            writer.write("--- Financial & Emergency Information ---\n");
            writer.write("Monthly Family Income: " + monthlyFamilyIncome.getText() + "\n");
            writer.write("Emergency Contact Name: " + emergencyContactName.getText() + "\n");
            writer.write("Emergency Contact Relation: " + emergencyContactRelation.getText() + "\n");
            writer.write("Emergency Contact Address: " + emergencyContactAddress.getText() + "\n");
            writer.write("Emergency Contact Mobile: " + emergencyContactMobile.getText() + "\n\n");

            writer.write("--- SSC Details ---\n");
            writer.write("Institution: " + sscInstitution.getText() + "\n");
            writer.write("Roll: " + sscRoll.getText() + "\n");
            writer.write("GPA: " + sscGpa.getText() + "\n");
            writer.write("Scale: " + sscScale.getText() + "\n");
            writer.write("Group: " + sscGroup.getText() + "\n");
            writer.write("Board: " + sscBoard.getText() + "\n");
            writer.write("Passing Year: " + sscPassingYear.getText() + "\n\n");

            writer.write("--- HSC Details ---\n");
            writer.write("Institution: " + hscInstitution.getText() + "\n");
            writer.write("Roll: " + hscRoll.getText() + "\n");
            writer.write("GPA: " + hscGpa.getText() + "\n");
            writer.write("Scale: " + hscScale.getText() + "\n");
            writer.write("Group: " + hscGroup.getText() + "\n");
            writer.write("Board: " + hscBoard.getText() + "\n");
            writer.write("Passing Year: " + hscPassingYear.getText() + "\n");

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
                    case "Date of Birth": dateOfBirth.setText(value); break;
                    case "Place of Birth": placeOfBirth.setText(value); break;
                    case "Nationality": nationality.setText(value); break;
                    case "NID/Passport": nidOrPassport.setText(value); break;
                    case "Gender": gender.setSelectedItem(value); break;
                    case "Marital Status": maritalStatus.setSelectedItem(value); break;
                    case "Religion": religion.setSelectedItem(value); break;
                    case "Blood Group": bloodGroup.setSelectedItem(value); break;
                    case "Father's Name": fatherName.setText(value); break;
                    case "Father's Occupation": fatherOccupation.setText(value); break;
                    case "Father's Designation": fatherDesignation.setText(value); break;
                    case "Father's Mobile": fatherMobile.setText(value); break;
                    case "Father's Email": fatherEmail.setText(value); break;
                    case "Mother's Name": motherName.setText(value); break;
                    case "Mother's Occupation": motherOccupation.setText(value); break;
                    case "Mother's Designation": motherDesignation.setText(value); break;
                    case "Mother's Mobile": motherMobile.setText(value); break;
                    case "Mother's Email": motherEmail.setText(value); break;
                    case "Local Guardian Name": localGuardianName.setText(value); break;
                    case "Local Guardian Relationship": localGuardianRelationship.setText(value); break;
                    case "Local Guardian Contact": localGuardianContact.setText(value); break;
                    case "Present Address (House)": presentAddressHouse.setText(value); break;
                    case "Present Address (Road)": presentAddressRoad.setText(value); break;
                    case "Present Address (Police Station)": presentAddressPS.setText(value); break;
                    case "Present Address (District)": presentAddressDistrict.setText(value); break;
                    case "Permanent Address (House)": permanentAddressHouse.setText(value); break;
                    case "Permanent Address (Road)": permanentAddressRoad.setText(value); break;
                    case "Permanent Address (Police Station)": permanentAddressPS.setText(value); break;
                    case "Permanent Address (District)": permanentAddressDistrict.setText(value); break;
                    case "Monthly Family Income": monthlyFamilyIncome.setText(value); break;
                    case "Emergency Contact Name": emergencyContactName.setText(value); break;
                    case "Emergency Contact Relation": emergencyContactRelation.setText(value); break;
                    case "Emergency Contact Address": emergencyContactAddress.setText(value); break;
                    case "Emergency Contact Mobile": emergencyContactMobile.setText(value); break;
                }
            }

            // Separate logic for education details as they are in sections
            List<String> sscLines = lines.stream().dropWhile(l -> !l.equals("--- SSC Details ---")).skip(1).takeWhile(l -> !l.equals("--- HSC Details ---")).collect(Collectors.toList());
            List<String> hscLines = lines.stream().dropWhile(l -> !l.equals("--- HSC Details ---")).skip(1).collect(Collectors.toList());

            for (String line : sscLines) {
                String[] parts = line.split(": ", 2);
                 if (parts.length < 2) continue;
                String key = parts[0].trim();
                String value = parts[1].trim();
                switch (key) {
                    case "Institution": sscInstitution.setText(value); break;
                    case "Roll": sscRoll.setText(value); break;
                    case "GPA": sscGpa.setText(value); break;
                    case "Scale": sscScale.setText(value); break;
                    case "Group": sscGroup.setText(value); break;
                    case "Board": sscBoard.setText(value); break;
                    case "Passing Year": sscPassingYear.setText(value); break;
                }
            }

            for (String line : hscLines) {
                String[] parts = line.split(": ", 2);
                 if (parts.length < 2) continue;
                String key = parts[0].trim();
                String value = parts[1].trim();
                switch (key) {
                    case "Institution": hscInstitution.setText(value); break;
                    case "Roll": hscRoll.setText(value); break;
                    case "GPA": hscGpa.setText(value); break;
                    case "Scale": hscScale.setText(value); break;
                    case "Group": hscGroup.setText(value); break;
                    case "Board": hscBoard.setText(value); break;
                    case "Passing Year": hscPassingYear.setText(value); break;
                }
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading student data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
