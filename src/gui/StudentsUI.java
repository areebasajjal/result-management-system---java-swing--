package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import backend.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class StudentsUI extends BaseUI {

    private JTable table = new JTable();
    private JTextField searchField = new JTextField("Enter Student ID to search or delete record...");
    private Student selectedStudent = null;

    public void launchStudentsPage() {
        launchBasePage(
                "Students",
                "Student Management",
                "Manage student records and information");
    }

    @Override
    public void loadPage() {
        // -------------------------------- main body starts
        // --------------------------------------------
        JPanel mainBody = new JPanel();
        mainBody.setBackground(Color.WHITE);
        mainBody.setLayout(new BoxLayout(mainBody, BoxLayout.Y_AXIS));
        mainBody.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // ================== yahan par main BUTTON PANEL ban raha hai
        // ======================
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);

        // Add Student Button cretaing
        JButton addStudentBtn = new JButton("+ Add Students");
        addStudentBtn.setBackground(Color.BLACK);
        addStudentBtn.setForeground(Color.WHITE);
        addStudentBtn.setFocusPainted(false);
        addStudentBtn.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));

        // action listener for add student button
        addStudentBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSidebarNavigation();
            }
        });

        buttonPanel.add(addStudentBtn); // adding add students button to the button pannl

        // Add Transcript Button
        JButton addTranscriptBtn = new JButton("+ Add Transcript");
        addTranscriptBtn.setBackground(Color.BLACK);
        addTranscriptBtn.setForeground(Color.WHITE);
        addTranscriptBtn.setFocusPainted(false);
        addTranscriptBtn.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));

        // action listener for add transcript button
        addTranscriptBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAddTranscript();
            }
        });

        buttonPanel.add(addTranscriptBtn); // adding transcript button to the panel

        // view transcript button
        JButton viewTBtn = new JButton("View Transcript");
        viewTBtn.setBackground(Color.BLACK);
        viewTBtn.setForeground(Color.WHITE);
        viewTBtn.setFocusPainted(false);
        viewTBtn.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));

        // action listender for view transcript button
        viewTBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewTranscript();
            }
        });

        buttonPanel.add(viewTBtn); // adding view transcript to the button panel

        // Delete Student Button
        JButton deleteStudentBtn = new JButton("- Delete Student");
        deleteStudentBtn.setBackground(Color.BLACK);
        deleteStudentBtn.setForeground(Color.WHITE);
        deleteStudentBtn.setFocusPainted(false);
        deleteStudentBtn.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));

        // action listender for delete student button
        deleteStudentBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteStudent();
            }
        });

        buttonPanel.add(deleteStudentBtn); // adding deleteing button to thr button panel

        // Search Button
        JButton searchBtn = new JButton("Search Student");
        searchBtn.setBackground(Color.BLACK);
        searchBtn.setForeground(Color.WHITE);
        searchBtn.setFocusPainted(false);
        searchBtn.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));

        // action listender for search student button
        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchStudent();
            }
        });

        buttonPanel.add(searchBtn); // adding the search button on our panel

        // View All Button
        JButton viewAllBtn = new JButton("View All Students");
        viewAllBtn.setBackground(Color.BLACK);
        viewAllBtn.setForeground(Color.WHITE);
        viewAllBtn.setFocusPainted(false);
        viewAllBtn.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));

        // action listender for view all stds button
        viewAllBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshStudentTable();
            }
        });

        buttonPanel.add(viewAllBtn); // adding view all btn to the panel

        // View course Button
        JButton viewCourseBtn = new JButton("View Courses");
        viewCourseBtn.setBackground(Color.BLACK);
        viewCourseBtn.setForeground(Color.WHITE);
        viewCourseBtn.setFocusPainted(false);
        viewCourseBtn.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));

        // action listender for view course button
        viewCourseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewAssignedCoursesByProgram();
            }
        });

        buttonPanel.add(viewCourseBtn);

        mainBody.add(buttonPanel); // button panel ko main body par add ker diya over here
        mainBody.add(Box.createVerticalStrut(20));

        // ============================== SEARCH FIELD ==================
        JPanel searchPanel = new JPanel(new BorderLayout());

        searchPanel.setBackground(Color.WHITE);
        searchField.setPreferredSize(new Dimension(400, 40));
        searchField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        searchPanel.add(searchField, BorderLayout.CENTER);
        mainBody.add(searchPanel);
        mainBody.add(Box.createVerticalStrut(20));

        // ================================ STUDENT TABLE ==================
        String[] columns = {
                "Student ID", "Name", "Program",
                "Course 1", "Course 2", "Course 3", "Course 4",
                "Semester", "Gender"
        };

        table.setModel(new DefaultTableModel(loadStudentTableData(), columns));
        table.setRowHeight(35);

        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        mainBody.add(scrollPane);

        mainContent.add(mainBody, BorderLayout.CENTER);

        mainContent.revalidate(); // Ask the panel to recalculate its layout after adding/removing components
        mainContent.repaint(); // Refresh the panel visually so all changes appear on the screen

    }

    // ================== MAIN BUTTONS METHODS ==================

    public void handleSidebarNavigation() { // add students screen opens via this method
        new AddStudentsUI().launchAddStudentsPage();
    }

    public void openAddTranscript() { // opening add transcript screen for the id user enters in the search bar
        String searchId = searchField.getText().trim();
        if (searchId.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please search for a student first!");
            return;
        }

        selectedStudent = null;
        DataStore<Object> store = new DataStore<>();
        ArrayList<Object> list = store.readAll("records.dat");
        RecordList<Object> recordList = new RecordList<>();
        for (Object obj : list)
            recordList.add(obj);

        for (Student s : recordList.getTotalStudents()) {
            if (s.getStudentId().equalsIgnoreCase(searchId)) {
                selectedStudent = s;
                break;
            }
        }

        if (selectedStudent != null) {
            AddTranscriptUI transcriptUI = new AddTranscriptUI(selectedStudent);
            transcriptUI.launchAddTranscriptPage();
        } else {
            JOptionPane.showMessageDialog(null, "Student not found!");
        }
    }

    public void deleteStudent() { // deleting studens via their id
        String deleteId = searchField.getText().trim();
        if (deleteId.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Enter Student ID to delete!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to delete this student?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION)
            return;

        DataStore<Object> dataStore = new DataStore<>();
        ArrayList<Object> allRecords = dataStore.readAll("records.dat");
        RecordList<Object> recordList = new RecordList<>();

        for (Object obj : allRecords)
            recordList.add(obj);

        int beforeCount = recordList.getTotalStudents().size();
        recordList.remove(deleteId);
        int afterCount = recordList.getTotalStudents().size();

        if (beforeCount == afterCount) {
            JOptionPane.showMessageDialog(null, "Student not found!");
            return;
        }

        dataStore.overwriteFile("records.dat", recordList.getAll());
        JOptionPane.showMessageDialog(null, "Student deleted successfully!");
        searchField.setText("");
        refreshStudentTable();
    }

    public void searchStudent() { // searching students via their id
        String searchId = searchField.getText().trim();
        if (searchId.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Enter Student ID to search!");
            return;
        }

        DataStore<Object> store = new DataStore<>();
        ArrayList<Object> list = store.readAll("records.dat");

        RecordList<Object> recordList = new RecordList<>();
        for (Object obj : list)
            recordList.add(obj);

        ArrayList<Student> foundStudents = new ArrayList<>();

        for (Student s : recordList.getTotalStudents()) {
            if (s.getStudentId().equalsIgnoreCase(searchId)) {
                foundStudents.add(s);
                break;
            }
        }

        if (foundStudents.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No student found with this ID!");
            return;
        }

        Object[][] data = convertStudentsToTableData(foundStudents);
        String[] columns = {
                "Student ID", "Name", "Program",
                "Course 1", "Course 2", "Course 3", "Course 4",
                "Semester", "Gender"
        };
        table.setModel(new DefaultTableModel(data, columns));
    }

    public void viewTranscript() { // view transcrips method
        String searchId = searchField.getText().trim();
        if (searchId.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please search for a student first!");
            return;
        }

        selectedStudent = null;
        DataStore<Object> store = new DataStore<>();
        ArrayList<Object> list = store.readAll("records.dat");

        RecordList<Object> recordList = new RecordList<>();
        for (Object obj : list)
            recordList.add(obj);

        for (Student s : recordList.getTotalStudents()) {
            if (s.getStudentId().equalsIgnoreCase(searchId)) {
                selectedStudent = s;
                break;
            }
        }

        if (selectedStudent == null) {
            JOptionPane.showMessageDialog(null, "Student not found!");
            return;
        }

        Student s = selectedStudent;

        // READ-ONLY FIELDS ---------- <3
        JTextField idField = new JTextField(s.getStudentId());
        JTextField nameField = new JTextField(s.getName());
        JTextField progField = new JTextField(s.getProgram());
        JTextField semesterField = new JTextField(String.valueOf(s.getSemester()));
        JTextField gpaField = new JTextField(String.valueOf(s.getGPA()));
        JTextField percentageField = new JTextField(String.valueOf(s.calculatePercentage()));
        JTextField totalField = new JTextField(String.valueOf(s.calculateTotal()));
        JTextField gradeField = new JTextField(s.calculateGrade());

        JTextField[] fields = { idField, nameField, progField, semesterField, gpaField, percentageField, totalField,
                gradeField };

        for (JTextField f : fields) {
            f.setEditable(false);
        }

        // PANEL ----------
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.add(new JLabel("Student ID:"));
        panel.add(idField);
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Program:"));
        panel.add(progField);
        panel.add(new JLabel("Semester:"));
        panel.add(semesterField);
        panel.add(new JLabel("GPA:"));
        panel.add(gpaField);
        panel.add(new JLabel("Percentage:"));
        panel.add(percentageField);
        panel.add(new JLabel("obtained marks:"));
        panel.add(totalField);
        panel.add(new JLabel("Grade:"));
        panel.add(gradeField);

        JOptionPane.showMessageDialog(null, panel, "Student Transcript", JOptionPane.INFORMATION_MESSAGE);
    }

    public void refreshStudentTable() { // view all button ki functionality. loads all the previous entries
        String[] columns = {
                "Student ID", "Name", "Program",
                "Course 1", "Course 2", "Course 3", "Course 4",
                "Semester", "Gender"
        };

        table.setModel(new DefaultTableModel(loadStudentTableData(), columns));
    }

    public Object[][] loadStudentTableData() {
        DataStore<Object> store = new DataStore<>();
        ArrayList<Object> list = store.readAll("records.dat");

        RecordList<Object> recordList = new RecordList<>();
        for (Object obj : list)
            recordList.add(obj);

        ArrayList<Student> students = recordList.getTotalStudents();

        return convertStudentsToTableData(students);
    }

    public Object[][] convertStudentsToTableData(ArrayList<Student> students) {

        Object[][] data = new Object[students.size()][9];

        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            data[i][0] = s.getStudentId();
            data[i][1] = s.getName();
            data[i][2] = s.getProgram();

            String[] courses;
            if (s.getProgram().equalsIgnoreCase("Science")) {
                courses = ScienceStudent.getAvailableCoursesScience();
            } else if (s.getProgram().equalsIgnoreCase("Arts")) {
                courses = ArtsStudent.getAvailableCoursesForArtsProg();
            } else {
                courses = EngineeringStudent.getAvailableCoursesForEngineeringProg();
            }

            for (int j = 0; j < 4; j++) {
                if (j < courses.length)
                    data[i][3 + j] = courses[j];
                else
                    data[i][3 + j] = "";
            }

            data[i][7] = s.getSemester();
            data[i][8] = s.getGender();
        }

        return data;
    }

    public void viewAssignedCoursesByProgram() {
        String studentId = searchField.getText().trim();

        if (studentId.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter Student ID to view courses!");
            return;
        }

        DataStore<Object> store = new DataStore<>();
        ArrayList<Object> allRecords = store.readAll("records.dat");

        RecordList<Object> recordList = new RecordList<>();
        for (Object obj : allRecords)
            recordList.add(obj);

        Student selectedStudent = null;

        for (Student s : recordList.getTotalStudents()) {
            if (s.getStudentId().equalsIgnoreCase(studentId)) {
                selectedStudent = s;
                break;
            }
        }

        if (selectedStudent == null) {
            JOptionPane.showMessageDialog(null, "Student not found!");
            return;
        }

        String[] courses;
        String program = selectedStudent.getProgram();
        if (program.equalsIgnoreCase("Science")) {
            courses = ScienceStudent.getAvailableCoursesScience();
        } else if (program.equalsIgnoreCase("Arts")) {
            courses = ArtsStudent.getAvailableCoursesForArtsProg();
        } else if (program.equalsIgnoreCase("Engineering")) {
            courses = EngineeringStudent.getAvailableCoursesForEngineeringProg();
        } else {
            JOptionPane.showMessageDialog(null, "Unknown program for this student!");
            return;
        }

        ArrayList<String> courseList = new ArrayList<>();

        for (String c : courses) {
            courseList.add(c);
        }

        String[] coursesArray = courseList.toArray(new String[0]);
        JOptionPane.showMessageDialog(null, coursesArray,
                "Assigned Courses for " + selectedStudent.getName(),
                JOptionPane.INFORMATION_MESSAGE);
    }

}
