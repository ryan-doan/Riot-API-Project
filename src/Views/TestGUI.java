package Views;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class TestGUI {
    static JFrame frame = new JFrame();
    static JScrollPane scroll = new JScrollPane();
    static JPanel panel = new JPanel();
    static JTextArea text = new JTextArea();

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Metadata Ref.txt"));
            StringBuilder stringBuilder = new StringBuilder();
            while (true) {
                String temp = reader.readLine();
                if (temp == null) {
                    break;
                }
                stringBuilder.append(temp);
            }
            System.out.println(panel.getSize().toString());
            System.out.println(scroll.getSize().toString());
            text.setText(stringBuilder.toString());
            panel.add(text);
            panel.setBackground(Color.CYAN);
            scroll.setViewportView(panel);
            frame.add(scroll);
            frame.setSize(800,400);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
