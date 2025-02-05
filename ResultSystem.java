import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.ArrayList;

public class ResultSystem {
    private static JFrame frame;
    private static JTextField nameField, rollField, regField, courseNameField, courseCodeField, creditField, marksField;
    private static JLabel resultLabel;
    private static JButton calculateResultsButton, nextButton, calculateGpaButton, marksheetButton;
    private static List<Course> courses = new ArrayList<>();
    private static String studentName, studentRoll, studentReg;
    private static JPanel coursePanel, buttonPanel;
    
    public static void resultCalculation(JFrame previousFrame) {
    // public static void main(String[] args) {
        frame = new JFrame("Result System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new GridLayout(6, 2));

        // Student Information Inputs
        frame.add(new JLabel("Student Name:"));
        nameField = new JTextField();
        frame.add(nameField);

        frame.add(new JLabel("Roll:"));
        rollField = new JTextField();
        frame.add(rollField);

        frame.add(new JLabel("Reg:"));
        regField = new JTextField();
        frame.add(regField);

        // Calculate Results Button (Initial Step)
        calculateResultsButton = new JButton("Calculate Results");
        frame.add(calculateResultsButton);

        resultLabel = new JLabel("Enter student details and click 'Calculate Results'");
        frame.add(resultLabel);

        // Panel for course input fields (Initially Hidden)
        coursePanel = new JPanel(new GridLayout(4, 2));
        coursePanel.setVisible(false);

        coursePanel.add(new JLabel("Course Name:"));
        courseNameField = new JTextField();
        coursePanel.add(courseNameField);

        coursePanel.add(new JLabel("Course Code:"));
        courseCodeField = new JTextField();
        coursePanel.add(courseCodeField);

        coursePanel.add(new JLabel("Credit:"));
        creditField = new JTextField();
        coursePanel.add(creditField);

        coursePanel.add(new JLabel("Marks:"));
        marksField = new JTextField();
        coursePanel.add(marksField);

        frame.add(coursePanel);

        // Panel for buttons (Initially Hidden)
        buttonPanel = new JPanel(new GridLayout(1, 3));
        buttonPanel.setVisible(false);

        nextButton = new JButton("Next");
        calculateGpaButton = new JButton("Calculate GPA");
        marksheetButton = new JButton("Marksheet");
        JButton backButton = new JButton("Back");  // Create the Back button here
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();  // Dispose of current window
                previousFrame.setVisible(true);  // Show previous window
            }
        });
        
        buttonPanel.add(backButton);  // Add backButton to the panel
        buttonPanel.add(nextButton);
        buttonPanel.add(calculateGpaButton);
        buttonPanel.add(marksheetButton);

        frame.add(buttonPanel);

        // Button Actions
        calculateResultsButton.addActionListener(e -> showCourseInput());
        nextButton.addActionListener(e -> addCourse());
        calculateGpaButton.addActionListener(e -> calculateGPA());
        marksheetButton.addActionListener(e -> showMarksheet());

        frame.setVisible(true);
    }

    private static void showCourseInput() {
        studentName = nameField.getText();
        studentRoll = rollField.getText();
        studentReg = regField.getText();

        if (studentName.isEmpty() || studentRoll.isEmpty() || studentReg.isEmpty()) {
            resultLabel.setText("Please fill all student details!");
            return;
        }

        coursePanel.setVisible(true);
        buttonPanel.setVisible(true);
        calculateResultsButton.setEnabled(false);
    }

    private static void addCourse() {
        try {
            String courseName = courseNameField.getText();
            String courseCode = courseCodeField.getText();
            int credit = Integer.parseInt(creditField.getText());
            double marks = Double.parseDouble(marksField.getText());
            double gradePoint = getGradePoint(marks);

            courses.add(new Course(courseName, courseCode, credit, marks, gradePoint));

            resultLabel.setText("Course added! Enter next course or click Marksheet");
            clearCourseFields();
        } catch (Exception e) {
            resultLabel.setText("Error: Invalid input");
        }
    }

    private static void calculateGPA() {
        if (courses.isEmpty()) {
            resultLabel.setText("No courses added!");
            return;
        }

        double totalGradePoints = 0;
        int totalCredits = 0;

        for (Course course : courses) {
            totalGradePoints += course.gradePoint * course.credit;
            totalCredits += course.credit;
        }

        double gpa = totalGradePoints / totalCredits;
        resultLabel.setText("GPA: " + String.format("%.2f", gpa));
    }

    private static void showMarksheet() {
        if (courses.isEmpty()) {
            resultLabel.setText("No courses added!");
            return;
        }
    
        // Create a panel to display student info
        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        infoPanel.add(new JLabel("Student Name: " + studentName, SwingConstants.CENTER));
        infoPanel.add(new JLabel("Roll: " + studentRoll + " | Reg: " + studentReg, SwingConstants.CENTER));
        infoPanel.add(new JLabel("--------------------------------------------------", SwingConstants.CENTER));
    
        // Column names for the table
        String[] columns = {"Course", "Code", "Credit", "Marks", "Grade"};
        Object[][] data = new Object[courses.size()][5];
    
        double totalGradePoints = 0;
        int totalCredits = 0;
    
        // Populate data array with course details
        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            data[i][0] = course.name;
            data[i][1] = course.code;
            data[i][2] = course.credit;
            data[i][3] = course.marks;
            data[i][4] = getLetterGrade(course.gradePoint);
    
            totalGradePoints += course.gradePoint * course.credit;
            totalCredits += course.credit;
        }
    
        double cgpa = totalGradePoints / totalCredits;
        String finalCgpa = "Overall CGPA: " + String.format("%.2f", cgpa) + " (" + getLetterGrade(cgpa) + ")";
    
        // Create table
        JTable table = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(table);
    
        // Create panel to hold student info, table, and CGPA label
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(infoPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(new JLabel(finalCgpa, SwingConstants.CENTER), BorderLayout.SOUTH);
    
        // Show in a dialog
        JOptionPane.showMessageDialog(frame, panel, "Marksheet", JOptionPane.INFORMATION_MESSAGE);
    }
    

    private static double getGradePoint(double marks) {
        if (marks >= 80) return 4.0;
        if (marks >= 75) return 3.75;
        if (marks >= 70) return 3.5;
        if (marks >= 65) return 3.25;
        if (marks >= 60) return 3.0;
        if (marks >= 55) return 2.75;
        if (marks >= 50) return 2.5;
        if (marks >= 45) return 2.25;
        if (marks >= 40) return 2.0;
        return 0.0;
    }

    private static String getLetterGrade(double gradePoint) {
        return gradePoint == 4.0 ? "A+" : gradePoint >= 3.75 ? "A" : gradePoint >= 3.5 ? "A-" : gradePoint >= 3.25 ? "B+" :
               gradePoint >= 3.0 ? "B" : gradePoint >= 2.75 ? "B-" : gradePoint >= 2.5 ? "C+" : gradePoint >= 2.25 ? "C" :
               gradePoint >= 2.0 ? "D" : "F";
    }

    private static void clearCourseFields() {
        courseNameField.setText("");
        courseCodeField.setText("");
        creditField.setText("");
        marksField.setText("");
    }
}

class Course {
    String name, code;
    int credit;
    double marks, gradePoint;
    public Course(String name, String code, int credit, double marks, double gradePoint) {
        this.name = name;
        this.code = code;
        this.credit = credit;
        this.marks = marks;
        this.gradePoint = gradePoint;
    }
}
