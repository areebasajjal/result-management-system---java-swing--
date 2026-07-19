package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import backend.*;

public class AddTranscriptUI {

    private JFrame frame;
    private Student student;
    private JTextField[] marksFields;

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

    public AddTranscriptUI(Student student) {
        this.student = student;
    }

    public void launchAddTranscriptPage() {

        frame = new JFrame("Add Transcript for Student: " + student.getName());
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        mainPanel.setBackground(Color.WHITE);

        JLabel title = new JLabel("Add Transcript");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(title);
        mainPanel.add(Box.createVerticalStrut(20));

        // ----------------- STUDENT INFO -----------------
        JPanel infoPanel = new JPanel(new GridLayout(0, 2, 15, 10));
        infoPanel.setBackground(Color.WHITE);

        JTextField idField = new JTextField(student.getStudentId());
        idField.setEditable(false);
        JTextField nameField = new JTextField(student.getName());
        nameField.setEditable(false);
        JTextField progField = new JTextField(student.getProgram());
        progField.setEditable(false);
        JTextField semField = new JTextField(String.valueOf(student.getSemester()));
        semField.setEditable(false);

        infoPanel.add(new JLabel("Student ID:"));
        infoPanel.add(idField);
        infoPanel.add(new JLabel("Name:"));
        infoPanel.add(nameField);
        infoPanel.add(new JLabel("Program:"));
        infoPanel.add(progField);
        infoPanel.add(new JLabel("Semester:"));
        infoPanel.add(semField);

        mainPanel.add(infoPanel);
        mainPanel.add(Box.createVerticalStrut(20));

        JPanel coursesPanel = new JPanel(new GridLayout(0, 3, 15, 10));
        coursesPanel.setBackground(Color.WHITE);

        String[] courseTitles;

        if (student.getProgram().equalsIgnoreCase("Arts")) {
            courseTitles = ArtsStudent.getAvailableCoursesForArtsProg();

        } else if (student.getProgram().equalsIgnoreCase("Science")) {
            courseTitles = ScienceStudent.getAvailableCoursesScience();

        } else if (student.getProgram().equalsIgnoreCase("Engineering")) {
            courseTitles = EngineeringStudent.getAvailableCoursesForEngineeringProg();

        } else {
            courseTitles = new String[0]; // n available course. an empty string
        }

        marksFields = new JTextField[courseTitles.length];

        for (int i = 0; i < courseTitles.length; i++) {

            coursesPanel.add(new JLabel(courseTitles[i]));
            coursesPanel.add(new JLabel("Total marks: 100"));

            marksFields[i] = new JTextField();

            coursesPanel.add(marksFields[i]);
        }

        mainPanel.add(coursesPanel);
        mainPanel.add(Box.createVerticalStrut(20));

        // ----------------- BUTTONS panelsssss -----------------
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);

        JButton saveBtn = new JButton("Save");
        saveBtn.setBackground(Color.BLACK);
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFocusPainted(false);

        // action listender for save button
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Validate and parse all marks first
                double[] marks = new double[marksFields.length];
                for (int i = 0; i < marksFields.length; i++) {
                    String text = marksFields[i].getText().trim();
                    if (text.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Please enter marks for all courses.");
                        return;
                    }

                    try {
                        marks[i] = Double.parseDouble(text);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Marks must be a number.");
                        return;
                    }

                    if (marks[i] < 0 || marks[i] > 100) {
                        JOptionPane.showMessageDialog(frame, "Enter valid marks (0-100).");
                        return;
                    }
                }

                // Load all records using RecordList
                DataStore<Object> store = new DataStore<>();
                ArrayList<Object> allObjects = store.readAll("records.dat");

                RecordList<Object> recordList = new RecordList<>();
                for (Object obj : allObjects) {
                    recordList.add(obj);
                }

                ArrayList<Object> items = recordList.getAll();
                boolean studentFound = false;

                // Find and update the student in the list
                for (int j = 0; j < items.size(); j++) {
                    Object obj = items.get(j);
                    if (obj instanceof Student) {
                        Student s = (Student) obj;
                        if (s.getStudentId().equalsIgnoreCase(student.getStudentId())) {
                            studentFound = true;

                            // Clear old transcript
                            s.getTranscript().getResults().clear();

                            // Add new courses and marks
                            for (int i = 0; i < marks.length; i++) {
                                if (i >= courses.length)
                                    break;
                                s.addCourse(courses[i], marks[i]);
                            }

                            // The object is already in the list, so we don't need to 'set' it again
                            // because s is a reference to the object in the list.
                            // But for clarity/safety if we were replacing it:
                            items.set(j, s);
                            break;
                        }
                    }
                }

                // If student not found (new student), add them
                if (!studentFound) {
                    for (int i = 0; i < marks.length; i++) {
                        if (i >= courses.length)
                            break;
                        student.addCourse(courses[i], marks[i]);
                    }
                    recordList.add(student);
                }

                // Overwrite file with updated records (ALL objects)
                store.overwriteFile("records.dat", recordList.getAll());

                JOptionPane.showMessageDialog(frame, "Transcript saved successfully!");
                System.out.println("Transcript saved successfully.");
                frame.dispose();
            }
        });

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setBackground(Color.WHITE);
        cancelBtn.setForeground(Color.BLACK);
        cancelBtn.setFocusPainted(false);

        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        buttonPanel.add(cancelBtn);
        buttonPanel.add(saveBtn);

        mainPanel.add(buttonPanel);

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

}
