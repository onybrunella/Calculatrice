import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;


public class CalculatriceGUI extends JFrame {
    private JTextField textField;
    private JButton[] numberButtons;
    private JButton[] functionButtons;
    private JButton addButton, subButton, mulButton, divButton;
    private JButton decButton, equButton, delButton, clrButton;
    private JPanel panel = new JPanel();

    private double num1, num2;
    private char operator;

    private List<JButton> scientificButtons;

    public CalculatriceGUI() {
        setTitle("Calculatrice - Ony Brunella");
        setSize(600, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        setResizable(false);

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }    

        panel = new JPanel();
        panel.setLayout(new GridLayout(6, 4, 10, 10));
        panel.setBackground(Color.LIGHT_GRAY);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        textField = new JTextField("0");
        textField.setFont(new Font("Arial", Font.PLAIN, 20));
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setPreferredSize(new Dimension(600, 60)); 


        numberButtons = new JButton[10];
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].setFont(new Font("Arial", Font.PLAIN, 24));
            numberButtons[i].addActionListener(new NumberButtonListener());
            numberButtons[i].setBackground(Color.WHITE);
        }

        functionButtons = new JButton[8];
        addButton = new JButton("+");
        subButton = new JButton("-");
        mulButton = new JButton("*");
        divButton = new JButton("/");
        decButton = new JButton(".");
        equButton = new JButton("=");
        delButton = new JButton("DEL");
        clrButton = new JButton("CLR");

        functionButtons[0] = addButton;
        functionButtons[1] = subButton;
        functionButtons[2] = mulButton;
        functionButtons[3] = divButton;
        functionButtons[4] = decButton;
        functionButtons[5] = equButton;
        functionButtons[6] = delButton;
        functionButtons[7] = clrButton;

        for (int i = 0; i < 8; i++) {
            functionButtons[i].setFont(new Font("Arial", Font.PLAIN, 24));
            functionButtons[i].addActionListener(new FunctionButtonListener());
            functionButtons[i].setBackground(Color.GRAY);
        }

         // Scientific buttons
        scientificButtons = new ArrayList<>();
        String[] scientificLabels = {"sin", "cos", "tan", "sqrt", "log", "exp", "π"};
        for (String label : scientificLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.PLAIN, 18));
            button.addActionListener(new ScientificButtonListener());
            button.setBackground(Color.darkGray);
            scientificButtons.add(button);
        }

        panel.add(textField);

        for (int i = 1; i < 10; i++) {
            panel.add(numberButtons[i]);
        }
        panel.add(functionButtons[0]);
        panel.add(numberButtons[0]);
        panel.add(functionButtons[4]);
        panel.add(functionButtons[5]);
        panel.add(functionButtons[6]);
        panel.add(functionButtons[1]);
        panel.add(functionButtons[2]);
        panel.add(functionButtons[3]);
        panel.add(functionButtons[7]);

        // Scientific buttons
        for (JButton button : scientificButtons) {
            panel.add(button);
        }

        add(panel, BorderLayout.CENTER); 
        setVisible(true);
    }

    private class NumberButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < 10; i++) {
                if (e.getSource() == numberButtons[i]) {
                    textField.setText(textField.getText().concat(String.valueOf(i)));
                }
            }
        }
    }

    private class FunctionButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == addButton) {
                num1 = Double.parseDouble(textField.getText());
                operator = '+';
                textField.setText("");
            } else if (e.getSource() == subButton) {
                num1 = Double.parseDouble(textField.getText());
                operator = '-';
                textField.setText("");
            } else if (e.getSource() == mulButton) {
                num1 = Double.parseDouble(textField.getText());
                operator = '*';
                textField.setText("");
            } else if (e.getSource() == divButton) {
                num1 = Double.parseDouble(textField.getText());
                operator = '/';
                textField.setText("");
            } else if (e.getSource() == decButton) {
                textField.setText(textField.getText().concat("."));
            } else if (e.getSource() == equButton) {
                num2 = Double.parseDouble(textField.getText());

            try {
                switch (operator) {
                    case '+':
                        Addition addition = new Addition(new Nombre(num1), new Nombre(num2));
                        double result = addition.valeur();
                        textField.setText(String.valueOf(result));
                        break;
                    case '-':
                        Soustraction soustraction = new Soustraction(new Nombre(num1), new Nombre(num2));
                        result = soustraction.valeur();
                        textField.setText(String.valueOf(result));
                        break;
                    case '*':
                        Multiplication multiplication = new Multiplication(new Nombre(num1), new Nombre(num2));
                        result = multiplication.valeur();
                        textField.setText(String.valueOf(result));
                        break;
                    case '/':
                    if (num2 == 0) {
                        textField.setText("Erreur : Division par zéro !");
                    } else {
                        Division division = new Division(new Nombre(num1), new Nombre(num2));
                        result = division.valeur();
                        textField.setText(String.valueOf(result));
                    }
                        break;
                }
            } catch (NumberFormatException ex) {
                textField.setText("Erreur : Nombre mal formaté !");
            } catch (ArithmeticException ex) {
                textField.setText("Erreur : Division par zéro !");
            }
            } else if (e.getSource() == delButton) {
                String currentText = textField.getText();
                textField.setText(currentText.substring(0, currentText.length() - 1));
            } else if (e.getSource() == clrButton) {
                textField.setText("");
            }
        }
    }

    // Scientific buttons
    private class ScientificButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = ((JButton) e.getSource()).getText();
            double input = Double.parseDouble(textField.getText());
            double result = 0.0;

            switch (command) {
                case "sin":
                    result = Math.sin(input);
                    break;
                case "cos":
                    result = Math.cos(input);
                    break;
                case "tan":
                    result = Math.tan(input);
                    break;
                case "sqrt":
                    if (input >= 0) {
                        result = Math.sqrt(input);
                    } else {
                        textField.setText("Erreur : Racine carrée d'un nombre négatif !");
                        return;
                    }
                    break;
                case "log":
                    if (input > 0) {
                        result = Math.log(input);
                    } else {
                        textField.setText("Erreur : Logarithme d'un nombre non positif !");
                        return;
                    }
                    break;
                case "exp":
                    result = Math.exp(input);
                    break;
                case "π":
                    result = Math.PI;
                    break;
            }

            textField.setText(String.valueOf(result));
        }
    }

    public static void main(String[] args) {
        new CalculatriceGUI();
    }
}
