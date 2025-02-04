import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HistoryUI {
    public static void showHistory() {
        JFrame frame = new JFrame("Calculation History");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);

        JTextArea historyArea = new JTextArea();
        historyArea.setEditable(false);

        for (String entry : History.getCalculations()) {
            historyArea.append(entry + "\n");
        }

        JScrollPane scrollPane = new JScrollPane(historyArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        JButton clearButton = new JButton("Clear History");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                History.clearHistory();
                historyArea.setText("");
            }
        });

        frame.add(clearButton, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}