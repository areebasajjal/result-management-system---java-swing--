package gui;

import backend.DataStore;
import backend.RecordList;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DashboardUI extends BaseUI {

    public void launchDashboardPage() {
        launchBasePage(
                "Dashboard",
                "Dashboard",
                "Overview of students and faculty statistics");
    }

    @Override
    public void loadPage() {

        // ================================ DASHBOARD BODY (CENTER)
        // =================================================
        JPanel dashboardBody = new JPanel();
        dashboardBody.setBackground(Color.WHITE);
        dashboardBody.setLayout(new BoxLayout(dashboardBody, BoxLayout.Y_AXIS));
        dashboardBody.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // -------------------- STAT CARDS ROW -------------------------------------
        JPanel statsRow = new JPanel(new GridLayout(1, 4, 20, 0));
        statsRow.setBackground(Color.WHITE);

        DataStore<Object> dataStore = new DataStore<>();
        ArrayList<Object> allRecords = dataStore.readAll("records.dat");

        RecordList<Object> recordList = new RecordList<>();
        for (Object obj : allRecords) {
            recordList.add(obj);
        }

        int totalStudents = recordList.getTotalStudents().size();
        int totalCourses = recordList.getTotalCourses().size();

        statsRow.add(createStatCard("Total Students", String.valueOf(totalStudents)));
        statsRow.add(createStatCard("Total Faculty", String.valueOf(totalCourses)));
        statsRow.add(createStatCard("Departments", "3"));
        statsRow.add(createStatCard("Courses", "12"));

        dashboardBody.add(statsRow);
        dashboardBody.add(Box.createVerticalStrut(30));

        // -------------------- ADDINF DASHBOARD BODY TO MY MAIN CONTENT
        // ------------------
        mainContent.add(dashboardBody, BorderLayout.CENTER);
    }

    // ====================================== STAT CARD CREATION METHOD
    // ===========================
    public JPanel createStatCard(String title, String value) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(245, 245, 245));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(Color.DARK_GRAY);

        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        valueLabel.setForeground(Color.BLACK);
        valueLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

}