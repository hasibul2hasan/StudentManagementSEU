package com.mycompany.studentmanagementseu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ViewStudentsForm extends JFrame {

    public ViewStudentsForm() {
        setTitle("View Students");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        File studentDataDir = new File("student_data");
        String[] studentFiles = studentDataDir.list();

        DefaultListModel<String> listModel = new DefaultListModel<>();
        if (studentFiles != null) {
            for (String fileName : studentFiles) {
                if (fileName.toLowerCase().endsWith(".txt")) {
                    listModel.addElement(fileName.substring(0, fileName.lastIndexOf('.')));
                }
            }
        }

        JList<String> studentList = new JList<>(listModel);
        studentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        studentList.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        studentList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    String selectedStudentId = studentList.getSelectedValue();
                    if (selectedStudentId != null) {
                        try {
                            String content = new String(Files.readAllBytes(Paths.get("student_data", selectedStudentId + ".txt")));
                            JTextArea textArea = new JTextArea(content);
                            textArea.setEditable(false);
                            textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
                            JScrollPane scrollPane = new JScrollPane(textArea);
                            
                            JDialog detailsDialog = new JDialog(ViewStudentsForm.this, "Student Details: " + selectedStudentId, true);
                            detailsDialog.setSize(500, 600);
                            detailsDialog.setLocationRelativeTo(ViewStudentsForm.this);
                            detailsDialog.add(scrollPane);
                            detailsDialog.setVisible(true);
                        } catch (IOException e) {
                            JOptionPane.showMessageDialog(ViewStudentsForm.this, "Could not read student data.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(studentList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Student IDs"));

        JButton backButton = new JButton("Go Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(backButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
    }
}
