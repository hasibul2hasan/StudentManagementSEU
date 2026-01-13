package com.mycompany.studentmanagementseu;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import javax.swing.AbstractCellEditor;
import javax.swing.table.TableCellEditor;

public class ViewStudentsForm extends JFrame {

    private JTextField searchField;
    private JTable studentTable;
    private DefaultTableModel tableModel;

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

        String[] columnNames = {"Actions", "ID", "Name", "Program"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0; // Only the "Actions" column is editable
            }
        };

        studentTable = new JTable(tableModel);
        studentTable.setRowHeight(30);
        studentTable.getColumn("Actions").setPreferredWidth(240);
        studentTable.getColumn("Actions").setCellRenderer(new ButtonPanelRenderer());
        studentTable.getColumn("Actions").setCellEditor(new ButtonPanelEditor(this));

        refreshStudentList();

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        studentTable.setRowSorter(sorter);

        searchButton.addActionListener(e -> {
            String text = searchField.getText();
            if (text.trim().length() == 0) {
                sorter.setRowFilter(null);
            } else {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 1)); // Case-insensitive search on ID column
            }
        });

        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Students"));

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

    private void refreshStudentList() {
        tableModel.setRowCount(0); // Clear existing data
        List<Student> students = DataStorage.getAllStudents();
        for (Student student : students) {
            Object[] row = {
                "Actions", // Placeholder for buttons
                student.getStudentId(),
                student.getFullName(),
                student.getProgramName()
            };
            tableModel.addRow(row);
        }
    }
}

class ButtonPanel extends JPanel {
    public JButton updateButton = new JButton("Update");
    public JButton deleteButton = new JButton("Delete");
    public JButton viewButton = new JButton("View");

    public ButtonPanel() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
        add(updateButton);
        add(deleteButton);
        add(viewButton);
    }
}

class ButtonPanelRenderer extends ButtonPanel implements TableCellRenderer {
    public ButtonPanelRenderer() {
        super();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            setBackground(table.getSelectionBackground());
        } else {
            setBackground(table.getBackground());
        }
        return this;
    }
}

class ButtonPanelEditor extends AbstractCellEditor implements TableCellEditor {
    private ButtonPanel panel = new ButtonPanel();
    private ViewStudentsForm parentForm;
    private String studentId;

    public ButtonPanelEditor(ViewStudentsForm parentForm) {
        this.parentForm = parentForm;

        panel.updateButton.addActionListener(e -> {
            new AddNewStudentForm(studentId).setVisible(true);
            fireEditingStopped();
        });

        panel.deleteButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(parentForm, "Are you sure you want to delete this student?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    Files.deleteIfExists(Paths.get("student_data", studentId + ".txt"));
                    // A bit of a hack to refresh the table
                    parentForm.dispose();
                    new ViewStudentsForm().setVisible(true);

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(parentForm, "Error deleting student: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            fireEditingStopped();
        });

        panel.viewButton.addActionListener(e -> {
            try {
                String content = new String(Files.readAllBytes(Paths.get("student_data", studentId + ".txt")));
                JTextArea textArea = new JTextArea(content);
                textArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(500, 400));
                JOptionPane.showMessageDialog(parentForm, scrollPane, "Student Details - " + studentId, JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(parentForm, "Could not read details for student ID: " + studentId, "Error", JOptionPane.ERROR_MESSAGE);
            }
            fireEditingStopped();
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.studentId = table.getValueAt(row, 1).toString();
        if (isSelected) {
            panel.setBackground(table.getSelectionBackground());
        } else {
            panel.setBackground(table.getBackground());
        }
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return "Actions";
    }
}
