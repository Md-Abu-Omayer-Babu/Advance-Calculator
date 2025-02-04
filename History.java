import java.util.ArrayList;
import java.util.List;

public class History {
    private static List<String> calculations = new ArrayList<>();

    // Method to add a calculation to the history
    public static void addHistory(String calculation) {
        calculations.add(calculation);
    }

    // Method to get the list of calculations
    public static List<String> getCalculations() {
        return new ArrayList<>(calculations);
    }

    // Method to clear the history
    public static void clearHistory() {
        calculations.clear();
    }
}