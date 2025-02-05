import java.util.List;
import java.util.Map;

public class Calculator {

    public static double sine(double value) {
        return Math.sin(Math.toRadians(value));
    }

    public static double cosine(double value) {
        return Math.cos(Math.toRadians(value));
    }

    public static double tangent(double value) {
        return Math.tan(Math.toRadians(value));
    }

    public static double exponentiate(double base, double exponent) {
        return Math.pow(base, exponent);
    }

    public static double logarithm(double value) {
        return Math.log10(value);
    }

    public static double nthRoot(double value, double root) {
        return Math.pow(value, 1.0 / root);
    }

    public static double reciprocal(double value) {
        return 1.0 / value;
    }

    public static long factorial(int value) {
        if (value < 0)
            throw new IllegalArgumentException("Value must be non-negative");
        long result = 1;
        for (int i = 1; i <= value; i++) {
            result *= i;
        }
        return result;
    }

    public static boolean isPrime(int value) {
        if (value <= 1)
            return false;
        for (int i = 2; i <= Math.sqrt(value); i++) {
            if (value % i == 0)
                return false;
        }
        return true;
    }

    public static boolean isDivisible(int a, int b) {
        return a % b == 0;
    }

    public static double celsiusToFahrenheit(double celsius) {
        return (celsius * 9 / 5) + 32;
    }

    public static double dollarToBDT(double dollar) {
        return dollar * 122; // Example conversion rate
    }

    public static double calculateGPA(List<Map.Entry<Integer, Double>> grades) {
        double totalPoints = 0, totalCredits = 0;
        for (Map.Entry<Integer, Double> grade : grades) {
            totalCredits += grade.getKey();
            totalPoints += grade.getKey() * grade.getValue();
        }
        return totalCredits > 0 ? totalPoints / totalCredits : 0;
    }

    public static double calculateCGPA(List<Double> gpas) {
        if (gpas.isEmpty())
            return 0;
        return gpas.stream().mapToDouble(Double::doubleValue).average().orElse(0);
    }

    public static String toDMS(double degrees) {
        int d = (int) degrees;
        int m = (int) ((degrees - d) * 60);
        double s = (degrees - d - m / 60.0) * 3600;
        return d + "°" + m + "'" + s + "\"";
    }

    public static double fromDMS(int degrees, int minutes, double seconds) {
        return degrees + (minutes / 60.0) + (seconds / 3600.0);
    }
}