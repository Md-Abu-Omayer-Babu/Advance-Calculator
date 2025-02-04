import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Random;

public class CalculatorUI  {
    public static void main(String[] args) {
        showMainMenu();
    }

    public static void showMainMenu() {
        JFrame frame = new JFrame("Advanced Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new GridLayout(10, 1));

        String[] buttons = {"Basic Operations", "Scientific Operations", "Special Operations", "Unit Conversion",
                            "GPA and CGPA Calculator", "Functional Operations", "View History", "Clear History", "Exit"};

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.addActionListener(new MainMenuButtonClickListener(frame, text));
            frame.add(button);
        }

        frame.setVisible(true);
    }

    private static class MainMenuButtonClickListener implements ActionListener {
        private final JFrame frame;
        private final String command;

        public MainMenuButtonClickListener(JFrame frame, String command) {
            this.frame = frame;
            this.command = command;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            frame.dispose();
            switch (command) {
                case "Basic Operations":
                    showBasicCalculator(frame);
                    break;
                case "Scientific Operations":
                    showScientificCalculator(frame);
                    break;
                case "Special Operations":
                    showSpecialOperationsMenu(frame);
                    break;
                case "Unit Conversion":
                    showUnitConversionMenu(frame);
                    break;
                case "GPA and CGPA Calculator":
                    showGpaCgpaMenu(frame);
                    break;
                case "Functional Operations":
                    showFunctionalOperationsMenu(frame);
                    break;
                case "View History":
                    HistoryUI.showHistory();
                    break;
                case "Clear History":
                    History.clearHistory();
                    JOptionPane.showMessageDialog(null, "History has been cleared", "Info", JOptionPane.INFORMATION_MESSAGE);
                    showMainMenu();
                    break;
                case "Exit":
                    System.exit(0);
                    break;
            }
        }
    }

    public static void showBasicCalculator(JFrame previousFrame) {
        JFrame frame = new JFrame("Basic Calculator");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());

