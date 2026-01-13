package com.mycompany.studentmanagementseu;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ViewStudentsForm extends JFrame {

    private JTextField searchField;
    private JPanel studentListPanel;
    private List<JPanel> allStudentEntryPanels = new ArrayList<>();

    public ViewStudentsForm() {
        setTitle("View Students");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Search by ID:"));
        searchField = new JTextField(20);
        searchPanel.add(searchField);
        JButton searchButton = new JButton("Search");
        searchPanel.add(searchButton);

        File studentDataDir = new File("student_data");
        String[] studentFiles = studentDataDir.list();

        studentListPanel = new JPanel();
        studentListPanel.setLayout(new BoxLayout(studentListPanel, BoxLayout.Y_AXIS));

        if (studentFiles != null) {
            for (String fileName : studentFiles) {
                if (fileName.toLowerCase().endsWith(".txt")) {
                    String studentId = fileName.substring(0, fileName.lastIndexOf('.'));
                    
                    JPanel studentEntryPanel = createStudentEntryPanel(studentId, fileName, studentDataDir);
                    allStudentEntryPanels.add(studentEntryPanel);
                    studentListPanel.add(studentEntryPanel);
                }
            }
        }

        searchButton.addActionListener(e -> filterStudents());

        JScrollPane scrollPane = new JScrollPane(studentListPanel);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Student IDs"));

        JButton backButton = new JButton("Go Back");
        backButton.addActionListener(e -> dispose());

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(backButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
    }

    private JPanel createStudentEntryPanel(String studentId, String fileName, File studentDataDir) {
        JPanel studentEntryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        studentEntryPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel studentIdLabel = new JLabel(studentId);
        studentIdLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        
        JButton updateButton = new JButton("update");
        JButton deleteButton = new JButton("Delete");
        JButton viewButton = new JButton("View");

        updateButton.addActionListener(e -> {
            AddNewStudentForm updateForm = new AddNewStudentForm(studentId);
            updateForm.setVisible(true);
            // Refresh the list after updateing
            updateForm.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    refreshStudentList();
                }
            });
        });

        deleteButton.addActionListener(e -> {
            int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete " + studentId + "?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirmation == JOptionPane.YES_OPTION) {
                File studentFile = new File(studentDataDir, fileName);
                if (studentFile.delete()) {
                    studentListPanel.remove(studentEntryPanel);
                    allStudentEntryPanels.remove(studentEntryPanel);
                    studentListPanel.revalidate();
                    studentListPanel.repaint();
                    JOptionPane.showMessageDialog(this, studentId + " deleted successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete " + studentId, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        viewButton.addActionListener(e -> {
            try {
                String content = new String(Files.readAllBytes(Paths.get(studentDataDir.getPath(), fileName)));
                JTextArea textArea = new JTextArea(content);
                textArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(400, 300));
                JOptionPane.showMessageDialog(this, scrollPane, "Details for " + studentId, JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Could not read details for " + studentId, "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        studentEntryPanel.add(studentIdLabel);
        studentEntryPanel.add(viewButton);
        studentEntryPanel.add(updateButton);
        studentEntryPanel.add(deleteButton);

        studentEntryPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        studentEntryPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, studentEntryPanel.getPreferredSize().height));
        return studentEntryPanel;
    }

    private void filterStudents() {
        String searchText = searchField.getText().toLowerCase();
        studentListPanel.removeAll();
        for (JPanel studentEntryPanel : allStudentEntryPanels) {
            JLabel label = (JLabel) studentEntryPanel.getComponent(0);
            if (label.getText().toLowerCase().contains(searchText)) {
                studentListPanel.add(studentEntryPanel);
            }
        }
        studentListPanel.revalidate();
        studentListPanel.repaint();
    }

    private void refreshStudentList() {
        studentListPanel.removeAll();
        allStudentEntryPanels.clear();
        File studentDataDir = new File("student_data");
        String[] studentFiles = studentDataDir.list();
        if (studentFiles != null) {
            for (String fileName : studentFiles) {
                if (fileName.toLowerCase().endsWith(".txt")) {
                    String studentId = fileName.substring(0, fileName.lastIndexOf('.'));
                    JPanel studentEntryPanel = createStudentEntryPanel(studentId, fileName, studentDataDir);
                    allStudentEntryPanels.add(studentEntryPanel);
                    studentListPanel.add(studentEntryPanel);
                }
            }
        }
        studentListPanel.revalidate();
        studentListPanel.repaint();
    }
}
