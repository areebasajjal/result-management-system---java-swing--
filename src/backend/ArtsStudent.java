package backend;


public class ArtsStudent extends Student {

    public ArtsStudent(String id, String name,String gender, int semester) {
        super(id, name, "Arts", gender, semester);
    }
    public static String[] getAvailableCoursesForArtsProg(){
        String [] courses = {"Drawing","English","Technical and business writing","Economics"};
        return courses;
    }

        @Override
    public String calculateGrade() {
        double p = calculatePercentage();
        if (p >= 70) return "A";
        else if (p >= 60) return "B";
        else if (p >= 50) return "C";
        else if (p >= 40) return "D";
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



