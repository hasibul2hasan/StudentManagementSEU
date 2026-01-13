package com.mycompany.studentmanagementseu;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

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

        addStudent.addActionListener(e -> {
            try {
                saveStudentData();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving student data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
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

    private void saveStudentData() throws IOException {
        // Basic validation
        if (studentId.getText().trim().isEmpty() || fullName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Student ID and Full Name are required.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String currentStudentId = studentId.getText().trim();

        // Check if student ID already exists only when adding a new student
        if (studentIdToEdit == null) {
            File studentFile = new File("student_data/" + currentStudentId + ".txt");
            if (studentFile.exists()) {
                JOptionPane.showMessageDialog(this, "Student ID already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        Student student = new Student(currentStudentId, fullName.getText(), programName.getText());
        student.setUmsSerial(umsSerial.getText());
        student.setBatch(batch.getText());
        if (sprint.isSelected()) {
            student.setAdmissionSession("Sprint");
        } else if (summer.isSelected()) {
            student.setAdmissionSession("Summer");
        } else if (fall.isSelected()) {
            student.setAdmissionSession("Fall");
        }
        student.setYear((String) year.getSelectedItem());

        // Personal Data
        student.setNidOrPassport(nidOrPassport.getText());
        student.setGender((String) gender.getSelectedItem());
        student.setMaritalStatus((String) maritalStatus.getSelectedItem());
        student.setReligion((String) religion.getSelectedItem());
        student.setBloodGroup((String) bloodGroup.getSelectedItem());

        // Family/Guardian
        student.setFatherName(fatherName.getText());
        student.setFatherOccupation(fatherOccupation.getText());
        student.setFatherMobile(fatherMobile.getText());
        student.setMotherName(motherName.getText());
        student.setMotherOccupation(motherOccupation.getText());
        student.setMotherMobile(motherMobile.getText());

        // Addresses
        student.setPresentAddress(presentAddress.getText());
        student.setPermanentAddress(permanentAddress.getText());
        
        DataStorage.saveStudent(student);

        JOptionPane.showMessageDialog(this, "Student data saved successfully!");
        dispose();
    }

    private void loadStudentData(String studentIdToLoad) {
        Student student = DataStorage.loadStudent(studentIdToLoad);
        if (student == null) {
            JOptionPane.showMessageDialog(this, "Student data file not found!", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        umsSerial.setText(student.getUmsSerial());
        studentId.setText(student.getStudentId());
        programName.setText(student.getProgramName());
        batch.setText(student.getBatch());
        String session = student.getAdmissionSession();
        if (session != null) {
            if (session.equals("Sprint")) sprint.setSelected(true);
            else if (session.equals("Summer")) summer.setSelected(true);
            else if (session.equals("Fall")) fall.setSelected(true);
        }
        year.setSelectedItem(student.getYear());
        fullName.setText(student.getFullName());
    }

}