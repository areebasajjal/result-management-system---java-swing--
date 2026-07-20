package backend;


import java.io.Serializable;

public class CourseInstructor implements Serializable {
    private String name;
    private String qualification;
    private String gender;
    public CourseInstructor(String name, String qualification,String gender) {
        this.name = name;
        this.qualification = qualification;
        this.gender = gender;
    }

    
    @Override
    public String toString() {
        return "CourseInstructor [name=" + name + ", qualification=" + qualification + ", gender=" + gender + "]";
    }


    public String getName() {
        return name;
    }


    public String getQualification() {
        return qualification;
    }


    public String getGender() {
        return gender;
    }

 }