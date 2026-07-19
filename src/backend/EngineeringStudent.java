package backend;




public class EngineeringStudent extends Student {

    public EngineeringStudent(String id, String name,String gender, int semester
    ) {
        super(id, name, "Engineering", gender, semester);
    }

    public static String[] getAvailableCoursesForEngineeringProg(){
        String [] courses = {"Calculus","Physics","Maths","Electronics"};
        return courses;
    }
        @Override
    public String calculateGrade() {
        double p = calculatePercentage();
        if (p >= 85) return "A";
        else if (p >= 75) return "B";
        else if (p >= 65) return "C";
        else if (p >= 55) return "D";
        return "F";
    }
    @Override
    public double calculatePercentage() {
    double percentage = (calculateTotal() / (transcript.getResults().size() * 100)) * 100;
    return Math.round(percentage * 100.0) / 100.0;
}


    @Override
    public double calculateTotal() {
        return transcript.getTotalMarks();
    }


}
