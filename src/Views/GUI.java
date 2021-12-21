package Views;

import javax.swing.*;
import java.awt.*;

public class GUI {
    public JTextArea newJTextArea(int x, int y, int width, int height, int fontSize, Color foreground,
                                  Color background, String text) {
        JTextArea temp = new JTextArea();

        temp.setBounds(x, y, width, height);
        temp.setFont(new Font(Font.SERIF, Font.PLAIN, fontSize));
        temp.setForeground(foreground);
        temp.setBackground(background);
        temp.setText(text);
        temp.setEditable(false);

        return temp;
    }

    public JLabel newJLabel(int x, int y, int width, int height) {
        JLabel temp = new JLabel();

        temp.setBounds(x, y, width, height);


        return temp;
    }

    public JPanel newJPanel(int x, int y, int width, int height, Color background) {
        JPanel temp = new JPanel(null);

        temp.setBounds(x, y, width, height);
        temp.setBackground(background);

        return temp;
    }
}
