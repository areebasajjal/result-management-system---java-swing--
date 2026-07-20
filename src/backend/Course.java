package backend;

import java.io.Serializable;

public class Course implements Serializable {

    public static int totalCourses = 0;

    private String courseCode;
    private String title;
    private int creditHours;
    private CourseInstructor instructor;

    public Course(String code, String title, int creditHours, CourseInstructor instructor) {
        this.courseCode = code;
        this.title = title;
        this.creditHours = creditHours;
        this.instructor = instructor;
        totalCourses++;
    }


    public String getCourseCode() { return courseCode; }
    public String getTitle() { return title; }
    public int getCreditHours() { return creditHours; }
    public CourseInstructor getInstructor() { return instructor; }

public void displayCourseDetails() {
    System.out.println(courseCode + " - " + title + " | Credit hours: " + creditHours);

    if (instructor != null) {
        System.out.println("Instructor: " + instructor);
    } else {
        System.out.println("Instructor: Not Assigned");
    }
}


  @Override
public String toString() {
    String instructorName;

    if (instructor != null) {
        instructorName = instructor.getName();
    } else {
        instructorName = "Not Assigned";
    }

    return "Course[" +
           "Code='" + courseCode +
           ", Title='" + title +
           ", Credit Hours=" + creditHours +
           ", Instructor: " + instructorName +
           "]";
}


}