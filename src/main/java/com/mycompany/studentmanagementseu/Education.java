package com.mycompany.studentmanagementseu;

public class Education {
    private String institution;
    private String roll;
    private double gpa;
    private double scale;
    private String group;
    private String board;
    private int passingYear;

    public Education(String institution, String roll, double gpa, double scale, String group, String board, int passingYear) {
        this.institution = institution;
        this.roll = roll;
        this.gpa = gpa;
        this.scale = scale;
        this.group = group;
        this.board = board;
        this.passingYear = passingYear;
    }

    // Getters and Setters
    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public int getPassingYear() {
        return passingYear;
    }

    public void setPassingYear(int passingYear) {
        this.passingYear = passingYear;
    }

    @Override
    public String toString() {
        return institution + "|" + roll + "|" + gpa + "|" + scale + "|" + group + "|" + board + "|" + passingYear;
    }

    public static Education fromString(String data) {
        if (data == null || data.isEmpty()) {
            return null;
        }
        String[] parts = data.split("\\|");
        if (parts.length < 7) {
            return null; // Or handle error appropriately
        }
        return new Education(parts[0], parts[1], Double.parseDouble(parts[2]), Double.parseDouble(parts[3]), parts[4], parts[5], Integer.parseInt(parts[6]));
    }
}
