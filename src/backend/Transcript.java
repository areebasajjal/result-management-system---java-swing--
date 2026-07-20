package backend;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Transcript implements Serializable {

    private List<ResultEntry> results = new ArrayList<>();

    public void addOrUpdateResultEntry(ResultEntry r) {
    for (ResultEntry existing : results) {
        if (existing.getCourse().getTitle().equalsIgnoreCase(r.getCourse().getTitle())) {
            existing.setMarks(r.getMarks()); // update existing marks
            return;
        }
    }
    results.add(r); // add new if not found
}


    public double getTotalMarks() {
        double sum = 0;
        for (ResultEntry r : results)
            sum += r.getMarks();
        return sum;
    }

    public List<ResultEntry> getResults() {   
        
        //it is used in displayResults() in Student class
        
        return results;   //it is the array list of all courses and obtain marks of a student that has this transcript
    }

    public double calculateGPA() {
        double total = getTotalMarks();
        return Math.round(total / (results.size() * 25)); // simple formula
    }
}
