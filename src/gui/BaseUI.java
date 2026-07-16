package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public abstract class BaseUI { // CLASS STARTS { every main screen is going to extend ye wali class }

    protected JFrame frame;
    protected JPanel sidebar;
    protected JPanel mainContent;

    protected boolean sidebarVisible = true;
    protected int sidebarWidth = 180;

    // main launching method all the screens are going to use
    public void launchBasePage(String windowTitle, String pageTitle, String pageDescription) {

        // ---------------------------------MAIN FRAME ---------------------------------------------------
        frame = new JFrame(windowTitle);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1500, 700);
        frame.setLocationRelativeTo(null);

        ImageIcon icon1 = new ImageIcon(getClass().getResource("/images/dashboardicon.jpeg"));
        frame.setIconImage(icon1.getImage());

        // ---------------------------- MAIN CONTENT
        // ---------------------------------------------- //
        mainContent = new JPanel(new BorderLayout());
        mainContent.setBackground(Color.WHITE);
        frame.add(mainContent, BorderLayout.CENTER);

        // Slider icon for collapsing sidebar (CLICING ON THIS CLOSES THE SIDE BAR)
        ImageIcon icon2 = new ImageIcon(getClass().getResource("/images/slider.jpeg"));
        Image img = icon2.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        icon2 = new ImageIcon(img);

        // title 1 header for main content page
        JLabel title1 = new JLabel(pageTitle);
        title1.setIcon(icon2);
        title1.setIconTextGap(10);
        title1.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        title1.setBackground(Color.WHITE);
        title1.setOpaque(true);
        title1.setForeground(Color.BLACK);
        title1.setFont(new Font("Segoe UI", Font.BOLD, 14));
        title1.setHorizontalAlignment(SwingConstants.LEFT);
        title1.setVerticalAlignment(SwingConstants.CENTER);
        title1.setPreferredSize(new Dimension(0, 50));

        // title 2 sub header for the main content page
        JLabel title2 = new JLabel(pageTitle);
        title2.setBorder(BorderFactory.createEmptyBorder(20, 18, 3, 0));
        title2.setBackground(Color.WHITE);
        title2.setOpaque(true);
        title2.setForeground(Color.BLACK);
        title2.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JLabel title3 = new JLabel(pageDescription);
        title3.setBorder(BorderFactory.createEmptyBorder(0, 18, 3, 0));
        title3.setBackground(Color.WHITE);
        title3.setOpaque(true);
        title3.setForeground(Color.GRAY);
        title3.setFont(new Font("Segoe UI", Font.ITALIC, 14));

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setBackground(Color.WHITE);

        northPanel.add(title1, BorderLayout.NORTH);
        northPanel.add(title2, BorderLayout.CENTER);
        northPanel.add(title3, BorderLayout.SOUTH);

        mainContent.add(northPanel, BorderLayout.NORTH);

        // ------------------------------------ SIDEBAR ------------------------------------- //
        sidebar = new JPanel();
        sidebar.setBackground(Color.WHITE);
        sidebar.setPreferredSize(new Dimension(sidebarWidth, frame.getHeight()));
        sidebar.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        sidebar.setLayout(new BorderLayout());

        // Sidebar title
        JPanel titlePanel = new JPanel(new GridBagLayout());
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setPreferredSize(new Dimension(sidebarWidth, 80));
        JLabel label1 = new JLabel("<html><center>Student Result<br>Management System</center></html>");
        label1.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titlePanel.add(label1);
        sidebar.add(titlePanel, BorderLayout.NORTH);

        // Sidebar buttons
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(Color.WHITE);
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.add(Box.createVerticalStrut(10));
        buttonsPanel.add(createSidebarButton("Dashboard"));
        buttonsPanel.add(createSidebarButton("Courses"));
        buttonsPanel.add(createSidebarButton("Students"));
        buttonsPanel.add(createSidebarButton("Results"));
        sidebar.add(buttonsPanel, BorderLayout.CENTER);

        // Add sidebar to frame
        frame.add(sidebar, BorderLayout.WEST);

        // ----------------------------- SIDEBAR TOGGLE --------------------------------
        title1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        title1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                animateSidebar();
            }
        });

        loadPage();

        frame.setVisible(true);
    }

    public abstract void loadPage();

    // ===================== CREATE SIDEBAR BUTTONS  =====================================
    public JButton createSidebarButton(String text) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(180, 40));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setForeground(Color.BLACK);
        btn.setBackground(Color.WHITE);
        btn.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                handleSidebarNavigation(text);
            }
        });
        return btn;
    }

    // ===================== yahan side bar animate ho raha hai ========================================
    public void animateSidebar() {

        int targetWidth;
        if (sidebarVisible) {
            targetWidth = 0;
        } else {
            targetWidth = sidebarWidth;
        }

        Timer timer = new Timer(10, null);

        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int currentWidth = sidebar.getWidth();
                int step;

                if (sidebarVisible) {
                    step = -10;
                } else {
                    step = 10;
                }

                int newWidth = currentWidth + step;

                if ((sidebarVisible && newWidth <= targetWidth) ||
                        (!sidebarVisible && newWidth >= targetWidth)) {

                    newWidth = targetWidth;
                    sidebarVisible = !sidebarVisible;
                    timer.stop();
                }

                sidebar.setPreferredSize(new Dimension(newWidth, sidebar.getHeight()));
                sidebar.revalidate();
            }
        });

        timer.start();
    }

    public void handleSidebarNavigation(String pageName) {
        frame.dispose(); // close current window // if i wont write this to screen k uper screen aye gi

        switch (pageName) {
            case "Dashboard":
                new DashboardUI().launchDashboardPage();
                break;

            case "Courses":
                new FacultyUI().launchFacultyPage();
                break;

            case "Results":
                new ResultsUI().launchResultsPage();
                break;

            case "Students":
                new StudentsUI().launchStudentsPage();
                break;
        }
    }

} // CLASS ENDS
