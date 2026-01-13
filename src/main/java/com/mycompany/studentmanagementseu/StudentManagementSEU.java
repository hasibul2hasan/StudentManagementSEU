
package com.mycompany.studentmanagementseu;

import javax.swing.*;
import java.awt.*;

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

        addStudentButton.addActionListener(e -> new AddNewStudentForm().setVisible(true));

        viewStudentsButton.addActionListener(e -> new ViewStudentsForm().setVisible(true));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentManagementSEU().setVisible(true));
    }
}
