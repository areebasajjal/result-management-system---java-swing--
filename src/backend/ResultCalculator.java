package backend;


public interface ResultCalculator {
    static final double passMarks = 50;

    double calculateTotal();
    double calculatePercentage();
    String calculateGrade();
}
