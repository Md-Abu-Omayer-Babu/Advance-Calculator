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

public class CalculatorUI {
    public static void main(String[] args) {
        showMainMenu();
    }

    public static void showMainMenu() {
        JFrame frame = new JFrame("Advanced Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new GridLayout(10, 1));

        String[] buttons = { "Basic Operations", "Scientific Operations", "Special Operations", "Unit Conversion",
                "Result Calculator", "Functional Operations", "View History", "Exit" };

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
                case "Result Calculator":
                    ResultSystem.resultCalculation(frame);
                    break;
                case "Functional Operations":
                    showFunctionalOperationsMenu(frame);
                    break;
                case "View History":
                    HistoryUI.showHistory(frame); // Pass the current JFrame
                    frame.setVisible(false); // Hide current window
                    break;
                // case "Clear History":
                // History.clearHistory();
                // JOptionPane.showMessageDialog(null, "History has been cleared", "Info",
                // JOptionPane.INFORMATION_MESSAGE);
                // showMainMenu();
                // break;
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

        String[] buttons = { "+", "-", "*", "/", "%",
                "7", "8", "9", "4", "5", "6",
                "1", "2", "3", "0", ".",
                "=", "C", "AC", "Back" };

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

        String[] buttons = { "Sine", "Cosine", "Tangent", "Secant", "Cosecant", "Cotangent",
                "Exponentiation", "Natural Logarithm (ln)", "Logarithm Base 10 (log)",
                "Nth Root", "Reciprocal" };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.addActionListener(new ScientificButtonClickListener(inputField, frame, previousFrame));
            panel.add(button);
        }

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            frame.dispose();
            if (previousFrame != null) {
                previousFrame.setVisible(true);
            }
        });

        panel.add(backButton);
        frame.add(panel, BorderLayout.CENTER);

        if (previousFrame != null) {
            previousFrame.setVisible(false); // Hide the previous window only if it exists
        }

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
                String inputText = inputField.getText().trim();

                double input = 0;

                // Handle cases where input is empty for most operations except Nth Root
                if (!command.equals("Nth Root") && inputText.isEmpty()) {
                    inputField.setText("Error: No input");
                    return;
                }

                if (!command.equals("Nth Root")) {
                    input = Double.parseDouble(inputText);
                }

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
                        String[] rootValues = JOptionPane
                                .showInputDialog(frame, "Enter base and root separated by a comma:")
                                .split(",");
                        if (rootValues.length != 2) {
                            inputField.setText("Error: Invalid input");
                            return;
                        }

                        double base = Double.parseDouble(rootValues[0].trim());
                        double root = Double.parseDouble(rootValues[1].trim());

                        if (root == 0) {
                            inputField.setText("Error: Cannot take 0th root");
                            return;
                        }

                        result = Double.toString(Math.pow(base, 1.0 / root));
                        break;
                    case "Reciprocal": // Inverse of the input(e.g. reciprocal(x) = 1 / x)
                        result = Double.toString(1 / input);
                        break;
                    case "Back":
                        frame.dispose();
                        previousFrame.setVisible(true);
                        return;
                }

                inputField.setText(result);
                History.addHistory(command + "(" + inputText + ") = " + result);
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

        String[] buttons = { "Factorial", "Prime Check", "Divisibility Check", "Back" };

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
        frame.setLayout(new GridLayout(5, 2)); // Adjusted to include the extra input field and the Back button

        JTextField inputField1 = new JTextField();
        JTextField inputField2 = new JTextField(); // For divisor in divisibility check
        JLabel resultLabel = new JLabel("Result: ");

        frame.add(new JLabel("Input a number:"));
        frame.add(inputField1);

        if (operation.equals("Divisibility Check")) {
            frame.add(new JLabel("Input Divisor:"));
            frame.add(inputField2);
        } else {
            frame.add(new JLabel()); // Empty label for spacing
            frame.add(new JLabel()); // Empty label for spacing
        }

        JButton calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String result = "";
                    int input1 = Integer.parseInt(inputField1.getText());

                    switch (operation) {
                        case "Factorial":
                            result = Long.toString(Calculator.factorial(input1));
                            break;
                        case "Prime Check":
                            boolean isPrime = Calculator.isPrime(input1);
                            if (isPrime == true) {
                                result = input1 + " is a Prime number!";
                            } else {
                                result = input1 + " is not a Prime number!";
                            }
                            break;
                        case "Divisibility Check":
                            if (inputField2.getText().isEmpty()) {
                                throw new IllegalArgumentException(
                                        "Please enter the divisor in the second input field.");
                            }
                            int input2 = Integer.parseInt(inputField2.getText());
                            boolean isDivisible = Calculator.isDivisible(input1, input2);
                            if (isDivisible) {
                                result = input1 + " is divisible by " + input2;
                            } else {
                                result = input1 + " is not divisible by " + input2;
                            }
                            break;
                        default:
                            result = "Invalid operation!";
                            break;
                    }

                    // Display the result in the resultLabel
                    resultLabel.setText("Result: " + result);
                    History.addHistory(operation + "(" + inputField1.getText()
                            + (operation.equals("Divisibility Check") ? ", " + inputField2.getText() : "") + ") = "
                            + result);
                } catch (NumberFormatException ex) {
                    resultLabel.setText("Error: Invalid input. Please enter a valid number.");
                } catch (IllegalArgumentException ex) {
                    resultLabel.setText("Error: " + ex.getMessage());
                } catch (Exception ex) {
                    resultLabel.setText("Error: An unexpected error occurred.");
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

        String[] buttons = { "Celsius to Fahrenheit", "Dollar to BDT", "Pound to BDT", "Euro to BDT",
                "Meter to Centimeter", "Meter to Inch", "Meter to Feet", "Meter to Yard",
                "Meter to Mile", "Gram to Kilogram", "Kilogram to Gram", "Kilogram to Pounds",
                "Kilogram to Ounces", "Kilogram to Stones", "Back" };

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
        frame.setLayout(new GridLayout(4, 2));

        JTextField inputField = new JTextField();
        JLabel inputLabel = new JLabel();
        JLabel resultLabel = new JLabel();

        final String inputPrompt;
        final String inputUnit;
        final String resultUnit;

        switch (conversion) {
            case "Celsius to Fahrenheit":
                inputPrompt = "Enter temperature in Celsius:";
                inputUnit = "Celsius";
                resultUnit = "Fahrenheit";
                break;
            case "Dollar to BDT":
                inputPrompt = "Enter amount in USD:";
                inputUnit = "USD";
                resultUnit = "BDT";
                break;
            case "Pound to BDT":
                inputPrompt = "Enter amount in GBP:";
                inputUnit = "GBP";
                resultUnit = "BDT";
                break;
            case "Euro to BDT":
                inputPrompt = "Enter amount in EUR:";
                inputUnit = "EUR";
                resultUnit = "BDT";
                break;
            case "Meter to Centimeter":
                inputPrompt = "Enter length in meters:";
                inputUnit = "Meters";
                resultUnit = "Centimeters";
                break;
            case "Meter to Inch":
                inputPrompt = "Enter length in meters:";
                inputUnit = "Meters";
                resultUnit = "Inches";
                break;
            case "Meter to Feet":
                inputPrompt = "Enter length in meters:";
                inputUnit = "Meters";
                resultUnit = "Feet";
                break;
            case "Meter to Yard":
                inputPrompt = "Enter length in meters:";
                inputUnit = "Meters";
                resultUnit = "Yards";
                break;
            case "Meter to Mile":
                inputPrompt = "Enter length in meters:";
                inputUnit = "Meters";
                resultUnit = "Miles";
                break;
            case "Gram to Kilogram":
                inputPrompt = "Enter weight in grams:";
                inputUnit = "Grams";
                resultUnit = "Kilograms";
                break;
            case "Kilogram to Gram":
                inputPrompt = "Enter weight in kilograms:";
                inputUnit = "Kilograms";
                resultUnit = "Grams";
                break;
            case "Kilogram to Pounds":
                inputPrompt = "Enter weight in kilograms:";
                inputUnit = "Kilograms";
                resultUnit = "Pounds";
                break;
            case "Kilogram to Ounces":
                inputPrompt = "Enter weight in kilograms:";
                inputUnit = "Kilograms";
                resultUnit = "Ounces";
                break;
            case "Kilogram to Stones":
                inputPrompt = "Enter weight in kilograms:";
                inputUnit = "Kilograms";
                resultUnit = "Stones";
                break;
            default:
                inputPrompt = "Enter a value:";
                inputUnit = "";
                resultUnit = "";
        }

        inputLabel.setText(inputPrompt);
        frame.add(inputLabel);
        frame.add(inputField);

        JButton calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(e -> {
            try {
                double input = Double.parseDouble(inputField.getText());
                double result = 0;

                switch (conversion) {
                    case "Celsius to Fahrenheit":
                        result = input * 9 / 5 + 32;
                        break;
                    case "Dollar to BDT":
                        result = input * 110;
                        break;
                    case "Pound to BDT":
                        result = input * 140;
                        break;
                    case "Euro to BDT":
                        result = input * 120;
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

                resultLabel.setText(input + " " + inputUnit + " = " + result + " " + resultUnit);
                History.addHistory(conversion + " (" + input + " " + inputUnit + ") = " + result + " " + resultUnit);
            } catch (Exception ex) {
                resultLabel.setText("Error: " + ex.getMessage());
            }
        });

        frame.add(calculateButton);
        frame.add(resultLabel);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            frame.dispose();
            currentFrame.setVisible(true);
        });
        frame.add(new JLabel());
        frame.add(backButton);

        frame.setVisible(true);
    }


    // result calculation
    

    public static void showFunctionalOperationsMenu(JFrame previousFrame) {
        JFrame frame = new JFrame("Functional Operations");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new GridLayout(7, 1));

        String[] buttons = { "Absolute value", "Floor value", "Ceiling value", "Random number", "Degrees to DMS",
                "DMS to Degrees", "Back" };

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
        frame.setSize(500, 300);
        frame.setLayout(new GridLayout(6, 2)); // Adjusted layout for better spacing

        JLabel inputLabel = new JLabel("Input:");
        JTextField inputField = new JTextField();
        JLabel resultLabel = new JLabel("Result: ");
        JButton calculateButton = new JButton("Calculate");

        JTextField degreesField = new JTextField();
        JTextField minutesField = new JTextField();
        JTextField secondsField = new JTextField();

        // Modify input method for each operation
        if (operation.equals("DMS to Degrees")) {
            frame.add(new JLabel("Degrees:"));
            frame.add(degreesField);

            frame.add(new JLabel("Minutes:"));
            frame.add(minutesField);

            frame.add(new JLabel("Seconds:"));
            frame.add(secondsField);
        } else {
            if (operation.equals("Random number")) {
                inputLabel.setText("Enter an upper value:");
                inputField.setToolTipText("Enter an upper value to get a random number from 0 to this value");
            }

            frame.add(inputLabel);
            frame.add(inputField);
        }

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String result = "";
                    String formattedResult = "";

                    switch (operation) {
                        case "Absolute value":
                            result = Double.toString(Math.abs(Double.parseDouble(inputField.getText())));
                            break;
                        case "Floor value":
                            result = Double.toString(Math.floor(Double.parseDouble(inputField.getText())));
                            break;
                        case "Ceiling value":
                            result = Double.toString(Math.ceil(Double.parseDouble(inputField.getText())));
                            break;
                        case "Random number":
                            double upperValue = Double.parseDouble(inputField.getText());
                            result = Double.toString(new Random().nextDouble() * upperValue);
                            break;
                        case "Degrees to DMS":
                            double inputDegree = Double.parseDouble(inputField.getText());
                            int d = (int) inputDegree;
                            double mFloat = (inputDegree - d) * 60;
                            int m = (int) mFloat;
                            double s = (mFloat - m) * 60;

                            formattedResult = d + "° " + m + "' " + String.format("%.2f", s) + "\""
                                    + "\n= " + d + "° + " + m + "'/60 + " + String.format("%.2f", s) + "\"/3600"
                                    + "\n= " + String.format("%.6f", inputDegree) + "°";
                            result = formattedResult;
                            break;
                        case "DMS to Degrees":
                            int degrees = Integer.parseInt(degreesField.getText().trim());
                            int minutes = Integer.parseInt(minutesField.getText().trim());
                            double seconds = Double.parseDouble(secondsField.getText().trim());
                            double decimalDegrees = degrees + (minutes / 60.0) + (seconds / 3600.0);

                            formattedResult = degrees + "° " + minutes + "' " + String.format("%.2f", seconds) + "\""
                                    + "\n= " + degrees + "° + " + minutes + "'/60 + " + String.format("%.2f", seconds)
                                    + "\"/3600"
                                    + "\n= " + String.format("%.6f", decimalDegrees) + "°";
                            result = formattedResult;
                            break;
                    }

                    resultLabel.setText("<html><pre>" + result + "</pre></html>"); // Preserve formatting
                    History.addHistory(operation + " -> " + result);
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