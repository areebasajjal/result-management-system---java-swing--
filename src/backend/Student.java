package backend;

import java.io.Serializable;


public abstract class Student implements Serializable, ResultCalculator { // result calculator interface

    public static int totalStudents = 0;
    protected String studentId;
    protected String name;
    protected String program;
    protected String gender;
    protected int semester;
    protected Transcript transcript; // has a transcript

    public Student(String studentId, String name, String program, String gender, int semester) {
        this.studentId = studentId;
        this.name = name;
        this.program = program;
        this.gender = gender;
        this.semester = semester;
        transcript = new Transcript();
        totalStudents++;
    }

    public static int getTotalStudents() {
        return totalStudents;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public String getProgram() {
        return program;
    }

    public String getGender() {
        return gender;
    }

    public int getSemester() {
        return semester;
    }

    public double getGPA() {
        return transcript.calculateGPA();
    }

    public Transcript getTranscript() {
        return transcript;
    }

public void addCourse(Course c, double marks) {
    ResultEntry addedCourse = new ResultEntry(c, marks);
    transcript.addOrUpdateResultEntry(addedCourse);
}



    public String displayResults() {

        return (

        "Student: \" " + name + "\" | ID: \" " + studentId + "\" | Program: \" " + program +
                "\n______________Result Summary___________" +
                "\nTotal: " + calculateTotal() +
                "\nPercentage: " + calculatePercentage() +
                "\nGrade: " + calculateGrade() +
                "\nGPA: " + transcript.calculateGPA());

    }

    @Override
    public String toString() {
        return "Student [studentId=" + studentId + ", name=" + name + ", program=" + program + ", GPA: " + getGPA()
                + ", Semester: " + semester + ", Gender: " + gender + "]";
    }
}
