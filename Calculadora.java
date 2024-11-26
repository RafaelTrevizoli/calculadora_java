import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculadora extends JFrame implements ActionListener {
    private JTextField display;
    private double memory = 0;
    private double result = 0;
    private String operator = "";
    private boolean startNewNumber = true;

    public Calculadora() {
        setTitle("Calculadora");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        display = new JTextField("0");
        display.setFont(new Font("Arial", Font.BOLD, 30));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        add(display, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx = 1;
        gbc.weighty = 1;

        String[] buttons = {
                "MC", "MR", "MS", "M+", "M-",
                "←", "CE", "C", "±", "√",
                "7", "8", "9", "/", "%",
                "4", "5", "6", "*", "1/x",
                "1", "2", "3", "-", "=",
                "0", ",", "+", ""
        };

        int row = 0, col = 0;
        for (int i = 0; i < buttons.length; i++) {
            JButton button = new JButton(buttons[i]);
            button.setFont(new Font("Arial", Font.BOLD, 18));
            button.addActionListener(this);

            gbc.gridx = col;
            gbc.gridy = row;

            if (buttons[i].equals("0")) {
                gbc.gridwidth = 2;
                col++;
            } else if (buttons[i].equals("=")) {
                gbc.gridheight = 6;
                gbc.gridy = row;
                row++;
            } else {
                gbc.gridwidth = 1;
                gbc.gridheight = 1;
            }

            if (!buttons[i].isEmpty()) {
                panel.add(button, gbc);
            }

            col++;
            if (col == 5) {
                col = 0;
                row++;
            }
        }

        add(panel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.matches("[0-9]")) {
            if (startNewNumber) {
                display.setText(command);
            } else {
                display.setText(display.getText() + command);
            }
            startNewNumber = false;
        } else if (command.equals("C")) {
            display.setText("0");
            result = 0;
            operator = "";
        } else if (command.equals("CE")) {
            display.setText("0");
        } else if (command.equals("←")) {
            String text = display.getText();
            display.setText(text.length() > 1 ? text.substring(0, text.length() - 1) : "0");
        } else if (command.equals("±")) {
            double value = Double.parseDouble(display.getText());
            display.setText(String.valueOf(-value));
        } else if (command.equals(",")) {
            if (!display.getText().contains(".")) {
                display.setText(display.getText() + ".");
            }
        } else if (command.equals("=")) {
            calculate(Double.parseDouble(display.getText()));
            operator = "";
            startNewNumber = true;
        } else if (command.equals("√")) {
            double value = Math.sqrt(Double.parseDouble(display.getText()));
            display.setText(String.valueOf(value));
            startNewNumber = true;
        } else if (command.equals("1/x")) {
            double value = 1 / Double.parseDouble(display.getText());
            display.setText(String.valueOf(value));
            startNewNumber = true;
        } else if (command.equals("M+")) {
            memory += Double.parseDouble(display.getText());
        } else if (command.equals("M-")) {
            memory -= Double.parseDouble(display.getText());
        } else if (command.equals("MR")) {
            display.setText(String.valueOf(memory));
            startNewNumber = true;
        } else if (command.equals("MS")) {
            memory = Double.parseDouble(display.getText());
        } else if (command.equals("MC")) {
            memory = 0;
        } else {
            if (!operator.isEmpty()) {
                calculate(Double.parseDouble(display.getText()));
            } else {
                result = Double.parseDouble(display.getText());
            }
            operator = command;
            startNewNumber = true;
        }
    }

    private void calculate(double value) {
        switch (operator) {
            case "+": result += value; break;
            case "-": result -= value; break;
            case "*": result *= value; break;
            case "/": result /= value; break;
            case "%": result %= value; break;
        }
        display.setText(String.valueOf(result));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Calculadora().setVisible(true);
        });
    }
}
