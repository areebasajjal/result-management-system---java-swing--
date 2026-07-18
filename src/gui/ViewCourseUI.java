package gui;

import javax.swing.*;

import backend.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewCourseUI {

private JFrame frame;
private JTextField course1;
private JTextField course2 ;
private JTextField course3 ;
private JTextField course4 ;
    
String selectedProg ;
ViewCourseUI(String sp){
    selectedProg = sp;
}
    

public void launchAddCoursesPage() {

    frame = new JFrame("View Courses");
    frame.setSize(600, 400);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    frame.setLayout(new BorderLayout());

    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
    mainPanel.setBackground(Color.WHITE);

    JLabel title = new JLabel("Student Courses");
    title.setFont(new Font("Segoe UI", Font.BOLD, 22));
    title.setAlignmentX(Component.CENTER_ALIGNMENT);

    mainPanel.add(title);
    mainPanel.add(Box.createVerticalStrut(20));

    JPanel formGrid = new JPanel(new GridLayout(0, 2, 20, 15));
    formGrid.setBackground(Color.WHITE);

    
    course1 = new JTextField();
    course2 = new JTextField();
    course3 = new JTextField();
    course4 = new JTextField();

    //Loading courses based on the program user selected
    loadCoursesByProgram(); //overrrite 

    
    formGrid.add(createField("Course 1", course1));
    formGrid.add(createField("Course 2", course2));
    formGrid.add(createField("Course 3", course3));
    formGrid.add(createField("Course 4", course4));

    mainPanel.add(formGrid);

    JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    buttonRow.setBackground(Color.WHITE);

    JButton cancelBtn = new JButton("Cancel");

    cancelBtn.addActionListener( new ActionListener() {
         @Override
          public void actionPerformed(ActionEvent e) {
        frame.dispose();
        }
     });

    buttonRow.add(cancelBtn);

    frame.add(mainPanel, BorderLayout.CENTER);
    frame.add(buttonRow, BorderLayout.SOUTH);

    frame.setVisible(true);
}



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


public void loadCoursesByProgram() {

    String[] allowedCourses;

    if (selectedProg.equalsIgnoreCase("Science")) {
        allowedCourses = ScienceStudent.getAvailableCoursesScience();
    } 
    else if (selectedProg.equalsIgnoreCase("Arts")) {
        allowedCourses = ArtsStudent.getAvailableCoursesForArtsProg();
    } 
    else if (selectedProg.equalsIgnoreCase("Engineering")) {
        allowedCourses = EngineeringStudent.getAvailableCoursesForEngineeringProg();
    } 
    else {
        JOptionPane.showMessageDialog(null, "Please select a program first!");
                    return;
    }

    course1.setText(allowedCourses[0]);
    course2.setText(allowedCourses[1]);
    course3.setText(allowedCourses[2]);
    course4.setText(allowedCourses[3]);

    course1.setEditable(false);
    course2.setEditable(false);
    course3.setEditable(false);
    course4.setEditable(false);
}


}