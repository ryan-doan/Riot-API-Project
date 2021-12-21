package Views;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class StartingGUI {
    JFrame frame;
    JPanel panel;
    JLabel name;
    JLabel authors, errorLabel;
    JTextField username;
    JButton searchButton;
    Font font;
    Color lightBlue, lighterBlue, black, grey;

    public StartingGUI() {
        try {
            font = Font.createFont
                    (Font.TRUETYPE_FONT, new File("Christmas.ttf"));
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        black = new Color(59, 11, 59);
        grey = new Color(86, 24, 86);

        frame = new JFrame("League of Legends");
        panel = new JPanel(null);
        panel.setBackground(black);

        name = new JLabel("League of Legends profile");
        name.setForeground(Color.white);
        name.setBounds(35, 40, 360, 45);
        name.setFont(font.deriveFont(36f));
        panel.add(name);

        username = new JTextField(5);
        username.setForeground(Color.white);
        username.setBounds(50, 110, 200, 40);
        username.setBackground(grey);
        username.setBorder(null);
        username.setFont(font.deriveFont(28f));
        panel.add(username);

        // Click to search
        searchButton = new JButton("Search");
        searchButton.setForeground(Color.white);
        searchButton.setFont(font.deriveFont(24f));
        searchButton.setBounds(260, 110, 80, 40);
        searchButton.setBackground(grey);
        searchButton.setBorder(null);
        searchButton.setFocusPainted(false);
        panel.add(searchButton);

        errorLabel = new JLabel();
        errorLabel.setForeground(Color.white);
        errorLabel.setBounds(50, 160, 350, 30);
        errorLabel.setFont(font.deriveFont(18f));
        panel.add(errorLabel);

        authors = new JLabel("A program made by Ryan Doan and An Nguyen");
        authors.setForeground(Color.white);
        authors.setBounds(20, 230, 360, 20);
        authors.setFont(font.deriveFont(15f));
        panel.add(authors);

        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new StartingGUI();
    }

    public JFrame getFrame() {
        return frame;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public JPanel getPanel() {
        return panel;
    }

    public JTextField getUsername() {
        return username;
    }

    public JLabel getErrorLabel() {
        return errorLabel;
    }
}
