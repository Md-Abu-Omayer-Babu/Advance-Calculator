import java.util.ArrayList;
import java.util.List;

public class History {
    private static List<String> calculations = new ArrayList<>();

    public static void addCalculation(String calculation) {
        calculations.add(calculation);
    }

    public static List<String> getCalculations() {
        return new ArrayList<>(calculations);
    }

    public static void clearHistory() {
        calculations.clear();
    }
}