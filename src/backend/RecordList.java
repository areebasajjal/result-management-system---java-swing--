package backend;

import java.io.Serializable;
import java.util.ArrayList;


public class RecordList<T> implements Serializable {

    private ArrayList<T> items = new ArrayList<>();

    public void add(T item) {
        items.add(item);
    }

    public void remove(String id) {
        for (int i = 0; i < items.size(); i++) {
            T item = items.get(i);
            boolean shouldRemove = false;

            // Check if it's a Student
            if (item instanceof Student) {
                Student student = (Student) item;
                if (student.getStudentId().equalsIgnoreCase(id)) {
                    shouldRemove = true;
                }
            }
            // Check if it's a Course
            else if (item instanceof Course) {
                Course course = (Course) item;
                if (course.getCourseCode().equalsIgnoreCase(id)) {
                    shouldRemove = true;
                }
            }

            if (shouldRemove) {
                items.remove(i);
                break;
            }
        }
    }

    public ArrayList<T> getAll() {
        return items;
    }

    public void showAll() {
        for (T item : items) {
            System.out.println(item);
        }
    }

    public ArrayList<Student> getTotalStudents() {
        ArrayList<Student> totalStuds = new ArrayList<>();

        for (T item : items) {
            if (item instanceof Student) {
                Student stud = (Student) item;
                totalStuds.add(stud);
            }
        }
        return totalStuds;
    }

    public ArrayList<Course> getTotalCourses() {
        ArrayList<Course> totalCourses = new ArrayList<>();

        for (T item : items) {
            if (item instanceof Course) {
                totalCourses.add((Course) item);

            }
        }
        return totalCourses;
    }

    public ArrayList<CourseInstructor> getTotalInstructors() {
        ArrayList<CourseInstructor> totalInstructors = new ArrayList<>();

        for (T item : items) {
            if (item instanceof CourseInstructor) {
                totalInstructors.add((CourseInstructor) item);

            }
        }
        return totalInstructors;
    }

}