        JTextField inputField = new JTextField();
        frame.add(inputField, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4));

        String[] buttons = {"+", "-", "*", "/", "%",
                            "7", "8", "9", "4", "5", "6",
                            "1", "2", "3", "0", ".",
                            "=", "C", "AC", "Back"};

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.addActionListener(new BasicButtonClickListener(inputField, frame, previousFrame));
            panel.add(button);
        }

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public static void showScientificCalculator(JFrame previousFrame) {
        JFrame frame = new JFrame("Scientific Calculator");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        JTextField inputField = new JTextField();
        frame.add(inputField, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 3));

        String[] buttons = {"Sine", "Cosine", "Tangent", "Secant", "Cosecant", "Cotangent",
                            "Exponentiation", "Natural Logarithm (ln)", "Logarithm Base 10 (log)",
                            "Nth Root", "Reciprocal", "Back"};

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.addActionListener(new ScientificButtonClickListener(inputField, frame, previousFrame));
            panel.add(button);
        }

        frame.add(panel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private static class BasicButtonClickListener implements ActionListener {
        private final JTextField inputField;
        private final JFrame frame;
        private final JFrame previousFrame;

        public BasicButtonClickListener(JTextField inputField, JFrame frame, JFrame previousFrame) {
            this.inputField = inputField;
            this.frame = frame;
            this.previousFrame = previousFrame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.equals("=")) {
                try {
                    Expression exp = new ExpressionBuilder(inputField.getText()).build();
                    double result = exp.evaluate();
                    inputField.setText(Double.toString(result));
                    History.addHistory(inputField.getText() + " = " + result);
                } catch (Exception ex) {
                    inputField.setText("Error");
                }
            } else if (command.equals("C")) {
                if (inputField.getText().length() > 0) {
                    inputField.setText(inputField.getText().substring(0, inputField.getText().length() - 1));
                }
            } else if (command.equals("AC")) {
                inputField.setText("");
            } else if (command.equals("Back")) {
                frame.dispose();
                previousFrame.setVisible(true);
            } else {
                inputField.setText(inputField.getText() + command);
            }
        }
    }

    private static class ScientificButtonClickListener implements ActionListener {
        private final JTextField inputField;
        private final JFrame frame;
        private final JFrame previousFrame;

        public ScientificButtonClickListener(JTextField inputField, JFrame frame, JFrame previousFrame) {
            this.inputField = inputField;
            this.frame = frame;
            this.previousFrame = previousFrame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            try {
                String result = "";
                double input = Double.parseDouble(inputField.getText());
                switch (command) {
                    case "Sine":
                        result = Double.toString(Math.sin(Math.toRadians(input)));
                        break;
                    case "Cosine":
                        result = Double.toString(Math.cos(Math.toRadians(input)));
                        break;
                    case "Tangent":
                        result = Double.toString(Math.tan(Math.toRadians(input)));
                        break;
                    case "Secant":
                        result = Double.toString(1 / Math.cos(Math.toRadians(input)));
                        break;
                    case "Cosecant":
                        result = Double.toString(1 / Math.sin(Math.toRadians(input)));
                        break;
                    case "Cotangent":
                        result = Double.toString(1 / Math.tan(Math.toRadians(input)));
                        break;
                    case "Exponentiation":
                        result = Double.toString(Math.exp(input));
                        break;
                    case "Natural Logarithm (ln)":
                        result = Double.toString(Math.log(input));
                        break;
                    case "Logarithm Base 10 (log)":
                        result = Double.toString(Math.log10(input));
                        break;
                    case "Nth Root":
                        String[] rootValues = JOptionPane.showInputDialog(frame, "Enter base and root separated by a comma:").split(",");
                        double base = Double.parseDouble(rootValues[0].trim());
                        double root = Double.parseDouble(rootValues[1].trim());
                        result = Double.toString(Math.pow(base, 1.0 / root));
                        break;
                    case "Reciprocal":
                        result = Double.toString(1 / input);
                        break;
                    case "Back":
                        frame.dispose();
                        previousFrame.setVisible(true);
                        return;
                }
                inputField.setText(result);
                JOptionPane.showMessageDialog(frame, result, "Result", JOptionPane.INFORMATION_MESSAGE);
                History.addHistory(command + "(" + input + ") = " + result);
            } catch (Exception ex) {
                inputField.setText("Error");
            }
        }
    }

    public static void showSpecialOperationsMenu(JFrame previousFrame) {
        JFrame frame = new JFrame("Special Operations");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new GridLayout(4, 1));

        String[] buttons = {"Factorial", "Prime Check", "Divisibility Check", "Back"};

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.addActionListener(new SpecialOperationsButtonClickListener(frame, previousFrame, text));
            frame.add(button);
        }

        frame.setVisible(true);
    }

    private static class SpecialOperationsButtonClickListener implements ActionListener {
        private final JFrame frame;
        private final JFrame previousFrame;
        private final String command;

        public SpecialOperationsButtonClickListener(JFrame frame, JFrame previousFrame, String command) {
            this.frame = frame;
            this.previousFrame = previousFrame;
            this.command = command;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            frame.dispose();
            switch (command) {
                case "Factorial":
                case "Prime Check":
                case "Divisibility Check":
                    performSpecialOperation(command, frame, previousFrame);
                    break;
                case "Back":
                    previousFrame.setVisible(true);
                    break;
            }
        }
    }

    private static void performSpecialOperation(String operation, JFrame currentFrame, JFrame previousFrame) {
        JFrame frame = new JFrame(operation);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new GridLayout(4, 2)); // Adjusted to include the Back button

        JTextField inputField = new JTextField();
        JLabel resultLabel = new JLabel("Result: ");

        frame.add(new JLabel("Input:"));
        frame.add(inputField);

        JButton calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int input = Integer.parseInt(inputField.getText());
                    String result = "";

                    switch (operation) {
                        case "Factorial":
                            result = Long.toString(Calculator.factorial(input));
                            break;
                        case "Prime Check":
                            boolean isPrime = Calculator.isPrime(input);
                            if (isPrime) {
                                result = input + " is a Prime number!";
                            } else {
                                result = input + " is not a Prime number!";
                            }
                            break;
                        case "Divisibility Check":
                            String[] values = JOptionPane.showInputDialog("Enter two numbers separated by a comma:").split(",");
                            int a = Integer.parseInt(values[0].trim());
                            int b = Integer.parseInt(values[1].trim());
                            boolean isDivisible = Calculator.isDivisible(a, b);
                            if (isDivisible) {
                                result = a + " is divisible by " + b;
                            } else {
                                result = a + " is not divisible by " + b;
                            }
                            break;
                    }
                    
                    // Display the result in the resultLabel
                    resultLabel.setText("Result: " + result);
                    History.addHistory(operation + "(" + inputField.getText() + ") = " + result);
                } catch (Exception ex) {
                    resultLabel.setText("Error: " + ex.getMessage());
                }
            }
        });

        frame.add(calculateButton);
        frame.add(resultLabel);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                currentFrame.setVisible(true);
            }
        });
        frame.add(new JLabel()); // Empty label for spacing
        frame.add(backButton);

        frame.setVisible(true);
    }

    public static void showUnitConversionMenu(JFrame previousFrame) {
        JFrame frame = new JFrame("Unit Conversion");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new GridLayout(16, 1));

        String[] buttons = {"Celsius to Fahrenheit", "Dollar to BDT", "Pound to BDT", "Euro to BDT",
                            "Meter to Centimeter", "Meter to Inch", "Meter to Feet", "Meter to Yard",
                            "Meter to Mile", "Gram to Kilogram", "Kilogram to Gram", "Kilogram to Pounds",
                            "Kilogram to Ounces", "Kilogram to Stones", "Back"};

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.addActionListener(new UnitConversionButtonClickListener(frame, previousFrame, text));
            frame.add(button);
        }

        frame.setVisible(true);
    }

    private static class UnitConversionButtonClickListener implements ActionListener {
        private final JFrame frame;
        private final JFrame previousFrame;
        private final String command;

        public UnitConversionButtonClickListener(JFrame frame, JFrame previousFrame, String command) {
            this.frame = frame;
            this.previousFrame = previousFrame;
            this.command = command;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            frame.dispose();
            switch (command) {
                case "Celsius to Fahrenheit":
                case "Dollar to BDT":
                case "Pound to BDT":
                case "Euro to BDT":
                case "Meter to Centimeter":
                case "Meter to Inch":
                case "Meter to Feet":
                case "Meter to Yard":
                case "Meter to Mile":
                case "Gram to Kilogram":
                case "Kilogram to Gram":
                case "Kilogram to Pounds":
                case "Kilogram to Ounces":
                case "Kilogram to Stones":
                    performUnitConversion(command, frame, previousFrame);
                    break;
                case "Back":
                    previousFrame.setVisible(true);
                    break;
            }
        }
    }

    private static void performUnitConversion(String conversion, JFrame currentFrame, JFrame previousFrame) {
        JFrame frame = new JFrame(conversion);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new GridLayout(4, 2)); // Adjusted to include the Back button

        JTextField inputField = new JTextField();
        JLabel resultLabel = new JLabel("Result: ");

        frame.add(new JLabel("Input:"));
        frame.add(inputField);

        JButton calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double input = Double.parseDouble(inputField.getText());
                    double result = 0;

                    switch (conversion) {
                        case "Celsius to Fahrenheit":
                            result = Calculator.celsiusToFahrenheit(input);
                            break;
                        case "Dollar to BDT":
                            result = Calculator.dollarToBDT(input);
                            break;
                        case "Pound to BDT":
                            result = input * 120; // Example conversion rate
                            break;
                        case "Euro to BDT":
                            result = input * 115; // Example conversion rate
                            break;
                        case "Meter to Centimeter":
                            result = input * 100;
                            break;
                        case "Meter to Inch":
                            result = input * 39.3701;
                            break;
                        case "Meter to Feet":
                            result = input * 3.28084;
                            break;
                        case "Meter to Yard":
                            result = input * 1.09361;
                            break;
                        case "Meter to Mile":
                            result = input * 0.000621371;
                            break;
                        case "Gram to Kilogram":
                            result = input / 1000;
                            break;
                        case "Kilogram to Gram":
                            result = input * 1000;
                            break;
                        case "Kilogram to Pounds":
                            result = input * 2.20462;
                            break;
                        case "Kilogram to Ounces":
                            result = input * 35.274;
                            break;
                        case "Kilogram to Stones":
                            result = input * 0.157473;
                            break;
                    }

                    resultLabel.setText("Result: " + result);
                    History.addHistory(conversion + "(" + input + ") = " + result);
                } catch (Exception ex) {
                    resultLabel.setText("Error: " + ex.getMessage());
                }
            }
        });

        frame.add(calculateButton);
        frame.add(resultLabel);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                currentFrame.setVisible(true);
            }
        });
        frame.add(new JLabel()); // Empty label for spacing
        frame.add(backButton);

        frame.setVisible(true);
    }

    public static void showGpaCgpaMenu(JFrame previousFrame) {
        JFrame frame = new JFrame("GPA and CGPA Calculator");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new GridLayout(4, 1));

        String[] buttons = {"Calculate GPA", "Calculate CGPA", "Back"};

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.addActionListener(new GpaCgpaButtonClickListener(frame, previousFrame, text));
            frame.add(button);
        }

        frame.setVisible(true);
    }

    private static class GpaCgpaButtonClickListener implements ActionListener {
        private final JFrame frame;
        private final JFrame previousFrame;
        private final String command;

        public GpaCgpaButtonClickListener(JFrame frame, JFrame previousFrame, String command) {
            this.frame = frame;
            this.previousFrame = previousFrame;
            this.command = command;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            frame.dispose();
            switch (command) {
                case "Calculate GPA":
                case "Calculate CGPA":
                    performGpaCgpaCalculation(command, frame, previousFrame);
                    break;
                case "Back":
                    previousFrame.setVisible(true);
                    break;
            }
        }
    }

    private static void performGpaCgpaCalculation(String calculation, JFrame currentFrame, JFrame previousFrame) {
        JFrame frame = new JFrame(calculation);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new GridLayout(4, 2)); // Adjusted to include the Back button

        JTextField inputField = new JTextField();
        JLabel resultLabel = new JLabel("Result: ");

        frame.add(new JLabel("Input:"));
        frame.add(inputField);

        JButton calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String result = "";

                    switch (calculation) {
                        case "Calculate GPA":
                            List<Map.Entry<Integer, Double>> grades = new ArrayList<>();
                            String[] entries = inputField.getText().split(";");
                            for (String entry : entries) {
                                String[] grade = entry.split(",");
                                grades.add(new AbstractMap.SimpleEntry<>(Integer.parseInt(grade[0].trim()), Double.parseDouble(grade[1].trim())));
                            }
                            result = Double.toString(Calculator.calculateGPA(grades));
                            break;
                        case "Calculate CGPA":
                            List<Double> gpas = new ArrayList<>();
                            String[] gpaValues = inputField.getText().split(",");
                            for (String gpa : gpaValues) {
                                gpas.add(Double.parseDouble(gpa.trim()));
                            }
                            result = Double.toString(Calculator.calculateCGPA(gpas));
                            break;
                    }

                    resultLabel.setText("Result: " + result);
                    History.addHistory(calculation + "(" + inputField.getText() + ") = " + result);
                } catch (Exception ex) {
                    resultLabel.setText("Error: " + ex.getMessage());
                }
            }
        });
        frame.add(calculateButton);
        frame.add(resultLabel);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                currentFrame.setVisible(true);
            }
        });
        frame.add(new JLabel()); // Empty label for spacing
        frame.add(backButton);

        frame.setVisible(true);
    }

    public static void showFunctionalOperationsMenu(JFrame previousFrame) {
        JFrame frame = new JFrame("Functional Operations");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new GridLayout(7, 1));

        String[] buttons = {"Absolute value", "Floor value", "Ceiling value", "Random number", "Degrees to DMS", "DMS to Degrees", "Back"};

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.addActionListener(new FunctionalOperationsButtonClickListener(frame, previousFrame, text));
            frame.add(button);
        }

        frame.setVisible(true);
    }

    private static class FunctionalOperationsButtonClickListener implements ActionListener {
        private final JFrame frame;
        private final JFrame previousFrame;
        private final String command;

        public FunctionalOperationsButtonClickListener(JFrame frame, JFrame previousFrame, String command) {
            this.frame = frame;
            this.previousFrame = previousFrame;
            this.command = command;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            frame.dispose();
            switch (command) {
                case "Absolute value":
                case "Floor value":
                case "Ceiling value":
                case "Random number":
                case "Degrees to DMS":
                case "DMS to Degrees":
                    performFunctionalOperation(command, frame, previousFrame);
                    break;
                case "Back":
                    previousFrame.setVisible(true);
                    break;
            }
        }
    }

    private static void performFunctionalOperation(String operation, JFrame currentFrame, JFrame previousFrame) {
        JFrame frame = new JFrame(operation);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new GridLayout(4, 2)); // Adjusted to include the Back button

        JTextField inputField = new JTextField();
        JLabel resultLabel = new JLabel("Result: ");

        frame.add(new JLabel("Input:"));
        frame.add(inputField);

        JButton calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double input = Double.parseDouble(inputField.getText());
                    String result = "";

                    switch (operation) {
                        case "Absolute value":
                            result = Double.toString(Math.abs(input));
                            break;
                        case "Floor value":
                            result = Double.toString(Math.floor(input));
                            break;
                        case "Ceiling value":
                            result = Double.toString(Math.ceil(input));
                            break;
                        case "Random number":
                            result = Double.toString(new Random().nextDouble());
                            break;
                        case "Degrees to DMS":
                            result = Calculator.toDMS(input);
                            break;
                        case "DMS to Degrees":
                            String[] dmsValues = inputField.getText().split(",");
                            int degrees = Integer.parseInt(dmsValues[0].trim());
                            int minutes = Integer.parseInt(dmsValues[1].trim());
                            double seconds = Double.parseDouble(dmsValues[2].trim());
                            result = Double.toString(Calculator.fromDMS(degrees, minutes, seconds));
                            break;
                    }

                    resultLabel.setText("Result: " + result);
                    History.addHistory(operation + "(" + inputField.getText() + ") = " + result);
                } catch (Exception ex) {
                    resultLabel.setText("Error: " + ex.getMessage());
                }
            }
        });

        frame.add(calculateButton);
        frame.add(resultLabel);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                currentFrame.setVisible(true);
            }
        });
        frame.add(new JLabel()); // Empty label for spacing
        frame.add(backButton);

        frame.setVisible(true);
    }
}