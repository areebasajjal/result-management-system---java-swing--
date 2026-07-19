package gui;

import backend.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class AddFacultyandCourseUI {

    private JFrame frame;

    private Course[] courses = new Course[] {
            new Course("CS101", "Object Oriented Programming", 3, null),
            new Course("CS102", "Fundamental Programming", 3, null),
            new Course("CS201", "DSA", 3, null),
            new Course("CS301", "Databases", 3, null),
            new Course("ART101", "Drawing", 2, null),
            new Course("ART102", "English", 2, null),
            new Course("ART201", "Technical and business writing", 2, null),
            new Course("ART301", "Economics", 2, null),
            new Course("ENG101", "Calculus", 3, null),
            new Course("ENG102", "Physics", 3, null),
            new Course("ENG201", "Maths", 3, null),
            new Course("ENG301", "Electronics", 3, null)
    };

    public void launchAddFacultyPage() {

        // -------------------- PARENT FRAME --------------------
        frame = new JFrame("Course & Instructor Management");
        frame.setSize(650, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // -------------------- MAIN PANEL --------------------
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        mainPanel.setBackground(Color.WHITE);

        // Title
        JLabel title = new JLabel("Add Course and Instructor");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));

        JLabel subtitle = new JLabel("Fill in the course and instructor information");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(Color.GRAY);

        mainPanel.add(title);
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(subtitle);
        mainPanel.add(Box.createVerticalStrut(25));

        // -------------------- FORM GRID --------------------
        JPanel formGrid = new JPanel(new GridLayout(0, 2, 20, 15));
        formGrid.setBackground(Color.WHITE);

        // Course fields
        JTextField courseCode = new JTextField();
        courseCode.setEditable(false);

        JComboBox<String> courseTitle = new JComboBox<>(new String[] {
                "Select course",
                // Science courses
                "Object Oriented Programming",
                "Fundamental Programming",
                "DSA",
                "Databases",
                // Arts courses
                "Drawing",
                "English",
                "Technical and business writing",
                "Economics",
                // Engineering courses
                "Calculus",
                "Physics",
                "Maths",
                "Electronics"
        });

        JTextField creditHours = new JTextField();
        creditHours.setEditable(false);

        courseTitle.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedTitle = (String) courseTitle.getSelectedItem();

                // Find the corresponding Course object from the courses array
                for (Course c : courses) {
                    if (c.getTitle().equals(selectedTitle)) {
                        courseCode.setText(c.getCourseCode());
                        creditHours.setText(String.valueOf(c.getCreditHours())); // autofill credit hours
                        break;
                    }
                }

                if ("Select course".equals(selectedTitle)) {
                    courseCode.setText("");
                    creditHours.setText("");
                }
            }
        });

        // Instructor fields
        JTextField instructorName = new JTextField();
        JComboBox<String> gender_ComboBox = new JComboBox<>(new String[] { "Select Gender", "Male", "Female" });

        JComboBox<String> instructorQualification = new JComboBox<>(
                new String[] { "Select qualifcation", "Bachelors", "Masters", "Ph.D" });

        formGrid.add(createField("Course Code *", courseCode)); //// has to be fixed alr defined
        formGrid.add(createField("Course Title *", courseTitle));
        formGrid.add(createField("Credit Hours *", creditHours));
        formGrid.add(createField("Instructor Name *", instructorName));
        formGrid.add(createField("Qualification *", instructorQualification));
        formGrid.add(createField("Gender", gender_ComboBox));

        mainPanel.add(formGrid);
        mainPanel.add(Box.createVerticalStrut(25));

        // -------------------- BUTTONS --------------------
        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonRow.setBackground(Color.WHITE);
        buttonRow.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setBackground(Color.WHITE);
        cancelBtn.setForeground(Color.BLACK);
        cancelBtn.setFocusPainted(false);
        cancelBtn.addActionListener(e -> frame.dispose());

        JButton saveBtn = new JButton("Save");
        saveBtn.setBackground(Color.BLACK);
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFocusPainted(false);

        // save button ka action listender
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Get values
                String C_code = courseCode.getText();
                String C_title = (String) courseTitle.getSelectedItem();
                String c_Hours = creditHours.getText();
                String InstructName = instructorName.getText().trim();
                String qualification = (String) instructorQualification.getSelectedItem();
                String gender = (String) gender_ComboBox.getSelectedItem();

                if (C_code.isEmpty() || c_Hours.isEmpty() || InstructName.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "All fields are required!");
                    return;
                }

                if (C_title.equals("Select course")) {
                    JOptionPane.showMessageDialog(null, "Please select a course!");
                    return;
                }

                String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz ";
                for (int i = 0; i < InstructName.length(); i++) {
                    if (letters.indexOf(InstructName.charAt(i)) == -1) {
                        JOptionPane.showMessageDialog(null, "Student name must contain only alphabets!");
                        return;
                    }
                }

                if (qualification.equals("Select qualifcation")) {
                    JOptionPane.showMessageDialog(null, "Please select a qualification!");
                    return;
                }

                if (gender.equals("Select Gender")) {
                    JOptionPane.showMessageDialog(null, "Please select gender!");
                    return;
                }

                int creditHrs;
                try {
                    creditHrs = Integer.parseInt(c_Hours);
                    if (creditHrs <= 0) {
                        JOptionPane.showMessageDialog(null, "Credit hours must be greater than 0!");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Credit hours must be a valid number!");
                    return;
                }

                CourseInstructor c_Instructor = new CourseInstructor(InstructName, qualification, gender);

                Course course = new Course(C_code, C_title, creditHrs, c_Instructor);

                DataStore<Object> dataStore = new DataStore<>();

                ArrayList<Object> allObjects = dataStore.readAll("records.dat");
                RecordList<Object> recordList = new RecordList<>();
                for (Object obj : allObjects)
                    recordList.add(obj);

                for (Course c : recordList.getTotalCourses()) {
                    if (c.getCourseCode().equalsIgnoreCase(C_code)) {
                        JOptionPane.showMessageDialog(null, "Course Code already exists!");
                        return;
                    }
                }

                dataStore.saveToFile("records.dat", course);

                JOptionPane.showMessageDialog(null, "Course saved successfully!");

                courseCode.setText("");
                courseTitle.setSelectedIndex(0);
                creditHours.setText("");
                instructorName.setText("");
                instructorQualification.setSelectedIndex(0);
                gender_ComboBox.setSelectedIndex(0);

            }
        });

        buttonRow.add(cancelBtn);
        buttonRow.add(saveBtn);

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.add(buttonRow, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private JPanel createField(String label, JComponent field) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));

        field.setPreferredSize(new Dimension(250, 35));

        panel.add(lbl, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);

        return panel;
    }
}