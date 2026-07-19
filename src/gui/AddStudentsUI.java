package gui;

import javax.swing.*;
import backend.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class AddStudentsUI {

    private JFrame frame;
    String selectedProg;
    String studName;
    String studID;
    String gender;
    int sem;

    public void launchAddStudentsPage() {

        // --------------------------- PARENT FRAME --------------------
        frame = new JFrame("Student Management");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout()); // Use BorderLayout

        // ----------------------------- MAIN PANEL --------------------
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        mainPanel.setBackground(Color.WHITE);

        JLabel title = new JLabel("Add Students");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT); // Center horizontally

        JLabel subtitle = new JLabel("Add new student information");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(Color.GRAY);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT); // Center horizontally

        mainPanel.add(title);
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(subtitle);
        mainPanel.add(Box.createVerticalStrut(25));

        JPanel formGrid = new JPanel(new GridLayout(0, 2, 20, 15)); // yahan par ek 0 by 2 ka ek form create hoga
        formGrid.setBackground(Color.WHITE);

        JTextField Name = new JTextField();
        JTextField StudentId = new JTextField();

        JComboBox<String> semester = new JComboBox<>(
                new String[] { "Select semester", "1", "2", "3", "4", "5", "6", "7", "8" });

        JComboBox<String> gd = new JComboBox<>(new String[] { "Select gender", "Male", "Female" });

        JComboBox<String> program = new JComboBox<>(
                new String[] { "Select program", "Arts", "Science", "Engineering" });

        JButton view_courses = new JButton("<3");

        // action listender for view courses
        view_courses.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                selectedProg = (String) program.getSelectedItem();

                if (selectedProg.equalsIgnoreCase("Select program")) {
                    JOptionPane.showMessageDialog(null, "Please select a program first!");
                } else {
                    ViewCourseUI ad = new ViewCourseUI(selectedProg);
                    ad.launchAddCoursesPage();
                }
            }
        });

        view_courses.setFont(new Font("Segoe UI", Font.BOLD, 16));
        view_courses.setBackground(Color.LIGHT_GRAY);
        view_courses.setForeground(Color.BLACK);
        view_courses.setFocusPainted(false);

        formGrid.add(createField("Student Id", StudentId));
        formGrid.add(createField("Student Name *", Name));
        formGrid.add(createField("Select Program*", program));
        formGrid.add(createField("Select Semester*", semester));
        formGrid.add(createField("View Courses ", view_courses));

        mainPanel.add(formGrid);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(Box.createVerticalStrut(15));

        // Bottom form grid
        JPanel bottomGrid = new JPanel(new GridLayout(0, 2, 20, 15));
        bottomGrid.setBackground(Color.WHITE);

        bottomGrid.add(createField("gender", gd));
        mainPanel.add(bottomGrid);

        // BUTTON row
        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonRow.setBackground(Color.WHITE);
        buttonRow.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // cancel button
        JButton cancelBtn = new JButton("Cancel");

        cancelBtn.setBackground(Color.WHITE);
        cancelBtn.setForeground(Color.BLACK);
        cancelBtn.setFocusPainted(false);

        // save buton
        JButton saveBtn = new JButton("Save");

        saveBtn.setBackground(Color.BLACK);
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFocusPainted(false);

        // action listener for save button
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Reading form values from the form and saving t in variables
                studName = Name.getText().trim();
                studID = StudentId.getText().trim();
                gender = (String) gd.getSelectedItem();
                String selected_Sem = (String) semester.getSelectedItem();
                selectedProg = (String) program.getSelectedItem();

                if (studName.trim().isEmpty() || studID.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all required fields!");
                    return;
                }

                String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz ";
                for (int i = 0; i < studName.length(); i++) {
                    if (letters.indexOf(studName.charAt(i)) == -1) {
                        JOptionPane.showMessageDialog(null, "Student name must contain only alphabets!");
                        return;
                    }
                }

                if (selectedProg.equals("Select program")) {
                    JOptionPane.showMessageDialog(null, "Please select a program!");
                    return;
                }

                if (selected_Sem.equals("Select semester")) {
                    JOptionPane.showMessageDialog(null, "Please select a semester!");
                    return;
                }

                if (gender.equals("Select gender")) {
                    JOptionPane.showMessageDialog(null, "Please select a gender!");
                    return;
                }

                sem = Integer.parseInt(selected_Sem);

                Student sd;

                if (selectedProg.equalsIgnoreCase("Science")) {
                    sd = new ScienceStudent(studID, studName, gender, sem);
                } else if (selectedProg.equalsIgnoreCase("Arts")) {
                    sd = new ArtsStudent(studID, studName, gender, sem);
                } else if (selectedProg.equalsIgnoreCase("Engineering")) {
                    sd = new EngineeringStudent(studID, studName, gender, sem);
                } else {

                    JOptionPane.showMessageDialog(null, "Select program to view saved courses!");
                    sd = null;
                }

                DataStore<Object> dataStore = new DataStore<>();

                ArrayList<Object> allObjects = dataStore.readAll("records.dat"); // read everything fro the file
                RecordList<Object> recordList = new RecordList<>();
                for (Object obj : allObjects)
                    recordList.add(obj);

                for (Student s : recordList.getTotalStudents()) {
                    if (s.getStudentId().equalsIgnoreCase(studID)) {
                        JOptionPane.showMessageDialog(null, "Student ID already exists!");
                        return; // Stop saving
                    }
                }

                if (sd == null) {
                    JOptionPane.showMessageDialog(null,
                            "No object is saved to the file as no program is selected, no object of student is made");
                    return;
                } else {
                    dataStore.saveToFile("records.dat", sd);
                    JOptionPane.showMessageDialog(null, "Student saved successfully!");

                    // CLEAR FIELDS AFTER SAVEing for the nxt input jo ha dalain gy
                    StudentId.setText("");
                    Name.setText("");
                    program.setSelectedIndex(0);
                    semester.setSelectedIndex(0);
                    gd.setSelectedIndex(0);
                }

            }

        });

        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        buttonRow.add(cancelBtn);
        buttonRow.add(saveBtn);

        // -------------------- ADD PANELS TO FRAME --------------------
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.add(buttonRow, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    // ================== FIELD BUILDER METHOD==================
    public JPanel createField(String label, JComponent field) {
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
