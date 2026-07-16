package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import backend.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class ResultsUI extends BaseUI {

    private JTable table = new JTable();
    private JTextField searchField = new JTextField("Enter Student ID to search...");

    public void launchResultsPage() {
        launchBasePage(
                "Results",
                "Results Management",
                "Manage student results and reports"
        );
    }

    @Override
    public void loadPage() {

        JPanel mainBody = new JPanel();
        mainBody.setBackground(Color.WHITE);
        mainBody.setLayout(new BoxLayout(mainBody, BoxLayout.Y_AXIS));
        mainBody.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // ---------------- SEARCH PANEL ----------------
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(Color.WHITE);
        searchField.setPreferredSize(new Dimension(400, 40));
        searchField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        JButton searchBtn = new JButton("Search");
        searchBtn.setBackground(Color.BLACK);
        searchBtn.setForeground(Color.WHITE);
        searchBtn.setFocusPainted(false);

        searchPanel.add(searchField);
        searchPanel.add(searchBtn);
        mainBody.add(searchPanel);
        mainBody.add(Box.createVerticalStrut(20));

        // ---------------- TABLE ----------------
        String[] columns = {
                "Student ID", "Name", "Semester", "Program",
                "C-1 Marks", "C-2 Marks", "C-3 Marks", "C-4 Marks",
                "GPA", "Percentage", "Grade"
        };

        table.setModel(new DefaultTableModel(loadTranscriptTableData(""), columns));
        table.setRowHeight(35);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        mainBody.add(scrollPane);

        // action listender for search result
        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchId = searchField.getText().trim();
                table.setModel(new DefaultTableModel(loadTranscriptTableData(searchId), columns));
            }
        });

        mainContent.add(mainBody, BorderLayout.CENTER);
        mainContent.revalidate();
        mainContent.repaint();
    }

    // ---------------- HELPER METHODS ----------------
    public Object[][] loadTranscriptTableData(String searchId) {
        DataStore<Student> store = new DataStore<>();
        ArrayList<Object> allRecords = store.readAll("records.dat");
        ArrayList<Student> students = new ArrayList<>();

        for (Object obj : allRecords) {
            if (obj instanceof Student) {
                Student s = (Student) obj;
                if (searchId.isEmpty() || s.getStudentId().equalsIgnoreCase(searchId)) {
                    students.add(s);
                }
            }
        }

        return convertStudentsToTranscriptTableData(students);
    }

    public Object[][] convertStudentsToTranscriptTableData(ArrayList<Student> students) {
    Object[][] data = new Object[students.size()][11]; // 11 columns

    for (int i = 0; i < students.size(); i++) {
        Student s = students.get(i);
        data[i][0] = s.getStudentId();
        data[i][1] = s.getName();
        data[i][2] = s.getSemester();
        data[i][3] = s.getProgram();

        // Get results from transcript
        List<ResultEntry> results =  s.getTranscript().getResults();

        // Fill C-1 to C-4 marks
        for (int j = 0; j < 4; j++) {
            if (j < results.size()) {
                data[i][4 + j] = results.get(j).getMarks();
            } else {
                data[i][4 + j] = ""; // empty if less than 4 courses
            }
        }

        // Fill GPA, Percentage, Grade
        if (results.isEmpty()) {
            data[i][8] = ""; // GPA
            data[i][9] = ""; // Percentage
            data[i][10] = ""; // Grade
        } else {
            data[i][8] = s.getGPA();
            data[i][9] = s.calculatePercentage();
            data[i][10] = s.calculateGrade();
        }
    }

    return data;
}

}