package backend;



import java.io.Serializable;

public class ResultEntry implements Serializable {
    private Course course;
    private double marksObtained;

    public ResultEntry(Course course, double marks) {
        this.course = course;
        this.marksObtained = marks;
    }

    public void setMarks(double m) {
        marksObtained = m;
    }

    public double getMarks() { 
        return marksObtained; }
    public Course getCourse() { 
        return course; }

    // @Override
    // public String toString() {
    //     return "ResultEntry [course=" + course + ", marksObtained=" + marksObtained + "]";
    // }
}
