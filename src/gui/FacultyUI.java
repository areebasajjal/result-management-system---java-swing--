
package gui;

import backend.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class FacultyUI extends BaseUI {

    private JTable table = new JTable();

    public void launchFacultyPage() {
        launchBasePage(
                "Courses",
                "Course and Faculty Management",
                "Manage Courses and Instructors");
    }

    @Override
    public void loadPage() {

        JPanel facultyBody = new JPanel();
        facultyBody.setBackground(Color.WHITE);
        facultyBody.setLayout(new BoxLayout(facultyBody, BoxLayout.Y_AXIS));
        facultyBody.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // ================= TITLE ROW
        JPanel titleRow = new JPanel();
        titleRow.setBackground(Color.WHITE);
        titleRow.setLayout(new BorderLayout());

        JPanel titleTexts = new JPanel();
        titleTexts.setLayout(new BoxLayout(titleTexts, BoxLayout.Y_AXIS));
        titleTexts.setBackground(Color.WHITE);
        titleRow.add(titleTexts, BorderLayout.WEST);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 0)); // horizontal alignment, spacing 10
        JTextField searchField = new JTextField(" Enter course ID to search or delete...");
        searchField.setPreferredSize(new Dimension(400, 40));
        searchField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        JButton addFacultyBtn = new JButton("+ Add Course ");
        JButton viewAllBtn = new JButton("View All Courses");

        JButton deleteBtn = new JButton("- Delete");

        // action listender for delete button
        deleteBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String deleteId = searchField.getText().trim();

                if (deleteId.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Enter Course Code to delete!");
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you want to delete this record?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION);

                if (confirm != JOptionPane.YES_OPTION)
                    return;

                // Loading all records into RecordList
                DataStore<Object> dataStore = new DataStore<>();
                ArrayList<Object> allRecords = dataStore.readAll("records.dat");

                RecordList<Object> recordList = new RecordList<>();

                for (Object obj : allRecords) {
                    recordList.add(obj);
                }

                // Get count before deletion
                int beforeCount = recordList.getTotalCourses().size();

                recordList.remove(deleteId);

                // Checking if deletion was successful
                int afterCount = recordList.getTotalCourses().size();

                if (beforeCount == afterCount) {
                    if (searchField.getText().equalsIgnoreCase(" Enter course ID to search, delete or view records.")) {

                        JOptionPane.showMessageDialog(null, "Please enter Course Code to delete in search bar");
                    } else {
                        JOptionPane.showMessageDialog(null, "Record not found!");
                    }
                    return;
                }

                // Save updated list back to file
                dataStore.overwriteFile("records.dat", recordList.getAll());

                JOptionPane.showMessageDialog(null, "Record deleted successfully!");

                searchField.setText("");
                refreshTable();
            }
        });

        JButton searchBtn = new JButton("Search Course");

        // action listender for search button
        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String searchId = searchField.getText().trim();

                if (searchId.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Enter Course Code to search!");
                    return;
                }

                DataStore<Object> store = new DataStore<>();
                ArrayList<Object> list = store.readAll("records.dat");

                RecordList<Object> recordList = new RecordList<>();
                for (Object obj : list)
                    recordList.add(obj);

                boolean found = false;

                for (Course c : recordList.getTotalCourses()) {
                    if (c.getCourseCode().equalsIgnoreCase(searchId)) {

                        Object[][] singleRow = {
                                {
                                        c.getCourseCode(),
                                        c.getTitle(),
                                        c.getCreditHours(),
                                        c.getInstructor().getName(),
                                        c.getInstructor().getQualification(),
                                        c.getInstructor().getGender()
                                }
                        };

                        String[] columns = {
                                "Course code", "Course Name", "Credit Hours",
                                "Instructor Name", "Qualification", "Gender"
                        };

                        table.setModel(new DefaultTableModel(singleRow, columns));
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    if (searchField.getText().equalsIgnoreCase(" Enter course ID to search or delete...")) {
                        JOptionPane.showMessageDialog(null, "Please Enter Course Code in the search bar.");
                    } else {
                        JOptionPane.showMessageDialog(null, "No course found with this Course Code!");
                    }

                }
            }
        });

        // action listener for view all
        viewAllBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTable();
            }
        });

        JButton[] buttons = { addFacultyBtn, viewAllBtn, deleteBtn, searchBtn };
        for (JButton btn : buttons) {
            btn.setBackground(Color.BLACK);
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
            buttonPanel.add(btn);
        }

        // action listender for add courses and faculty
        addFacultyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSidebarNavigation();
            }
        });

        titleRow.add(buttonPanel, BorderLayout.EAST);

        facultyBody.add(titleRow);
        facultyBody.add(Box.createVerticalStrut(25));

        JPanel filterRow = new JPanel(new BorderLayout(20, 0));
        filterRow.setBackground(Color.WHITE);

        filterRow.add(searchField, BorderLayout.CENTER);

        facultyBody.add(filterRow);
        facultyBody.add(Box.createVerticalStrut(25));

        // ================= FACULTY TABLE =================

        String[] columns = {
                "Course code", "Course Name", "Credit Hours",
                "Instructor Name", "Qualification", "Gender"
        };

        table.setModel(new DefaultTableModel(loadCourseTableData(), columns));

        table.setRowHeight(35);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        facultyBody.add(scrollPane);

        // ================= ADD TO MAIN CONTENT =================
        mainContent.add(facultyBody, BorderLayout.CENTER);

        // Ensure the layout is updated after adding content
        mainContent.revalidate();
        mainContent.repaint();
    }

    public void handleSidebarNavigation() {
        new AddFacultyandCourseUI().launchAddFacultyPage();
    }

    public Object[][] loadCourseTableData() {

        DataStore<Object> store = new DataStore<>();
        ArrayList<Object> list = store.readAll("records.dat");
        RecordList<Object> recordList = new RecordList<>();
        for (Object obj : list)
            recordList.add(obj);

        ArrayList<Course> courses = recordList.getTotalCourses();

        Object[][] data = new Object[courses.size()][6];

        for (int i = 0; i < courses.size(); i++) {
            Course c = courses.get(i);

            data[i][0] = c.getCourseCode();
            data[i][1] = c.getTitle();
            data[i][2] = c.getCreditHours();
            data[i][3] = c.getInstructor().getName();
            data[i][4] = c.getInstructor().getQualification();
            data[i][5] = c.getInstructor().getGender();
        }

        return data;
    }

    public void refreshTable() {
        // Object[][] newData = loadCourseTableData();

        String[] columns = {
                "Course code", "Course Name", "Credit Hours",
                "Instructor Name", "Qualification", "Gender"
        };

        table.setModel(new DefaultTableModel(loadCourseTableData(), columns));
    }

}
