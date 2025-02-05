import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HistoryUI {
    public static void showHistory(JFrame previousFrame) {
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

        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton clearButton = new JButton("Clear History");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                History.clearHistory();
                JOptionPane.showMessageDialog(null, "History has been cleared", "Info",
                JOptionPane.INFORMATION_MESSAGE);
                historyArea.setText("");
            }
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                previousFrame.setVisible(true); // Show previous window
            }
        });

        buttonPanel.add(clearButton);
        buttonPanel.add(backButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}
